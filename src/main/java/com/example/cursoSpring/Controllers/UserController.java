package com.example.cursoSpring.Controllers;

//un controlador maneja peticiones

import com.example.cursoSpring.Models.Usuario;
import com.example.cursoSpring.Utils.JWTUtils;
import com.example.cursoSpring.dao.UsuarioDao;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController //Indicando que va a controlar todas las peticiones entrantes


public class UserController{

    @Autowired //Une dependencias entre un repositorio(en este caso es ConexionBD)
    // y un RestController(siendo un servicio de consumo)
    UsuarioDao users;

    @Autowired
    JWTUtils jwtutil;

    @RequestMapping(value="nombre",method = RequestMethod.POST)

    public String getUser(@RequestBody Usuario u, @RequestHeader(value="Authorization") String token){
        if(jwtutil.getKey(token)!=null){
            //Obtener el nombre del usuario
            System.out.println(u);
            return users.getUser(u).getNombre();
        }
        return "";
    }

    //Obtener todos
    @RequestMapping(value="users", method = RequestMethod.GET)

    public List<Usuario> getAllUsers(@RequestHeader(value="Authorization") String token){

        //Si la token no es nula, o sea existe
        if(jwtutil.getKey(token)!=null){
            return users.getAllUsers();
        }
        return new ArrayList<>();
    }


    //Eliminar usuario
    @RequestMapping(value="user/{id}", method= RequestMethod.DELETE)

    public void eliminarUser(@PathVariable Long id, @RequestHeader(value="Authorization") String token){
        if(jwtutil.getKey(token)!=null){
            users.eliminarUsuario(id);
        }

    }

    //Registrar usuario
    @RequestMapping(value="registrar", method = RequestMethod.POST)

    //@RequestBody Convierte el obj usuario a un JSON
    public void registrarUsuario(@RequestBody Usuario u){
        //Encriptar contraseña
        Argon2 encriptar = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        //Generar el hash a través del password del usuario
        String hash = encriptar.hash(1,1024,1,u.getPass());
        u.setPass(hash);

        users.registrar(u);

    }

    //Iniciar sesión

    @RequestMapping(value="login",method = RequestMethod.POST)

    public String iniciarSesion(@RequestBody Usuario u){

        Usuario usuarioIniciado =users.iniciarSesion(u);

        if(usuarioIniciado!=null){

            //Crear el token a partir del id del usuario y email(generango el algoritmo con esos datos)
            String tokencreada= jwtutil.create(String.valueOf(usuarioIniciado.getId()),usuarioIniciado.getEmail());
            return tokencreada;

        }
        return "";
    }

    @RequestMapping(value = "search/{nombre}",method = RequestMethod.GET)
    public List<Usuario> encontrados(@PathVariable String nombre,@RequestHeader String token){
        if(jwtutil.getKey(token)!=null){
            return users.encontrados(nombre);
        }
        return new ArrayList<>();
    }
}
