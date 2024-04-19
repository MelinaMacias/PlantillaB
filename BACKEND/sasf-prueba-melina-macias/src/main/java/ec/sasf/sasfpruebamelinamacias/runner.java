package ec.sasf.sasfpruebamelinamacias;

import ec.sasf.sasfpruebamelinamacias.entity.Role;
import ec.sasf.sasfpruebamelinamacias.entity.Usuario;
import ec.sasf.sasfpruebamelinamacias.repository.RolRepository;
import ec.sasf.sasfpruebamelinamacias.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class runner implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        this.createRoles();
        this.createUsers();
    }

    private void createRoles() {
        ArrayList<String> roles = new ArrayList<>(Arrays.asList("ADMIN", "USER" ));

        roles.forEach(role ->
                this.rolRepository.save(
                        Role.builder()
                                .nombre(role)
                                .estado("A")
                                .feCreacion(new Timestamp(System.currentTimeMillis()))
                                .build()
                )
        );

    }


    private void createUsers() {
        ArrayList<String> userNames = new ArrayList<>(Arrays.asList("user1", "user2"));
        userNames.forEach(name -> {
            Set<Role> roles = new HashSet<>();
            Role role = new Role();
            if(name.equals("user1"))
            {
                role = this.rolRepository.findById(1).orElse(null);
            }else if(name.equals("user2")){
                role = this.rolRepository.findById(2).orElse(null);

            }

            roles.add(role);

            Usuario user = Usuario.builder()
                    .nombre(name)
                    .email(name + "@gmail.com")
                    .contrasena(passwordEncoder.encode("12345"))
                    .estado("A")
                    .rolesAsignados(roles)
                    .feCreacion(new Timestamp(System.currentTimeMillis()))
                    .build();
            userRepository.save(user);

        });

    }



}
