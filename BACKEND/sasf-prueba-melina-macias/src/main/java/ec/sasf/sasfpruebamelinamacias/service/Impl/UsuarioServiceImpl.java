package ec.sasf.sasfpruebamelinamacias.service.Impl;

import ec.sasf.sasfpruebamelinamacias.entity.Role;
import ec.sasf.sasfpruebamelinamacias.entity.Usuario;
import ec.sasf.sasfpruebamelinamacias.exception.BadRequestException;
import ec.sasf.sasfpruebamelinamacias.repository.UserRepository;
import ec.sasf.sasfpruebamelinamacias.service.UsuarioService;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDetailsService userDetailsService() {
        return email -> {
            Usuario user = this.userRepository.findByEmail(email);
            if (user != null) {
                return new User(user.getEmail(), user.getContrasena(), user.getAuthorities());
            } else {
                throw new BadRequestException("No existe usuario registrado con ese email");
            }

        };
    }

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<Usuario> getUsuarios() {
        return this.userRepository.getUsers();
    }

    @Transactional
    public Usuario getUsuarioById(Integer id) {
       return this.userRepository.getUsuarioById(id);
    }

    @Transactional
    public Usuario getUsuarioByEmail(String email) {
        return this.userRepository.getUsuarioByEmail(email);
    }

    @Transactional
    public Usuario createUsuario(Usuario user) {
        Usuario usuario = userRepository.getUsuarioByEmail(user.getEmail());
        if (usuario != null) {
            throw new BadRequestException("Correo ya en uso, intente uno nuevo.");
        } else {
            Role role = user.getRolesAsignados().stream().findFirst().get();
            return this.userRepository.createUsuario(role.getId(), 
                    user.getEmail() , user.getEstado(),
                    user.getNombre(), user.getContrasena());
        }
    }

    @Transactional
    public Usuario updateUsuario(Usuario user, Integer id) {
        Usuario usuario = userRepository.getUsuarioById(id);
        if (usuario == null) {
            throw new RuntimeException("El usuario que intenta editar no existe");
        } else {
            modelMapper.map(user, usuario);
            Role role = user.getRolesAsignados().stream().findFirst().get();
            return this.userRepository.updateUsuario(usuario.getId(), 
                    usuario.getEmail(), usuario.getEstado(),
                    usuario.getNombre(), usuario.getContrasena(),
                    role.getId());
        }
    }

}
