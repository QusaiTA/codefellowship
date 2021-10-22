package com.example.codefellowship.codefellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String bio;

    @Column(unique = true)
    private String username;

    @OneToMany(mappedBy = "applicationUser")
    List<Post> posts;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "following_followers",
            joinColumns= {@JoinColumn(name = "following_id")},
            inverseJoinColumns= {@JoinColumn(name = "followers_id")}
    )
    private Set<ApplicationUser> following = new HashSet<>();

    @ManyToMany(mappedBy = "following")
    List<ApplicationUser> followers=new ArrayList<>();

    public List<Post> getPosts() {
        return posts;
    }
    public ApplicationUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio ) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;

    }

    public ApplicationUser() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }


    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<ApplicationUser> getFollowing() {
        return following;
    }

    public void setFollowing(Set<ApplicationUser> following) {
        this.following = following;
    }

    public List<ApplicationUser> getFollowers() {
        return followers;
    }

    public void setFollowers(List<ApplicationUser> followers) {
        this.followers = followers;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                '}';
    }
}
