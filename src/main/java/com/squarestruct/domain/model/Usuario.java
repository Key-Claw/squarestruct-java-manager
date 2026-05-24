package com.squarestruct.domain.model;

import com.squarestruct.domain.enums.RolUsuario;

public class Usuario {

    private Long id;
    private String nombre;
    private String email;
    private String password;
    private RolUsuario rol;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String email, String password, RolUsuario rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }
}