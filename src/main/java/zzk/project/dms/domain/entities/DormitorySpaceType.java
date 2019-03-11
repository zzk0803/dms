package zzk.project.dms.domain.entities;

public enum DormitorySpaceType {
    //床位
    BERTH("床位"),

    //房间
    ROOM("房间"),

    //楼层
    FLOOR("楼层"),

    //建筑
    BUILDING("建筑"),

    //社区
    COMMUNITY("社区");

    //未分区
//        UNCLASSIFIED(5, "未分区");

    DormitorySpaceType(String cn) {
        this.cn = cn;
    }

    private String cn;

    public String getCn() {
        return cn;
    }


    public boolean hasPrevious() {
        return this.ordinal() != 0;
    }

    public boolean hasNext() {
        return this.ordinal() != 4;
    }

    public DormitorySpaceType next() {
        return hasNext() ? DormitorySpaceType.values()[this.ordinal() + 1] : null;
    }

    public DormitorySpaceType previous() {
        return hasPrevious() ? DormitorySpaceType.values()[this.ordinal() - 1] : null;
    }
}
