package ec.sasf.sasfpruebamelinamacias.service.Impl;

import ec.sasf.sasfpruebamelinamacias.config.Auth.JwtUtil;
import ec.sasf.sasfpruebamelinamacias.dto.JwtResponse;
import ec.sasf.sasfpruebamelinamacias.entity.Role;
import ec.sasf.sasfpruebamelinamacias.entity.Usuario;
import ec.sasf.sasfpruebamelinamacias.exception.BadRequestException;
import ec.sasf.sasfpruebamelinamacias.repository.RolRepository;
import ec.sasf.sasfpruebamelinamacias.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public Usuario signup(Usuario user){

        Usuario usuario = userRepository.getUsuarioByEmail(user.getEmail());
        if(usuario!=null){
            throw new BadRequestException("Correo ya en uso.\n Intente uno nuevo");
        }
        else{
            Set<Role> roles = new HashSet<>();
            Role role = rolRepository.findById(2).orElse(null);
            if(role==null){
                throw new BadRequestException("El rol que intenta asignar no existe");
            }
            roles.add(role);
            var usr = Usuario.builder()
                    .nombre(user.getNombre())
                    .email(user.getEmail())
                    .estado("A")
                    .rolesAsignados(roles)
                    .contrasena(passwordEncoder.encode(user.getPassword()))
                    .build();
            return userRepository.save(usr);
        }
    }

    @Transactional
    public JwtResponse login(Usuario user){

        Usuario usuario = userRepository.getUsuarioByEmail(user.getEmail());
        if(usuario==null){
            throw new BadRequestException("Error al iniciar sesion");
        }
        else{
            if(usuario.getEstado().equals("N")){
                throw new BadRequestException("Error al iniciar sesion");
            }
            else{
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getContrasena(),usuario.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
                try{
                    authenticationManager.authenticate(authToken);
                    var jwt = jwtUtil.generatedToken(usuario);
                    return JwtResponse.builder().token(jwt).build();
                }
                catch(BadCredentialsException e){
                    throw new BadCredentialsException("Las credenciales ingresadas son erroneas!\nIntentenlo nuevamente");
                }
            }

        }




    }

}
