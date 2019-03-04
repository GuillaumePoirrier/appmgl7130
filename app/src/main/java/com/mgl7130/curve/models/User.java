package com.mgl7130.curve.models;

import com.google.firebase.firestore.QueryDocumentSnapshot;


public class User {

    private transient String id;
    private String firstName;
    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(QueryDocumentSnapshot document) {
        this.id = document.getId();
        this.firstName = (String) document.getData().get("firstName");
        this.lastName = (String) document.getData().get("lastName");
    }

    public String getId() { return id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

}
