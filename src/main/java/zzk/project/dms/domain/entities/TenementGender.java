package zzk.project.dms.domain.entities;

public enum TenementGender {
    //男性
    MALE("男"),

    //女性
    FEMALE("女");

    private String cn;

    TenementGender(String cn) {
        this.cn = cn;
    }

    public String getCn() {
        return cn;
    }
}
