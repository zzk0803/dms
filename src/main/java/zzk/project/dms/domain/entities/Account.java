package zzk.project.dms.domain.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "system_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private boolean vaild = true;
}
