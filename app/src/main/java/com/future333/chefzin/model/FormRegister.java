package com.future333.chefzin.model;

/**
 * Created by manuel on 10/10/16.
 */
public class FormRegister {

    private String  nombres;
    private String  apellidos;
    private String  email;
    private String  password;
    private String  password_confirmation;
    private String  telefono;
    private boolean isCheckTerm;

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isCheckTerm() {
        return isCheckTerm;
    }

    public void setCheckTerm(boolean checkTerm) {
        isCheckTerm = checkTerm;
    }
}
