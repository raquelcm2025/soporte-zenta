package com.zenta.zenta.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "diagnosticos")
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean garantia;

    private LocalDateTime fechaRegistro;

    @Column(length = 1000)
    private String descripcionDiagnostico;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private User responsable;

    @OneToOne(mappedBy = "diagnostico", cascade = CascadeType.ALL)
    private Observacion observacion;


    @PrePersist
    public void prePersist() {
        fechaRegistro = LocalDateTime.now();
    }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Boolean getGarantia() {
		return garantia;
	}


	public void setGarantia(Boolean garantia) {
		this.garantia = garantia;
	}


	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public String getDescripcionDiagnostico() {
		return descripcionDiagnostico;
	}


	public void setDescripcionDiagnostico(String descripcionDiagnostico) {
		this.descripcionDiagnostico = descripcionDiagnostico;
	}


	public Ticket getTicket() {
		return ticket;
	}


	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}


	public User getResponsable() {
		return responsable;
	}


	public void setResponsable(User responsable) {
		this.responsable = responsable;
	}


	public Observacion getObservacion() {
		return observacion;
	}


	public void setObservacion(Observacion observacion) {
		this.observacion = observacion;
	}
    
    
}