package com.zenta.zenta.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zenta.zenta.entity.Diagnostico;
import com.zenta.zenta.entity.Observacion;

public interface ObservacionRepository extends JpaRepository<Observacion, Integer> {
	 @Query("SELECT d FROM Diagnostico d WHERE " +
	           "(:numero IS NULL OR d.ticket.numero LIKE %:numero%) AND " +
	           "(:garantia IS NULL OR d.garantia = :garantia) AND " +
	           "(:fecha IS NULL OR DATE(d.fechaRegistro) = :fecha)")
	    List<Diagnostico> buscarConFiltros(@Param("numero") String numero,
	                                       @Param("garantia") Boolean garantia,
	                                       @Param("fecha") LocalDate fecha);

	    Observacion findByDiagnostico(Diagnostico diagnostico);

	    @Query("SELECT o FROM Observacion o WHERE o.diagnostico = :diag")
	    Observacion obtenerPorDiagnostico(@Param("diag") Diagnostico diag);
	}