package ec.sasf.sasfpruebamelinamacias.service.Impl;

import ec.sasf.sasfpruebamelinamacias.entity.Role;
import ec.sasf.sasfpruebamelinamacias.exception.BadRequestException;
import ec.sasf.sasfpruebamelinamacias.repository.RolRepository;
import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RoleService {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public List<Role> getRoles(){
        return this.rolRepository.getRoles();
    }

    @Transactional
    public Role getRoleById(Integer id){
        return this.rolRepository.getRole(id);
    }

    @Transactional
    public Role getRoleByNombre(String name){
        return this.rolRepository.getRoleByName(name);
    }

    @Transactional
    public Role createRole(Role role){
        Role rol = this.rolRepository.getRoleByName(role.getNombre());
        if(rol != null){
            throw new BadRequestException("El rol ya existe");
        }else{
            return this.rolRepository.createRole(role.getNombre(), role.getEstado());
        }
    }

    @Transactional
    public Role updateRole(Role role, Integer id){
        Role rol = this.rolRepository.getRole(id);
        if(rol == null){
            throw new BadRequestException("El rol no existe");
        }else{
            modelMapper.map(role, rol);
            return this.rolRepository.updateRole(rol.getId(), rol.getNombre(), rol.getEstado());
        }
    }


}
