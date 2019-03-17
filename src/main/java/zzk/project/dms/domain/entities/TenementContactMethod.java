package zzk.project.dms.domain.entities;

import javax.persistence.Embeddable;

@Embeddable
public  class TenementContactMethod {
    private String houseTelephone;
    private String mobilePhone;
    private String primaryEmail;

    public String getHouseTelephone() {
        return houseTelephone;
    }

    public void setHouseTelephone(String houseTelephone) {
        this.houseTelephone = houseTelephone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }
}
