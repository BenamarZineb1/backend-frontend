package com.projet.Edoctorat.Auth.Models;

import jakarta.persistence.*;
import com.projet.Edoctorat.Auth.Models.AuthGroup;
@Entity
@Table(name = "auth_user_groups")
public class AuthUserGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private AuthGroup group;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }

    public AuthGroup getGroup() {
        return group;
    }

    public void setGroup(AuthGroup group) {
        this.group = group;
    }
}
