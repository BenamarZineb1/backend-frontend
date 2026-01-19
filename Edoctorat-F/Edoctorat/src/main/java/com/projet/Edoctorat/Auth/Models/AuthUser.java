package com.projet.Edoctorat.Auth.Models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "auth_user")
public class AuthUser {
    
    @Column(nullable = false)
    private String role;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private Boolean isStaff;
    private Boolean isSuperuser;
    private Date lastLogin;
    private Date dateJoined;
    
    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getRole() { return role; }
    public Boolean getIsActive() { return isActive; }
    public Boolean getIsStaff() { return isStaff; }
    public Boolean getIsSuperuser() { return isSuperuser; }
    public Date getLastLogin() { return lastLogin; }
    public Date getDateJoined() { return dateJoined; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setRole(String role) { this.role = role; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public void setIsStaff(Boolean isStaff) { this.isStaff = isStaff; }
    public void setIsSuperuser(Boolean isSuperuser) { this.isSuperuser = isSuperuser; }
    public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }
    public void setDateJoined(Date dateJoined) { this.dateJoined = dateJoined; }
}