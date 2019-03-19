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
import java.util.Objects;
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
    public void serve(Tenement tenement) throws DormitoryManageException {
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
            TenementServeType serveType = analysisServeType(oldDormitorySpace, newDormitorySpace);
            switch (serveType) {
                case RETURN:
                    dormitorySpaceService.updateOccupy(oldDormitorySpace, -1);
                    break;

                case EXCHANGE:
                    newDormitorySpace = findAvailableBerthForTenement(newDormitorySpace);
                    tenement.setDormitorySpace(newDormitorySpace);
                    dormitorySpaceService.updateOccupy(newDormitorySpace, 1);
                    dormitorySpaceService.updateOccupy(oldDormitorySpace, -1);
                    break;

                case TAKEIN:
                    newDormitorySpace = findAvailableBerthForTenement(newDormitorySpace);
                    tenement.setDormitorySpace(newDormitorySpace);
                    dormitorySpaceService.updateOccupy(newDormitorySpace, 1);
                    break;

                case KEEP:
                default:
                    break;
            }
            save(tenement);
        }
    }

    private void serveNewTenement(Tenement tenement) throws DormitoryManageException {
        DormitorySpace dormitorySpace = tenement.getDormitorySpace();
        if (Objects.nonNull(dormitorySpace)) {
            DormitorySpace newDormitorySpace = findAvailableBerthForTenement(dormitorySpace);
            tenement.setDormitorySpace(newDormitorySpace);
            dormitorySpaceService.updateOccupy(newDormitorySpace, 1);
        }
        save(tenement);
    }

    private DormitorySpace findAvailableBerthForTenement(DormitorySpace dormitorySpace) throws DormitoryManageException {
        DormitorySpace assumeAvailableBerth;
        if (!dormitorySpace.isAvailable()) {
            throw new DormitoryManageException("选的的空间已满或床位已被占用");
        }

        assumeAvailableBerth = dormitorySpace;
        if (assumeAvailableBerth.getType() != DormitorySpaceType.BERTH) {
            assumeAvailableBerth = dormitorySpaceService.findAvailableBerth(dormitorySpace);
        }
        return assumeAvailableBerth;
    }

    private TenementServeType analysisServeType(DormitorySpace oldDormitorySpace, DormitorySpace newDormitorySpace) {
        TenementServeType serveType;
        boolean oldIsNull = oldDormitorySpace == null;
        boolean newIsNull = newDormitorySpace == null;
        boolean spaceEqual = (oldIsNull && newIsNull) || (!oldIsNull && !newIsNull && newDormitorySpace.equals(oldDormitorySpace));
        if (spaceEqual) {
            serveType = TenementServeType.KEEP;
        } else {
            if (newIsNull) {
                serveType = TenementServeType.RETURN;
            } else {
                serveType = TenementServeType.TAKEIN;
            }
        }
        if (oldIsNull && !newIsNull) {
            serveType=TenementServeType.EXCHANGE;
        }
        return serveType;
    }

    private enum TenementServeType {
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
