package com.example.cursoSpring.dao;

import com.example.cursoSpring.Models.Usuario;

import java.util.List;

//DAO = Data access object, es la interfaz intermediaria de la conexión a la bd
public interface UsuarioDao {

    List<Usuario> getAllUsers();

    Usuario getUser(Usuario u);

    void eliminarUsuario(Long id);

    void registrar(Usuario u);

    Usuario iniciarSesion(Usuario u);

    List<Usuario> encontrados(String nombre);
}
