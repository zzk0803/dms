package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.dao.TenementRepository;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.DormitorySpaceService;
import zzk.project.dms.domain.services.TenementService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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
    public Tenement delete(Tenement tenement) {
        DormitorySpace dormitorySpace;
        if ((dormitorySpace = tenement.getDormitorySpace()) != null) {
            dormitorySpaceService.updateOccupy(dormitorySpace, dormitorySpace.getHasOccupy());
        }
        getRepository().delete(tenement);
        return tenement;
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

                case UNKNOW:
                    throw new DormitoryManageException("未知类型！！FixMe！！");

                case KEEP:
                default:
                    break;
            }
            commit(tenement);
        }
    }

    private void serveNewTenement(Tenement tenement) throws DormitoryManageException {
        DormitorySpace dormitorySpace = tenement.getDormitorySpace();
        if (Objects.nonNull(dormitorySpace)) {
            DormitorySpace newDormitorySpace = findAvailableBerthForTenement(dormitorySpace);
            tenement.setDormitorySpace(newDormitorySpace);
            dormitorySpaceService.updateOccupy(newDormitorySpace, 1);
        }
        commit(tenement);
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
            if (oldIsNull) {
                serveType = TenementServeType.TAKEIN;
            } else if (newIsNull) {
                serveType = TenementServeType.RETURN;
            } else {
                serveType = TenementServeType.EXCHANGE;
            }
        }

        return serveType;
    }

    @Override
    public List<Tenement> filterFromBackend(String name, Pageable pageable) {
        return tenementRepository.findByNameContaining(name, pageable).getContent();
    }

    @Override
    public int sizeInBackend(String name) {
        return tenementRepository.countByNameContains(name);
    }

    @Override
    public Collection<Tenement> findTenementBySpace(DormitorySpace dormitorySpace) {
        List<DormitorySpace> spaces = dormitorySpaceService.listChildSpaceRecursive(dormitorySpace);
        return tenementRepository.findAllByDormitorySpaceIn(spaces);
    }

    @Override
    public int countTenementInSpace(DormitorySpace dormitorySpace) {
        List<DormitorySpace> spaces = dormitorySpaceService.listChildSpaceRecursive(dormitorySpace);
        return tenementRepository.countByDormitorySpaceIn(spaces);
    }

    private enum TenementServeType {
        //调换
        EXCHANGE,
        //搬出
        RETURN,
        //入住
        TAKEIN,
        //不变
        KEEP,
        //出错未知！
        UNKNOW
    }
}
