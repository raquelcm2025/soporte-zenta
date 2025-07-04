package com.zenta.zenta.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenta.zenta.entity.Ticket;
import com.zenta.zenta.repository.TicketRepository;

@Service
public class TicketService {
	@Autowired
    private TicketRepository repo;

    
    public Ticket guardar(Ticket ticket) {
        return repo.save(ticket);
    }

    
    public List<Ticket> listar() {
        return repo.findAll();
    }

    public Ticket obtenerPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }
    public String generarNumeroTicket() {
        long total = repo.count() + 1;
        return String.format("TCK-%04d", total);
    }
    
    public List<Ticket> listarTodos() {
        return repo.findAll(); 
    }

    public List<Ticket> filtrarTickets(String numero, String prioridad, String estado, LocalDate fecha) {
        return repo.filtrarTickets(
                numero != null && !numero.isBlank() ? numero : null,
                prioridad != null && !prioridad.isBlank() ? prioridad : null,
                estado != null && !estado.isBlank() ? estado : null,
                fecha
        );
    }

}
