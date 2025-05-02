package com.terzicaglar.socialnetwork.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

public class UserProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private Integer age;
    private String userDefinedFields;
    private Boolean isFraud;

    public UserProfileDto() {
    }

    public UserProfileDto(Long id, String firstName, String lastName, String email, LocalDate dateOfBirth, Integer age,
                          String userDefinedFields, Boolean isFraud) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.userDefinedFields = userDefinedFields;
        this.isFraud = isFraud;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserDefinedFields() {
        return userDefinedFields;
    }

    public void setUserDefinedFields(String userDefinedFields) {
        this.userDefinedFields = userDefinedFields;
    }

    public Boolean getFraud() {
        return isFraud;
    }

    public void setFraud(Boolean fraud) {
        isFraud = fraud;
    }
}
