package com.milsabores.learning.repository; // OJO: Ajusta si tu carpeta es 'backend'

import com.milsabores.learning.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(String categoria);
}