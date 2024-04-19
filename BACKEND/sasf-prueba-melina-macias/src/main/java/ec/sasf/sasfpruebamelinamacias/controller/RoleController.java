package ec.sasf.sasfpruebamelinamacias.controller;

import ec.sasf.sasfpruebamelinamacias.entity.Role;
import ec.sasf.sasfpruebamelinamacias.entity.Role.RoleCreate;
import ec.sasf.sasfpruebamelinamacias.service.Impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(this.roleService.getRoles(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") Integer id){
        return new ResponseEntity<>(this.roleService.getRoleById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@Validated(RoleCreate.class) @RequestBody Role role){
        return new ResponseEntity<>(this.roleService.createRole(role), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") Integer id,
                                           @RequestBody Role role){
        return new ResponseEntity<>(this.roleService.updateRole(role , id), HttpStatus.OK);
    }


}
