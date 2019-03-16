package zzk.project.dms.domain.services.impl;

import com.vaadin.flow.data.provider.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.dao.TenementRepository;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.DormitorySpaceService;
import zzk.project.dms.domain.services.TenementService;
import zzk.project.dms.middle.ServiceAndSubscriber;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ServiceAndSubscriber
@Transactional(rollbackFor = DormitoryManageException.class)
public class TenementServiceImpl implements TenementService {

    @Autowired
    private TenementRepository tenementRepository;

    @Autowired
    private DormitorySpaceService dormitorySpaceService;

    @Override
    public JpaRepository<Tenement, Long> getRepository() {
        return tenementRepository;
    }

    @Override
    public Stream<Tenement> filterFromBackend(Query<Tenement, String> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();
        Optional<String> filter = query.getFilter();
        if (filter.isPresent()) {
            String string = filter.get();
            List<Tenement> byNameContains = tenementRepository.findDistinctByNameContains(string);
            return byNameContains.stream().skip(offset).limit(limit);
        }
        return fetchFromBackend(offset,limit);
    }

    @Override
    public int sizeInFilterBackend(Query<Tenement, String> query) {
        Optional<String> queryFilter = query.getFilter();
        if (queryFilter.isPresent()) {
            String string = queryFilter.get();
            return tenementRepository.countDistinctByNameContains(string);
        }
        return integerCount();
    }

    @Override
    public void distributeBerthForTenement(Tenement tenement) throws DormitoryManageException {
        if (tenement.getId() == null) {
            serveNewTenement(tenement);
        } else {
            serveOldTenement(tenement);
        }
    }

    private void serveOldTenement(Tenement tenement) throws DormitoryManageException {
        Tenement oldTenement;
        Optional<Tenement> oldTenementOptional = tenementRepository.findById(tenement.getId());
        if (oldTenementOptional.isPresent()) {
            oldTenement = oldTenementOptional.get();
            DormitorySpace oldDormitorySpace = oldTenement.getBerth();
            DormitorySpace newDormitorySpace = tenement.getBerth();
            OldTenementServeType serveType = analysisServeType(oldDormitorySpace, newDormitorySpace);
            switch (serveType) {
                case RETURN:
                    put(tenement);
                    dormitorySpaceService.updateOccupy(oldDormitorySpace, -1);
                    break;

                case EXCHANGE:
                    setAvailableBerthForTenement(tenement, newDormitorySpace);
                    dormitorySpaceService.updateOccupy(oldDormitorySpace, -1);
                    break;

                case TAKEIN:
                    setAvailableBerthForTenement(tenement, newDormitorySpace);
                    dormitorySpaceService.updateOccupy(newDormitorySpace, 1);
                    break;

                case KEEP:
                default:
                    break;
            }
        }
    }

    private void serveNewTenement(Tenement tenement) throws DormitoryManageException {
        setAvailableBerthForTenement(tenement, tenement.getBerth());
        put(tenement);
    }

    private void setAvailableBerthForTenement(Tenement tenement, DormitorySpace dormitorySpace) throws DormitoryManageException {
        if (dormitorySpace != null) {
            if (!dormitorySpace.isAvailable()) {
                throw new DormitoryManageException("选的的空间已满或者床位已被占用");
            }

            if (dormitorySpace.getType() == DormitorySpaceType.BERTH) {
                tenement.setBerth(dormitorySpace);
            } else {
                try {
                    DormitorySpace availableBerth = dormitorySpaceService.findAvailableBerth(dormitorySpace);
                    tenement.setBerth(availableBerth);
                    save(tenement);
                    dormitorySpaceService.updateOccupy(availableBerth, 1);
                } catch (DormitoryManageException e) {
                    throw e;
                }
            }
        }
    }

    private OldTenementServeType analysisServeType(DormitorySpace oldDormitorySpace, DormitorySpace newDormitorySpace) {
        OldTenementServeType serveType;
        if (newDormitorySpace != null) {
            serveType = OldTenementServeType.TAKEIN;
            if (oldDormitorySpace != null) {
                serveType = OldTenementServeType.EXCHANGE;
            }
        } else {
            serveType = OldTenementServeType.RETURN;
            if (oldDormitorySpace == null) {
                serveType = OldTenementServeType.KEEP;
            }
        }
        return serveType;
    }

    private enum OldTenementServeType {
        EXCHANGE,
        RETURN,
        TAKEIN,
        KEEP
    }
}
