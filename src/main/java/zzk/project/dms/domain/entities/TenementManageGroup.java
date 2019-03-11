package zzk.project.dms.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "domain_manage_group")
public class TenementManageGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String groupName;

    @OneToMany
    @JoinTable(name = "group_reporttarget_relationship")
    private List<Person> reportTargets = new ArrayList<>();

    @OneToMany
    @JoinTable(name = "group_tenement_relationship")
    private List<Tenement> tenements=new ArrayList<>();
}
