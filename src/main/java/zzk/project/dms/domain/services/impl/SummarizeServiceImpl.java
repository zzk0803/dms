package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import zzk.project.dms.domain.dao.DormitorySpaceRepository;
import zzk.project.dms.domain.dao.TenementRepository;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.services.SummarizeService;
import zzk.project.dms.middle.ServiceAndSubscriber;

@ServiceAndSubscriber
public class SummarizeServiceImpl implements SummarizeService {

    @Autowired
    private DormitorySpaceRepository dormitorySpaceRepository;

    @Autowired
    private TenementRepository tenementRepository;

    @Override
    public int countCommunities() {
        return dormitorySpaceRepository.countByType(DormitorySpaceType.COMMUNITY);
    }

    @Override
    public int countBuildings() {
        return dormitorySpaceRepository.countByType(DormitorySpaceType.BUILDING);
    }

    @Override
    public int countRooms() {
        return dormitorySpaceRepository.countByType(DormitorySpaceType.ROOM);
    }

    @Override
    public int countBerths() {
        return dormitorySpaceRepository.countByType(DormitorySpaceType.BERTH);
    }

    @Override
    public int countResident() {
        return tenementRepository.countByDormitorySpaceIsNotNull();
    }

    @Override
    public int countPerson() {
        return (int) tenementRepository.count();
    }
}
