package com.milsabores.learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milsabores.learning.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Para que el usuario pueda ver "Mis Pedidos"
    List<Pedido> findByUserId(String userId);
}