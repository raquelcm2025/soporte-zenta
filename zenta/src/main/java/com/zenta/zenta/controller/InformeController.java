package com.zenta.zenta.controller;

import com.zenta.zenta.entity.*;
import com.zenta.zenta.services.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/informe")
public class InformeController {

    @Autowired
    private InformeService informeService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private DiagnosticoService diagnosticoService;

    @Autowired
    private ObservacionService observacionService;

    @Autowired
    private SolucionService solucionService;

    @Autowired
    private UserServices userService;

    @GetMapping("/nuevo")
    public String mostrarFormularioInforme(Model model) {
        model.addAttribute("informe", new Informe());
        return "informe";
    }

    @GetMapping("/nuevo/{id}")
    public String cargarDatosDesdeTicket(@PathVariable Integer id, Model model, HttpSession session) {
        Ticket ticket = ticketService.obtenerPorId(id);

        if (ticket == null || !ticket.getEstado().equalsIgnoreCase("Concluido")) {
            return "redirect:/informe/nuevo";
        }

        Diagnostico diag = diagnosticoService.obtenerPorTicket(ticket).get(0);
        Observacion obs = observacionService.obtenerPorDiagnostico(diag);
        Solucion sol = solucionService.obtenerPorDiagnostico(diag);
        User responsable = (User) session.getAttribute("usuarioLogueado");

        Informe informe = new Informe();
        informe.setTicket(ticket);
        informe.setDiagnostico(diag);
        informe.setObservacion(obs);
        informe.setSolucion(sol);
        informe.setResponsable(responsable);

        model.addAttribute("informe", informe);
        return "informe";
    }

    @PostMapping("/guardar")
    public String guardarInforme(@ModelAttribute Informe informe, HttpSession session) {
        User usuario = (User) session.getAttribute("usuarioLogueado");

        if (usuario != null) {
            // Asegura que el responsable esté completo desde la BD
            User responsableCompleto = userService.obtenerPorId(usuario.getId());
            informe.setResponsable(responsableCompleto);
        }

        // Validación por seguridad
        if (informe.getTicket() != null) {
            Ticket ticketCompleto = ticketService.obtenerPorId(informe.getTicket().getId());
            informe.setTicket(ticketCompleto);
        }

        if (informe.getDiagnostico() != null) {
            Diagnostico diag = diagnosticoService.obtenerPorId(informe.getDiagnostico().getId());
            informe.setDiagnostico(diag);
        }

        if (informe.getObservacion() != null) {
            Observacion obs = observacionService.obtenerPorId(informe.getObservacion().getId());
            informe.setObservacion(obs);
        }

        if (informe.getSolucion() != null) {
            Solucion sol = solucionService.obtenerPorId(informe.getSolucion().getId());
            informe.setSolucion(sol);
        }

        informeService.guardar(informe);

        return "redirect:/informe/nuevo?exito"; // puedes mostrar un mensaje de éxito
    }

}
