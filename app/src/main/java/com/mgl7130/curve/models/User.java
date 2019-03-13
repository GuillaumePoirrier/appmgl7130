package com.mgl7130.curve.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class User extends GenericEntity{

    private String firstName;
    private String lastName;
    private Timestamp birthDate;
    private String description;

    public User(){}
    public User(String firstName, String lastName, Timestamp birthDate, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.description = description;
    }


    public User(String firstName, String lastName, Timestamp birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(QueryDocumentSnapshot document) {
        super(document.getId());
        this.firstName = (String) document.getData().get("firstName");
        this.lastName = (String) document.getData().get("lastName");
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean hasFirstName(){return firstName != null;}

    public boolean hasLastName(){return lastName != null;}

    public boolean hasBirthDate(){return birthDate != null;}

    public boolean hasDescription(){return description != null;}
}
