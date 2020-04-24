package com.example.tjman.rendezvouseventfeedlist;

public class UserProfile {
    public String firstName;
    public String lastName;
    public String age;
    public String email;
    public String number;

    public  UserProfile(){

    }

    public UserProfile (String firstName, String lastName, String age, String email, String number){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.number = number;
    }

    public String getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }
}
