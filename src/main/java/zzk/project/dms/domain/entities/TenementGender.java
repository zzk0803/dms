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

    public static TenementGender forCN(String cn) {
        if ("男".equals(cn)) {
            return MALE;
        } else if ("女".equals(cn)) {
            return FEMALE;
        }
        return null;
    }
}
