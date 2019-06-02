package zzk.project.dms.domain.entities;

public enum DormitorySpaceType {
    //社区
    COMMUNITY("社区"),

    //建筑
    BUILDING("建筑"),

    //楼层
    FLOOR("楼层"),

    //房间
    ROOM("房间"),

    //床位
    BERTH("床位");

    //未分区
//        UNCLASSIFIED(5, "未分区");

    private String cn;

    DormitorySpaceType(String cn) {
        this.cn = cn;
    }

    public String getCn() {
        return cn;
    }


    public boolean hasBigger() {
        int currentIndex = this.ordinal();
        return currentIndex != 0;
    }

    public boolean hasSmaller() {
        int currentIndex = this.ordinal();
        return currentIndex < values().length - 1;
    }

    public DormitorySpaceType smaller() {
        return hasSmaller() ? DormitorySpaceType.values()[this.ordinal() + 1] : null;
    }

    public DormitorySpaceType bigger() {
        return hasBigger() ? DormitorySpaceType.values()[this.ordinal() - 1] : null;
    }
}
