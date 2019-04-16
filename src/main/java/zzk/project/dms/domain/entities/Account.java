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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVaild() {
        return vaild;
    }

    public void setVaild(boolean vaild) {
        this.vaild = vaild;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }

        Account account = (Account) o;

        if (isVaild() != account.isVaild()) {
            return false;
        }
        if (getId() != null ? !getId().equals(account.getId()) : account.getId() != null) {
            return false;
        }
        if (getUsername() != null ? !getUsername().equals(account.getUsername()) : account.getUsername() != null) {
            return false;
        }
        return getPassword() != null ? getPassword().equals(account.getPassword()) : account.getPassword() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (isVaild() ? 1 : 0);
        return result;
    }
}
