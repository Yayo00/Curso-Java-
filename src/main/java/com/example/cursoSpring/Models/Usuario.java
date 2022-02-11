package com.example.cursoSpring.Models;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;

@Table(name="usuarios") //Relacionando la tabla de la BD con la clase Usuario
@Entity //Identificando que la clase Usuario es una entidad que será consultada por ConexionBD
@ToString
public class Usuario {

    public Usuario(long id,String nombre, String apellido, String email, String telefono, String pass) {
        this.id=id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.pass = pass;
    }

    public Usuario(){}
    //Lombok importa las métodos implícitos que pertenecen a una clase
    @Getter @Setter @Column(name="nombre")
    private String nombre;

    @Getter @Setter @Column(name="apellido")
    private String apellido;

    @Getter @Setter @Column(name="email")
    private String email;

    @Getter @Setter @Column(name="telefono")
    private String telefono;

    @Getter @Setter @Column(name="password")
    private String pass;

    @Getter @Setter @Column(name="id") @Id //Indica que es llave primaria
    @GeneratedValue(strategy=GenerationType.IDENTITY) //Indica que el valor es auto incrementable
    private long id;




}

