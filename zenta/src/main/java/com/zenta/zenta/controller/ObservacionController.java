package com.zenta.zenta.controller;

import com.zenta.zenta.entity.Observacion;
import com.zenta.zenta.entity.Diagnostico;
import com.zenta.zenta.entity.Ticket;
import com.zenta.zenta.entity.User;
import com.zenta.zenta.services.ObservacionService;
import com.zenta.zenta.services.DiagnosticoService;
import com.zenta.zenta.services.TicketService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/observacion")
public class ObservacionController {

    @Autowired
    private ObservacionService observacionService;

    @Autowired
    private DiagnosticoService diagnosticoService;

    @Autowired
    private TicketService ticketService;

    //  formulario vacío 
    @GetMapping("/nuevo")
    public String nuevaObservacionVacia(Model model, HttpSession session) {
        Observacion observacion = new Observacion();
        User responsable = (User) session.getAttribute("usuarioLogueado");
        observacion.setResponsable(responsable);

        model.addAttribute("observacion", observacion);
        model.addAttribute("listaDiagnosticos", diagnosticoService.listar());
        return "observacion_form";
    }

    //  formulario con datos del diagnóstico seleccionado
    @GetMapping("/nuevo/{id}")
    public String nuevaObservacionConDiagnostico(@PathVariable Integer id, Model model, HttpSession session) {
        Observacion observacion = new Observacion();
        Diagnostico diagnostico = diagnosticoService.obtenerPorId(id);
        User responsable = (User) session.getAttribute("usuarioLogueado");

        observacion.setDiagnostico(diagnostico);
        observacion.setResponsable(responsable);

        model.addAttribute("observacion", observacion);
        model.addAttribute("listaDiagnosticos", diagnosticoService.listar());
        return "observacion_form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("observacion") Observacion observacion) {
        // Recuperar el diagnóstico completo por su ID
        Diagnostico diagnosticoCompleto = diagnosticoService.obtenerPorId(observacion.getDiagnostico().getId());
        observacion.setDiagnostico(diagnosticoCompleto);

        // Recuperar ticket y actualizar estado
        Ticket ticket = diagnosticoCompleto.getTicket();
        if (ticket != null) {
            ticket.setEstado("En proceso");
            ticketService.guardar(ticket);
        }

        // Setear la fecha y guardar observación
        observacion.setFechaRegistro(LocalDateTime.now());
        observacionService.guardar(observacion);

        return "redirect:/";
    }



    // Buscar diagnósticos con filtros
    @GetMapping("/buscar")
    public String buscarDiagnosticos(
        @RequestParam(required = false) String numero,
        @RequestParam(required = false) Boolean garantia,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
        Model model,
        HttpSession session
    ) {
        List<Diagnostico> listaDiagnosticos = diagnosticoService.filtrarDiagnosticos(numero, garantia, fecha);
        Observacion observacion = new Observacion();

        User responsable = (User) session.getAttribute("usuarioLogueado");
        observacion.setResponsable(responsable);

        model.addAttribute("observacion", observacion);
        model.addAttribute("listaDiagnosticos", listaDiagnosticos);
        return "observacion_form";
    }
}
