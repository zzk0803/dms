package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
            DormitorySpace oldDormitorySpace = oldTenement.getDormitorySpace();
            DormitorySpace newDormitorySpace = tenement.getDormitorySpace();
            OldTenementServeType serveType = analysisServeType(oldDormitorySpace, newDormitorySpace);
            switch (serveType) {
                case RETURN:
                    save(tenement);
                    dormitorySpaceService.updateOccupy(oldDormitorySpace, -1);
                    break;

                case EXCHANGE:
                    save(tenement);
                    setAvailableBerthForTenement(tenement, newDormitorySpace);
                    dormitorySpaceService.updateOccupy(oldDormitorySpace, -1);
                    break;

                case TAKEIN:
                    save(tenement);
                    setAvailableBerthForTenement(tenement, newDormitorySpace);
                    break;

                case KEEP:
                default:
                    break;
            }
        }
    }

    private void serveNewTenement(Tenement tenement) throws DormitoryManageException {
        setAvailableBerthForTenement(tenement, tenement.getDormitorySpace());
        save(tenement);
    }

    private void setAvailableBerthForTenement(Tenement tenement, DormitorySpace dormitorySpace) throws DormitoryManageException {
        if (dormitorySpace != null) {
            if (!dormitorySpace.isAvailable()) {
                throw new DormitoryManageException("选的的空间已满或床位已被占用");
            }

            DormitorySpace assumeAvailableBerth = dormitorySpace;
            try {
                if (assumeAvailableBerth.getType() != DormitorySpaceType.BERTH) {
                    assumeAvailableBerth = dormitorySpaceService.findAvailableBerth(dormitorySpace);
                }
                tenement.setDormitorySpace(assumeAvailableBerth);
                save(tenement);
                dormitorySpaceService.updateOccupy(assumeAvailableBerth, 1);
                } catch (DormitoryManageException e) {
                    throw e;
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

    @Override
    public List<Tenement> filterFromBackend(String name, Pageable pageable) {
        return tenementRepository.findAll(
                (Specification<Tenement>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%")
        );
//        return tenementRepository.findAll((Specification<Tenement>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%"), pageable);
    }

    @Override
    public int sizeInBackend(String name) {
        return (int) tenementRepository.count((Specification<Tenement>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%"));
    }
}
