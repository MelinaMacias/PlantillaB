package ec.sasf.sasfpruebamelinamacias.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "MM_ROLE")
@Builder
@NoArgsConstructor
@AllArgsConstructor

@NamedStoredProcedureQuery(
    name = "MM_GETROLES", 
    procedureName = "MM_GETROLES",
    resultClasses = Role.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class, name = "C_ROLES")
    }
)
@NamedStoredProcedureQuery(
    name = "MM_GETROLEBYID",
    procedureName = "MM_GETROLEBYID",
    resultClasses = Role.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "ID_ROLE_IN"),
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class, name = "C_ROLE")
    }
)

@NamedStoredProcedureQuery(
    name = "MM_GETROLEBYNAME",
    procedureName = "MM_GETROLEBYNAME",
    resultClasses = Role.class,
    parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "NOMBRE_IN"),
            @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class, name = "C_ROLE")
    }

)


@NamedStoredProcedureQuery(
    name = "MM_CREATEROLE", 
    procedureName = "MM_CREATEROLE",
    resultClasses = Role.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "NOMBRE_IN"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "ESTADO_IN"),
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class, name = "C_ROLE")
    }
)
@NamedStoredProcedureQuery(
    name = "MM_UPDATEROLE", 
    procedureName = "MM_UPDATEROLE",
    resultClasses = Role.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "ID_ROLE_IN"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "NOMBRE_IN"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "ESTADO_IN"),
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class, name = "C_ROLE")
    }
)

public class Role {


    public interface RoleCreate {
    }

    public interface RoleUpdate {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROLE")
    private Integer id;
    @NotBlank(message = "Debe ingresar un nombre de rol", groups = {RoleCreate.class})
    private String nombre;
    @Column(name = "fe_creacion")
    private Timestamp feCreacion;
    @Column(name = "fe_actualizacion")
    private Timestamp feActualizacion;
    private String estado;


    @PrePersist
    public void prePersist() {
        this.feCreacion = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.feActualizacion = new Timestamp(System.currentTimeMillis());
    }
}
