package ec.sasf.sasfpruebamelinamacias.repository;

import ec.sasf.sasfpruebamelinamacias.entity.Role;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Role, Integer> {


    @Procedure(name = "MM_GETROLES")
    List<Role> getRoles();

    @Procedure(name = "MM_GETROLEBYID")
    Role getRole(@Param("ID_ROLE_IN") Integer ID_ROLE_IN);
    
    @Procedure(name = "MM_GETROLEBYNAME")
    Role getRoleByName(@Param("NOMBRE_IN") String NOMBRE_IN);
    
    @Procedure(name = "MM_CREATEROLE")
    Role createRole(@Param("NOMBRE_IN") String NOMBRE_IN,@Param("ESTADO_IN") String ESTADO_IN);

    @Procedure(name = "MM_UPDATEROLE")
    Role updateRole(@Param("ID_ROLE_IN") Integer ID_ROLE_IN,@Param("NOMBRE_IN") String NOMBRE_IN,
        @Param("ESTADO_IN") String ESTADO_IN);

    

}
