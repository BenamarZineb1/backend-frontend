package com.projet.Edoctorat.Auth.Models;

import jakarta.persistence.*;
import lombok.*;
import com.projet.Edoctorat.Auth.Models.AuthPermission;
@Entity
@Data
@Table(name = "auth_group_permissions")
public class AuthGroupPermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private AuthGroup group;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private AuthPermission permission;
}

