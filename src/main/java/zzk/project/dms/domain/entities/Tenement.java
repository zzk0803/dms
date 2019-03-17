package zzk.project.dms.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "domain_tenement")
public class Tenement {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TenementGender gender;

    private String name = "";

    private TenementContactMethod tenementContactMethod = new TenementContactMethod();

    private String personIdentityID;

    private LocalDate startDate;

    private LocalDate expiredDate;

    private boolean valid = true;

    @OneToOne
    @JoinColumn(name = "berth_id")
    private DormitorySpace dormitorySpace;
}
