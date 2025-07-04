package com.zenta.zenta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditarperfilesController {
	@GetMapping("/editarperfiles")
	public String editarperfiles() {
        return "editarperfiles";
    }
}
