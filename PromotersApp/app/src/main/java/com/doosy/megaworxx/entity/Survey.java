package com.doosy.megaworxx.entity;

public class Survey {
    private String id;
    private String firstName;
    private String lastName;

    public Survey(String id, String firstName, String lsatName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lsatName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLsatName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName(){
        return firstName +" "+ lastName;
    }
}
