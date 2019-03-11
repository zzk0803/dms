package zzk.project.dms.domain.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "domain_dormitoryspace_template")
public class DormitorySpaceTemplate {
    @Transient
    private static final int DEFAULT_CAPACITY = 10000;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private DormitorySpaceType spaceType = DormitorySpaceType.COMMUNITY;

    @Column
    private Integer capacity = DEFAULT_CAPACITY;
}
