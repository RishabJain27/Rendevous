package com.example.tjman.rendezvouseventfeedlist;

public class Hobby {
    public String hobby;
    public String hobbyimg;

    public Hobby(String hobby, String hobbyimg) {
        this.hobby = hobby;
        this.hobbyimg = hobbyimg;
    }
    public Hobby(){

    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getHobbyimg() {
        return hobbyimg;
    }

    public void setHobbyimg(String hobbyimg) {
        this.hobbyimg = hobbyimg;
    }
}
