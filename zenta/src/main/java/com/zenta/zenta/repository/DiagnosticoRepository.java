package com.zenta.zenta.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zenta.zenta.entity.Diagnostico;
import com.zenta.zenta.entity.Ticket;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Integer> {


	List<Diagnostico> findByTicket(Ticket ticket);


	    @Query("SELECT d FROM Diagnostico d WHERE " +
	           "(:numero IS NULL OR d.ticket.numero = :numero) AND " +
	           "(:garantia IS NULL OR d.garantia = :garantia) AND " +
	           "(:fecha IS NULL OR DATE(d.fechaRegistro) = :fecha)")
	    List<Diagnostico> buscarConFiltros(@Param("numero") String numero,
	                                       @Param("garantia") Boolean garantia,
	                                       @Param("fecha") LocalDate fecha);
	    
	    @Query("SELECT d FROM Diagnostico d " +
	            "WHERE (:numero IS NULL OR d.ticket.numero LIKE %:numero%) " +
	            "AND (:garantia IS NULL OR d.garantia = :garantia) " +
	            "AND (:fecha IS NULL OR DATE(d.fechaRegistro) = DATE(:fecha))")
	     List<Diagnostico> filtrarDiagnosticos(
	         @Param("numero") String numero,
	         @Param("garantia") Boolean garantia,
	         @Param("fecha") LocalDateTime fecha
	     );
	    
	    @Query("SELECT d FROM Diagnostico d WHERE d.ticket IS NOT NULL AND d.id IN (SELECT o.diagnostico.id FROM Observacion o)")
	    List<Diagnostico> findWithObservaciones();

}
