package zzk.project.dms.ui.dormitory;

public enum DormitoryDivideApproach {
    Number("数量均分"),
    CapacityEqualization("容量均分"),
    Capacity("容量划分");
    private String cn;

    DormitoryDivideApproach(String cn) {
        this.cn = cn;
    }

    public String getCn() {
        return cn;
    }
}
