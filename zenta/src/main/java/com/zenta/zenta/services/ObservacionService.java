package com.zenta.zenta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenta.zenta.entity.Diagnostico;
import com.zenta.zenta.entity.Observacion;
import com.zenta.zenta.repository.ObservacionRepository;

@Service
public class ObservacionService {

	@Autowired
    private ObservacionRepository repo;

    public Observacion guardar(Observacion observacion) {
        return repo.save(observacion);
    }

    public List<Observacion> listar() {
        return repo.findAll();
    }

    public Observacion obtenerPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Observacion obtenerPorDiagnostico(Diagnostico diag) {
        return repo.findByDiagnostico(diag);
    }
}