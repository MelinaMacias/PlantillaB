package ec.sasf.sasfpruebamelinamacias.controller;

import ec.sasf.sasfpruebamelinamacias.dto.JwtResponse;
import ec.sasf.sasfpruebamelinamacias.entity.Usuario;
import ec.sasf.sasfpruebamelinamacias.service.Impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signUp")
    public Usuario signUp(@Validated(Usuario.UserSignUp.class) @RequestBody  Usuario user){
        return this.authService.signup(user);
    }

    @PostMapping("/login")
    public JwtResponse loginUser(@Validated(Usuario.UserLogin.class) @RequestBody  Usuario user){
        return this.authService.login(user);

    }

}