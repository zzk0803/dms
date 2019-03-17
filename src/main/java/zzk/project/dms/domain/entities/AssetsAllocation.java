//package zzk.project.dms.domain.entities;
//
//import lombok.Data;
//
//import javax.persistence.*;
//
//@Data
//@Entity
//@Table(name = "domain_assets_allocation")
//public class AssetsAllocation {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "space_id")
//    private DormitorySpace dormitorySpace;
//
//    @OneToOne
//    @JoinColumn(name = "article_id")
//    private AssetsArticle assetsArticle;
//
//    @Column
//    private Integer articleNumber;
//}
