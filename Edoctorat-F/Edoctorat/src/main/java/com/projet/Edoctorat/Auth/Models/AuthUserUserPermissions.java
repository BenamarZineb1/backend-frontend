package com.projet.Edoctorat.Auth.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "auth_user_user_permissions")
public class AuthUserUserPermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private AuthPermission permission;

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

    public AuthPermission getPermission() {
        return permission;
    }

    public void setPermission(AuthPermission permission) {
        this.permission = permission;
    }
}

