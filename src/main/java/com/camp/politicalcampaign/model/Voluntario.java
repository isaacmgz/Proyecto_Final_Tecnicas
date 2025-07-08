package com.camp.politicalcampaign.model;

import java.time.LocalDateTime;

/**
 * Modelo de datos para un voluntario.
 */
public class Voluntario {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String departamento;
    private String ciudad;
    private String mensaje;
    private LocalDateTime fechaRegistro;

    public Voluntario() { }

    /**
     * Constructor completo.
     */
    public Voluntario(Long id, String nombre, String apellido, String email,
                      String telefono, String departamento, String ciudad,
                      String mensaje, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.mensaje = mensaje;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String toString() {
        return "Voluntario{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", apellido='" + apellido + '\'' +
               ", email='" + email + '\'' +
               ", telefono='" + telefono + '\'' +
               ", departamento='" + departamento + '\'' +
               ", ciudad='" + ciudad + '\'' +
               ", mensaje='" + mensaje + '\'' +
               ", fechaRegistro=" + fechaRegistro +
               '}';
    }
}
