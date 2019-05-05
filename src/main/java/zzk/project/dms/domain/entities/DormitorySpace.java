package zzk.project.dms.domain.entities;


import javax.persistence.*;

@Entity
@Table(name = "domain_dormitoryspace")
public class DormitorySpace {
    @Transient
    private static final int DEFAULT_CAPACITY = 1, DEFAULT_DIVIDED_REMAIN = 0, DEFAULT_HAS_OCCUPY = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinTable(name = "domain_dormitory_spacecascade_relationship",
            joinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "father_id", referencedColumnName = "id")
    )
    private DormitorySpace parent;

    @Column
    private boolean available = true;

    @Column
    private boolean operational = true;

    @Column
    private int capacity = DEFAULT_CAPACITY;

    @Column
    private int hasDivided = DEFAULT_DIVIDED_REMAIN;

    @Column
    private int hasOccupy = DEFAULT_HAS_OCCUPY;

    @Column
    private String name;

    @Enumerated(value = EnumType.STRING)
    private DormitorySpaceType type;

    public Long getId() {
        return this.id;
    }

    public DormitorySpace getParent() {
        return this.parent;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public boolean isOperational() {
        return this.operational;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getHasDivided() {
        return this.hasDivided;
    }

    public int getHasOccupy() {
        return this.hasOccupy;
    }

    public String getName() {
        return this.name;
    }

    public DormitorySpaceType getType() {
        return this.type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParent(DormitorySpace parent) {
        this.parent = parent;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setOperational(boolean operational) {
        this.operational = operational;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setHasDivided(int hasDivided) {
        this.hasDivided = hasDivided;
    }

    public void setHasOccupy(int hasOccupy) {
        this.hasOccupy = hasOccupy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(DormitorySpaceType type) {
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DormitorySpace)) {
            return false;
        }
        final DormitorySpace other = (DormitorySpace) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }


    protected boolean canEqual(final Object other) {
        return other instanceof DormitorySpace;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "DormitorySpace(id=" + this.getId() + ", available=" + this.isAvailable() + ", operational=" + this.isOperational() + ", capacity=" + this.getCapacity() + ", hasDivided=" + this.getHasDivided() + ", hasOccupy=" + this.getHasOccupy() + ", name=" + this.getName() + ")";
    }
}
