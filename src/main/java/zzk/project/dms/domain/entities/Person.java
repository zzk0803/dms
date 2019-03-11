package zzk.project.dms.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "domain_tenement_person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Enumerated(EnumType.STRING)
    private PersonGender gender;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private PersonContactMethod personContactMethod = new PersonContactMethod();

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

    public PersonGender getGender() {
        return this.gender;
    }

    public void setGender(PersonGender gender) {
        this.gender = gender;
    }

    public PersonContactMethod getPersonContactMethod() {
        return this.personContactMethod;
    }

    public void setPersonContactMethod(PersonContactMethod personContactMethod) {
        this.personContactMethod = personContactMethod;
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
        if (getPersonContactMethod() != null ? !getPersonContactMethod().equals(person.getPersonContactMethod()) : person.getPersonContactMethod() != null)
            return false;
        return getPersonIdentityID() != null ? getPersonIdentityID().equals(person.getPersonIdentityID()) : person.getPersonIdentityID() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        result = 31 * result + (getPersonContactMethod() != null ? getPersonContactMethod().hashCode() : 0);
        result = 31 * result + (getPersonIdentityID() != null ? getPersonIdentityID().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", personContactMethod=" + personContactMethod +
                ", personIdentityID='" + personIdentityID + '\'' +
                '}';
    }

}
