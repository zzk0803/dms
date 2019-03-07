package zzk.project.dms.domain.dto;

import lombok.Data;

@Data
public class DormitoryStrategy {
    private int berthPerRoom;
    private int roomPerFloor;
    private int floorPerBuilding;
    private int buildingNumber;
}
