package com.example.codefellowship.codefellowship.models;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    String body;
    Date timeStamp;

    @ManyToOne
    @JoinColumn(name = "applicationUser_id")
    ApplicationUser applicationUser;

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Post(String body, Date timeStamp, ApplicationUser applicationUser) {
        this.body = body;
        this.timeStamp = timeStamp;
        this.applicationUser = applicationUser;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
