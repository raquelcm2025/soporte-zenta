package com.zenta.zenta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zenta.zenta.entity.Informe;

@Repository
public interface InformeRepository extends JpaRepository<Informe, Integer> {
}
