package zzk.project.dms.domain.entities;

public enum  PurchaseType {
    /**
     * 一次性
     */
    ONCE("一次性"),

    /**
     * 每人
     */
    PERSON("每人"),

    /**
     * 按时间
     */
    PER_TIMING("按时间"),

    /**
     * 按给定量词
     */
    PER_UNIT("按给定量词");

    PurchaseType(String descript) {
        this.descript = descript;
    }

    private String descript;
}
