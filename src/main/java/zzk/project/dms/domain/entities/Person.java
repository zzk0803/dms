package zzk.project.dms.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "domain_tenement_person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Enumerated(EnumType.STRING)
    private Gender gender;

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
    private ContactMethod contactMethod = new ContactMethod();

    public Person() {
    }

    private String personIdentityID;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ContactMethod getContactMethod() {
        return this.contactMethod;
    }

    public void setContactMethod(ContactMethod contactMethod) {
        this.contactMethod = contactMethod;
    }

    public String getPersonIdentityID() {
        return this.personIdentityID;
    }

    public void setPersonIdentityID(String personIdentityID) {
        this.personIdentityID = personIdentityID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (getId() != null ? !getId().equals(person.getId()) : person.getId() != null) return false;
        if (getName() != null ? !getName().equals(person.getName()) : person.getName() != null) return false;
        if (getGender() != person.getGender()) return false;
        if (getContactMethod() != null ? !getContactMethod().equals(person.getContactMethod()) : person.getContactMethod() != null)
            return false;
        return getPersonIdentityID() != null ? getPersonIdentityID().equals(person.getPersonIdentityID()) : person.getPersonIdentityID() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        result = 31 * result + (getContactMethod() != null ? getContactMethod().hashCode() : 0);
        result = 31 * result + (getPersonIdentityID() != null ? getPersonIdentityID().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", contactMethod=" + contactMethod +
                ", personIdentityID='" + personIdentityID + '\'' +
                '}';
    }

    public enum Gender {
        //男性
        MALE("男"),

        //女性
        FEMALE("女");

        private String cn;

        Gender(String cn) {
            this.cn = cn;
        }

        public String getCn() {
            return cn;
        }
    }
}
