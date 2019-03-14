package zzk.project.dms.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "domain_financial_record")
public class FinancialRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate recordDate = LocalDate.now();

    @OneToOne
    private Tenement tenement;

    private BigDecimal checkIn = BigDecimal.ZERO;

    private String description;

    private boolean mark;
}
