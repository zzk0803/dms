package zzk.project.dms.domain.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "domain_tenement_person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    public static enum Gender {
        //男性
        MALE,

        //女性
        FEMALE,

        //其他
        OTHERS
    }

    @Data
    @Embeddable
    public static class ContactMethod {
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

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated
    private Gender gender;

    private ContactMethod contactMethod;

    private String personIdentityID;
}
