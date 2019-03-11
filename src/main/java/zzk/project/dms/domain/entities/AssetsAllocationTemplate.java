package zzk.project.dms.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@Data
@Entity
@Table(name = "domain_assets_allocation_template")
public class AssetsAllocationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private DormitorySpaceType spaceType;

    @ElementCollection
    @CollectionTable
    @MapKeyClass(AssetsArticle.class)
    private Map<AssetsArticle, Integer> assetAllocations = new HashMap<>();

    private boolean deprecated;
}
