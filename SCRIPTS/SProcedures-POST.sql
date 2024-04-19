create or replace PROCEDURE MM_CREATEROLE (
    NAME_IN IN VARCHAR2,
    ESTADO_IN IN VARCHAR2,
    C_ROLE OUT SYS_REFCURSOR
)AS 
role_id number;
BEGIN
    INSERT INTO MM_ROLE(FE_ACTUALIZACION, FE_CREACION, ESTADO, NOMBRE)
    VALUES(null, CURRENT_TIMESTAMP, ESTADO_IN, NAME_IN) returning ID_ROLE into role_id;
    
    OPEN C_ROLE FOR
    SELECT * FROM MM_ROLE WHERE ID_ROLE = role_id;
    
END MM_CREATEROLE;
