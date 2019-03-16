package zzk.project.dms.domain.entities;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString()
@Entity
@Table(name = "domain_dormitoryspace")
public class DormitorySpace {
    @Transient
    private static final int DEFAULT_CAPACITY = 1, DEFAULT_DIVIDED_REMAIN = 0, DEFAULT_HAS_OCCUPY = 0;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinTable(name = "domain_dormitory_spacecascade_relationship",
            joinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "father_id", referencedColumnName = "id")
    )
    private DormitorySpace parent;

    private boolean available = true;

    private boolean operational = true;

    private int capacity = DEFAULT_CAPACITY;

    private int hasDivided = DEFAULT_DIVIDED_REMAIN;

    private int hasOccupy = DEFAULT_HAS_OCCUPY;

    @EqualsAndHashCode.Include
    private String name;

    @Enumerated(value = EnumType.STRING)
    private DormitorySpaceType type;
}
