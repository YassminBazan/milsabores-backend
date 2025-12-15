package com.milsabores.learning.controller;

import com.milsabores.learning.model.Pedido;
import com.milsabores.learning.service.PedidoService; // Importamos el Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // 1. GET: Listar pedidos
    @GetMapping
    public List<Pedido> getAllPedidos(@RequestParam(required = false) String userId) {
        return pedidoService.listarPedidos(userId);
    }

    // 2. POST: Crear pedido
    @PostMapping
    public ResponseEntity<?> createPedido(@RequestBody Map<String, Object> payload) {
        try {
            Pedido nuevoPedido = pedidoService.procesarPedido(payload);
            return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Si falla el stock, devolvemos un error 400 con el mensaje claro
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno al procesar el pedido"));
        }
    }

    // 3. PATCH: Actualizar estado
    @PatchMapping("/{id}")
    public Pedido updateEstado(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        if (payload.containsKey("estado")) {
            return pedidoService.actualizarEstado(id, payload.get("estado"));
        }
        return null;
    }
}