package ec.sasf.sasfpruebamelinamacias.repository;

import ec.sasf.sasfpruebamelinamacias.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);

    @Procedure(name = "MM_GETUSERS")
    List<Usuario> getUsers();

    @Procedure(name = "MM_GETUSERBYID")
    Usuario getUsuarioById(@Param("ID_USER_IN") Integer ID_USER_IN);

    @Procedure(name = "MM_GETUSERBYEMAIL")
    Usuario getUsuarioByEmail(@Param("EMAIL_IN") String EMAIL_IN);

    @Procedure(name = "MM_CREATEUSER")
    Usuario createUsuario(
            @Param("ID_ROLE_IN") Integer ID_ROLE_IN,
            @Param("EMAIL_IN") String EMAIL_IN,
            @Param("ESTADO_IN") String ESTADO_IN,
            @Param("NOMBRE_IN") String NOMBRE_IN,
            @Param("PASSWORD_IN") String PASSWORD_IN
    );

    @Procedure(name = "MM_UPDATEUSER")
    Usuario updateUsuario(
            @Param("ID_USER_IN") Integer ID_USER_IN,
            @Param("EMAIL_IN") String EMAIL_IN,
            @Param("ESTADO_IN") String ESTADO_IN,
            @Param("NOMBRE_IN") String NOMBRE_IN,
            @Param("PASSWORD_IN") String PASSWORD_IN,
            @Param("ID_ROLE_IN") Integer ID_ROLE_IN
    );


}
