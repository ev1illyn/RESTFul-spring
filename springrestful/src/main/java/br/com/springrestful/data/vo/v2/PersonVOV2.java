package br.com.springrestful.data.vo.v2;

import java.util.Date;
import java.util.Objects;

public class PersonVOV2 {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonVOV2 personVO2 = (PersonVOV2) o;
        return Objects.equals(id, personVO2.id) && Objects.equals(firstName, personVO2.firstName) && Objects.equals(lastName, personVO2.lastName) && Objects.equals(address, personVO2.address) && Objects.equals(gender, personVO2.gender) && Objects.equals(birthDate, personVO2.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, gender, birthDate);
    }

    private Date birthDate;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    public PersonVOV2() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
