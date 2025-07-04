package com.zenta.zenta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;

import com.zenta.zenta.entity.TipoDocumento;
import com.zenta.zenta.entity.User;
import com.zenta.zenta.services.RolServices;
import com.zenta.zenta.services.UserServices;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private RolServices rolTipo;

    @RequestMapping("/registrarusuarios")
    public String listar(Model model) {
        model.addAttribute("lista", userServices.listarTodos());
        model.addAttribute("roles", rolTipo.listarTodos());
        model.addAttribute("tiposDocumento", TipoDocumento.values());
        model.addAttribute("user", new User());

        // para mantener filtro en la vista aunque sea lista completa
        model.addAttribute("param", new ParamFiltro());

        return "user";
    }

    @RequestMapping("/guardar")
    public String guardar(@ModelAttribute("user") User bean,
                          RedirectAttributes redirect) {
        try {
            if (bean.getId() == null) {
                userServices.registrar(bean);
                redirect.addFlashAttribute("MENSAJE", "Usuario Registrado");
            } else {
                userServices.actualizar(bean);
                redirect.addFlashAttribute("MENSAJE", "Usuario Actualizado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/usuarios";
    }

    @RequestMapping("/buscar")
    @ResponseBody
    public User buscar(@RequestParam("id") Integer id) {
        return userServices.buscarPorID(id);
    }

    @RequestMapping("/buscarPorNombre")
    public String buscarPorNombre(
        @RequestParam(name = "nombre", required = false) String nombre,
        @RequestParam(name = "rol", required = false) String rol,
        @RequestParam(name = "tipoDoc", required = false) String tipoDoc,
        Model model
    ) {
        List<User> datosFiltrados = userServices.buscarPorFiltros(nombre, rol, tipoDoc);

        model.addAttribute("lista", datosFiltrados);
        model.addAttribute("roles", rolTipo.listarTodos());
        model.addAttribute("tiposDocumento", TipoDocumento.values());
        model.addAttribute("user", new User());

        // Agregar filtros actuales a la vista para mantener selección en el HTML
        ParamFiltro filtrosActuales = new ParamFiltro();
        filtrosActuales.setNombre(nombre);
        filtrosActuales.setRol(rol);
        filtrosActuales.setTipoDoc(tipoDoc);
        model.addAttribute("param", filtrosActuales);

        if (datosFiltrados.isEmpty()) {
            model.addAttribute("MENSAJE", "No se encontraron resultados");
        }

        return "user";
    }

    @GetMapping("/validarDocumento")
    @ResponseBody
    public boolean validarDocumento(@RequestParam("numDocumento") String numDocumento) {
        return userServices.existsByNumDocumento(numDocumento);
    }

    @PostMapping("/eliminar")
    @ResponseBody
    public ResponseEntity<String> eliminarUsuario(@RequestParam("id") Integer id) {
        try {
            userServices.eliminarPorId(id);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar");
        }
    }

    // Clase interna para transportar los filtros a la vista
    public static class ParamFiltro {
        private String nombre;
        private String rol;
        private String tipoDoc;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getRol() {
            return rol;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }

        public String getTipoDoc() {
            return tipoDoc;
        }

        public void setTipoDoc(String tipoDoc) {
            this.tipoDoc = tipoDoc;
        }
    }
}