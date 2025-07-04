package com.zenta.zenta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.zenta.zenta.entity.User;
import com.zenta.zenta.services.UserServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class IniciarsesionController {

    @Autowired
    private UserServices userService;

    @GetMapping("/iniciarsesion")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuario", new User());
        return "iniciarsesion";
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute("usuario") User usuario,
                                HttpSession session,
                                Model model) {
        for (User u : userService.listarTodos()) {
            if (u.getEmail().equalsIgnoreCase(usuario.getEmail()) &&
                u.getContrasena().equals(usuario.getContrasena())) {

                session.setAttribute("usuarioLogueado", u);
                return "redirect:/dashboard";
            }
        }

        model.addAttribute("error", "Credenciales inválidas");
        return "iniciarsesion";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/iniciarsesion";
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        User usuario = (User) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/iniciarsesion";
        }

        model.addAttribute("usuario", usuario);
        return "index";  
    }

}
