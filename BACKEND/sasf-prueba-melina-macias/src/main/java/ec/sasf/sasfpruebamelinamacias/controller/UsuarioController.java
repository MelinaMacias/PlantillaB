package ec.sasf.sasfpruebamelinamacias.controller;

import ec.sasf.sasfpruebamelinamacias.entity.Usuario;
import ec.sasf.sasfpruebamelinamacias.service.Impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping()
    public ResponseEntity<List<Usuario>> getAllUsuarios(){
        return new ResponseEntity<>(this.usuarioService.getUsuarios(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable("id") Integer id){
        return new ResponseEntity<>(this.usuarioService.getUsuarioById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Usuario> createUsuario(@Validated(Usuario.UserCreate.class) @RequestBody Usuario user){
        user.setContrasena(passwordEncoder.encode(user.getContrasena()));
        return new ResponseEntity<>(this.usuarioService.createUsuario(user), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario user, @PathVariable("id") Integer id){
        return new ResponseEntity<>(this.usuarioService.updateUsuario(user, id), HttpStatus.OK);

    }

}
