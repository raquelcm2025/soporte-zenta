package com.zenta.zenta.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Informe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigoInforme;

    private LocalDate fechaInforme;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "diagnostico_id")
    private Diagnostico diagnostico;

    @ManyToOne
    @JoinColumn(name = "observacion_id")
    private Observacion observacion;

    @ManyToOne
    @JoinColumn(name = "solucion_id")
    private Solucion solucion;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private User responsable;

    @PrePersist
    public void prePersist() {
        fechaInforme = LocalDate.now();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoInforme() {
		return codigoInforme;
	}

	public void setCodigoInforme(String codigoInforme) {
		this.codigoInforme = codigoInforme;
	}

	public LocalDate getFechaInforme() {
		return fechaInforme;
	}

	public void setFechaInforme(LocalDate fechaInforme) {
		this.fechaInforme = fechaInforme;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Diagnostico getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}

	public Observacion getObservacion() {
		return observacion;
	}

	public void setObservacion(Observacion observacion) {
		this.observacion = observacion;
	}

	public Solucion getSolucion() {
		return solucion;
	}

	public void setSolucion(Solucion solucion) {
		this.solucion = solucion;
	}

	public User getResponsable() {
		return responsable;
	}

	public void setResponsable(User responsable) {
		this.responsable = responsable;
	}

}
