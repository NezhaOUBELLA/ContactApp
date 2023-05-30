package com.example.contactapp;

public class Contact {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String job;

    public Contact(String name, String phone, String email, String job) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}

