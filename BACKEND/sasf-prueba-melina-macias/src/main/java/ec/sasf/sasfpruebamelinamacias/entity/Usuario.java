package ec.sasf.sasfpruebamelinamacias.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "MM_USER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

@NamedStoredProcedureQuery(
    name = "MM_GETUSERS",
    procedureName = "MM_GETUSERS",
    resultClasses = Usuario.class,
    parameters  = {
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class, name = "C_USERS")
    }
)

@NamedStoredProcedureQuery(
    name = "MM_GETUSERBYID",
    procedureName = "MM_GETUSERBYID",
    resultClasses = Usuario.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "ID_USER_IN"),
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class, name = "C_USER")
    }
)

@NamedStoredProcedureQuery(
    name = "MM_GETUSERBYEMAIL",
    procedureName = "MM_GETUSERBYEMAIL",
    resultClasses = Usuario.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "EMAIL_IN"),
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class, name = "C_USER")
    }

)

@NamedStoredProcedureQuery(
    name = "MM_CREATEUSER",
    procedureName = "MM_CREATEUSER",
    resultClasses = Usuario.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "ID_ROLE_IN"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "EMAIL_IN"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "ESTADO_IN"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "NOMBRE_IN"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "PASSWORD_IN"),
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class, name = "C_USER")
    }
)

@NamedStoredProcedureQuery(
    name = "MM_UPDATEUSER",
    procedureName = "MM_UPDATEUSER",
    resultClasses = Usuario.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "ID_USER_IN"),        
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "EMAIL_IN"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "ESTADO_IN"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "NOMBRE_IN"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "PASSWORD_IN"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "ID_ROLE_IN"),
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class, name = "C_USER")
    }

)

public class Usuario implements UserDetails {

    public interface UserLogin{
    }
    public interface UserCreate{
    }
    public interface UserSignUp{

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;
    @NotBlank(message = "Debe ingresar un nombre de usuario", groups = {UserCreate.class, UserSignUp.class})
    private String nombre;
    @NotBlank(message = "Debe ingresar un email", groups = {UserCreate.class, UserLogin.class, UserSignUp.class})
    private String email;
    @NotBlank(message = "Debe ingresar una contraseña", groups = {UserCreate.class, UserLogin.class, UserSignUp.class})
    private String contrasena;
    @JsonIgnore
    @Column(name = "fe_creacion")
    private Timestamp feCreacion;
    @JsonIgnore
    @Column(name = "fe_actualizacion")
    private Timestamp feActualizacion;
    private String estado;




    @NotEmpty(message = "Debe asignar al menos un rol al usuario", groups = {UserCreate.class})
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "MM_USER_ROLES",
            joinColumns = @JoinColumn(name = "ID_USER"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE"))

    private Set<Role> rolesAsignados;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role: rolesAsignados){
            authorities.add(new SimpleGrantedAuthority(role.getNombre()));
        }
        return authorities;
    }


    @PrePersist
    public void prePersist(){
        this.feCreacion = new Timestamp(new Date().getTime());
    }

    @PreUpdate
    public void preUpdate(){
        this.feActualizacion = new Timestamp(new Date().getTime());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.contrasena;
    }


    @JsonIgnore
    public String getContrasena() {
        return this.contrasena;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }


    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }


    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return false;
    }


    @JsonProperty
    public void setContrasena(String password) {
        this.contrasena = password;
    }


}