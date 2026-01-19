package com.projet.Edoctorat.Auth.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "auth_permission")
@Data
public class AuthPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer content_type_id;
    private String codename;
}
