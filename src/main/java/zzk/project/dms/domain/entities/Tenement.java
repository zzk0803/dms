package zzk.project.dms.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

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

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public TenementGender getGender() {
        return gender;
    }

    public void setGender(TenementGender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TenementContactMethod getTenementContactMethod() {
        return tenementContactMethod;
    }

    public void setTenementContactMethod(TenementContactMethod tenementContactMethod) {
        this.tenementContactMethod = tenementContactMethod;
    }

    public String getPersonIdentityID() {
        return personIdentityID;
    }

    public void setPersonIdentityID(String personIdentityID) {
        this.personIdentityID = personIdentityID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public DormitorySpace getDormitorySpace() {
        return dormitorySpace;
    }

    public void setDormitorySpace(DormitorySpace dormitorySpace) {
        this.dormitorySpace = dormitorySpace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tenement)) {
            return false;
        }

        Tenement tenement = (Tenement) o;

        if (getId() != null ? !getId().equals(tenement.getId()) : tenement.getId() != null) {
            return false;
        }
        if (getName() != null ? !getName().equals(tenement.getName()) : tenement.getName() != null) {
            return false;
        }
        if (getTenementContactMethod() != null ? !getTenementContactMethod().equals(tenement.getTenementContactMethod()) : tenement.getTenementContactMethod() != null) {
            return false;
        }
        return getPersonIdentityID() != null ? getPersonIdentityID().equals(tenement.getPersonIdentityID()) : tenement.getPersonIdentityID() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getTenementContactMethod() != null ? getTenementContactMethod().hashCode() : 0);
        result = 31 * result + (getPersonIdentityID() != null ? getPersonIdentityID().hashCode() : 0);
        return result;
    }


}
