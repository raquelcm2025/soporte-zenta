package com.zenta.zenta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zenta.zenta.entity.TipoDocumento;
import com.zenta.zenta.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Validar duplicado de número de documento
    boolean existsByNumDocumento(String numDocumento);

    // Búsqueda por nombre o apellido
    List<User> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);

    // Búsqueda combinada por nombre (opcional), rol (opcional) y tipo de documento (opcional)
    @Query("SELECT u FROM User u " +
    	       "WHERE " +
    	       "(:nombre IS NULL OR UPPER(u.nombre) LIKE CONCAT('%', UPPER(:nombre), '%')) AND " +
    	       "(:rol IS NULL OR u.rol.nombre = :rol) AND " +
    	       "(:tipoDocumento IS NULL OR u.tipoDocumento = :tipoDocumento)")
    	List<User> findByNombreContainingIgnoreCaseAndRolNombreOrAll(
    	    @Param("nombre") String nombre,
    	    @Param("rol") String rol,
    	    @Param("tipoDocumento") TipoDocumento tipoDocumento);
}