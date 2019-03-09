package zzk.project.dms.domain.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "domain_tenement")
public class Tenement extends Person {
    private LocalDate startDate;

    private LocalDate expiredDate;

    private boolean valid = true;

    @OneToOne
    @JoinColumn(name = "berth_id")
    private DormitorySpace spot;

    public Tenement() {
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpiredDate() {
        return this.expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public DormitorySpace getSpot() {
        return this.spot;
    }

    public void setSpot(DormitorySpace spot) {
        this.spot = spot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tenement)) return false;
        if (!super.equals(o)) return false;

        Tenement tenement = (Tenement) o;

        if (isValid() != tenement.isValid()) return false;
        if (getStartDate() != null ? !getStartDate().equals(tenement.getStartDate()) : tenement.getStartDate() != null)
            return false;
        if (getExpiredDate() != null ? !getExpiredDate().equals(tenement.getExpiredDate()) : tenement.getExpiredDate() != null)
            return false;
        return getSpot() != null ? getSpot().equals(tenement.getSpot()) : tenement.getSpot() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getExpiredDate() != null ? getExpiredDate().hashCode() : 0);
        result = 31 * result + (isValid() ? 1 : 0);
        result = 31 * result + (getSpot() != null ? getSpot().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tenement{" +
                "startDate=" + startDate +
                ", expiredDate=" + expiredDate +
                ", valid=" + valid +
                ", spot=" + spot.getName() +
                '}';
    }
}
