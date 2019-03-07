package zzk.project.dms.domain.entities;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "domain_dormitory")
public class DormitorySpace {
    public enum SpaceType {
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

        SpaceType(String cn) {
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

        public SpaceType next() {
            return hasNext() ? SpaceType.values()[this.ordinal() + 1] : null;
        }

        public SpaceType previous() {
            return hasPrevious() ? SpaceType.values()[this.ordinal() - 1] : null;
        }
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String uuid;

    @ManyToOne
    @JoinTable(name = "domain_dormitory_spacecascade_relationship",
            joinColumns = @JoinColumn(name = "child_id", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "father_id", referencedColumnName = "uuid")
    )
    private DormitorySpace upperSpace;

    private boolean available = true;

    private boolean operational = true;

    private Integer capacity = 1;

    private Integer size = 0;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private SpaceType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DormitorySpace that = (DormitorySpace) o;

        if (getUuid() != null ? !getUuid().equals(that.getUuid()) : that.getUuid() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getType() == that.getType();

    }

    @Override
    public int hashCode() {
        int result = getUuid() != null ? getUuid().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DormitorySpace{" +
                "uuid='" + uuid + '\'' +
                ", available=" + available +
                ", operational=" + operational +
                ", capacity=" + capacity +
                ", size=" + size +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
