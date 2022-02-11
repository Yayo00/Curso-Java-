package com.example.cursoSpring.dao;


import com.example.cursoSpring.Models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository //indica que esta clase será la que conecte con la BD
@Transactional //Manejo de las consultas SQL NOTA: se deben instalar dependencias h2 database y JDBC spring

public class ConexionBD implements UsuarioDao{

    @PersistenceContext //Genera el contexto de consultas a la entidad
    private EntityManager entitymanager; //generador de consultas

    //Consultar
    @Override
    public List<Usuario> getAllUsers() {
        //Las consultas en maven se realizan a la entidad(clase) creada
        return entitymanager.createQuery("FROM Usuario").getResultList();//se guarda la consulta como lista
    }


    //Eliminar
    @Override
    public void eliminarUsuario(Long id) {
        //Primero se busca
        Usuario encontrado =entitymanager.find(Usuario.class,id);

        //Luego se elimina
        entitymanager.remove(encontrado);
    }

    //Registrar
    @Override
    public void registrar(Usuario u) {
        //registrando...
        entitymanager.merge(u);
    }

    @Override
    public Usuario iniciarSesion(Usuario u) {

        List<Usuario>lista = entitymanager.createQuery("FROM Usuario WHERE email= :email")
                .setParameter("email",u.getEmail())
                .getResultList();
        
        if(lista.get(0)!=null){
            Argon2 verificar = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

            //Verifica que la contraseña es la misma con el hash
            if(verificar.verify(lista.get(0).getPass(),u.getPass())){
                return lista.get(0);
            }
        }
        return null;

    }

    @Override
    public List<Usuario> encontrados(String nombre) {
        List<Usuario> lista = entitymanager.createQuery("FROM Usuario WHERE nombre= :nombre").
                setParameter("nombre",nombre).getResultList();
        if(!lista.isEmpty()){
            return lista;
        }
        return new ArrayList<>();
    }

    @Override
    public Usuario getUser(Usuario u) {
        List<Usuario> lista = entitymanager.createQuery("FROM Usuario WHERE email= :email").
                setParameter("email",u.getEmail()).getResultList();

        if(!lista.isEmpty()){
            return lista.get(0);
        }
        return null;
    }





}
