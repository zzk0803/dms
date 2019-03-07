package zzk.project.dms.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "domain_tenement")
public class Tenement extends Person {
    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private boolean valid;

    @OneToOne
    @JoinColumn(name = "berth_id")
    private DormitorySpace spot;
}
