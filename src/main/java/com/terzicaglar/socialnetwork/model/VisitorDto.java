package com.terzicaglar.socialnetwork.model;

import java.sql.Timestamp;

public class VisitorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Timestamp visitedAt;

    public VisitorDto() {
    }

    public VisitorDto(Long id, String firstName, String lastName, Timestamp visitedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.visitedAt = visitedAt;
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

    public Timestamp getVisitedAt() {
        return visitedAt;
    }

    public void setVisitedAt(Timestamp visitedAt) {
        this.visitedAt = visitedAt;
    }
}
