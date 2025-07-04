package com.zenta.zenta.services;

import com.zenta.zenta.entity.Informe;
import com.zenta.zenta.repository.InformeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformeService {

    @Autowired
    private InformeRepository repo;

    public void guardar(Informe informe) {
    	repo.save(informe);
    }

    public List<Informe> listar() {
        return repo.findAll();
    }

    public Informe obtenerPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }
}
