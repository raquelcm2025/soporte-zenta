package com.zenta.zenta.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.zenta.zenta.entity.Diagnostico;
import com.zenta.zenta.entity.Ticket;
import com.zenta.zenta.repository.DiagnosticoRepository;

@Service
public class DiagnosticoService  {

    @Autowired
    private DiagnosticoRepository repo;

    // Guardar o actualizar diagnóstico
    public Diagnostico guardar(Diagnostico diagnostico) {
        return repo.save(diagnostico);
    }

    // Obtener diagnóstico por ID
    public Diagnostico obtenerPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    // Listar todos los diagnósticos 
    public List<Diagnostico> listar() {
        return repo.findAll(); 
    }

    // Filtrar diagnósticos con campos opcionales (para modal)
    public List<Diagnostico> filtrarDiagnosticos(String numero, Boolean garantia, LocalDateTime fecha) {
        return repo.filtrarDiagnosticos(numero, garantia, fecha);
    }

    
    
    public List<Diagnostico> obtenerTicketsConDiagnosticoYObservacion() {
        return repo.findAll()
                .stream()
                .filter(d -> d.getObservacion() != null)
                .collect(Collectors.toList());
    }

    public List<Diagnostico> obtenerPorTicket(Ticket ticket) {
        return repo.findByTicket(ticket);
    }

}