package com.milsabores.learning.controller;

import com.milsabores.learning.model.Pedido;
import com.milsabores.learning.model.PedidoItem;
import com.milsabores.learning.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos") // URL: http://localhost:8080/pedidos
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    // 1. GET: Listar pedidos (Con filtro opcional por usuario)
    @GetMapping
    public List<Pedido> getAllPedidos(@RequestParam(required = false) String userId) {
        if (userId != null) {
            return pedidoRepository.findByUserId(userId);
        }
        return pedidoRepository.findAll();
    }

    // 2. POST: Crear un pedido nuevo
    @PostMapping
    public Pedido createPedido(@RequestBody Map<String, Object> payload) {
        Pedido pedido = new Pedido();

        // A. Datos Generales
        pedido.setUserId((String) payload.get("userId"));
        // (Number) cast es necesario porque JSON a veces manda Integer o Double
        pedido.setTotal(((Number) payload.get("total")).intValue()); 
        pedido.setEstado("pendiente");

        // B. Datos del Cliente (vienen anidados en "cliente")
        Map<String, String> cliente = (Map<String, String>) payload.get("cliente");
        if (cliente != null) {
            pedido.setClienteNombre(cliente.get("nombre"));
            pedido.setClienteEmail(cliente.get("email"));
            pedido.setClienteTelefono(cliente.get("telefono"));
            pedido.setClienteComuna(cliente.get("comuna"));
            // React manda la dirección ya formateada: "Calle 123 (Casa)"
            pedido
            .setClienteDireccion(cliente.get("direccion")); 
        }

        // C. Items del Pedido (La lista de productos)
        List<Map<String, Object>> itemsRaw = (List<Map<String, Object>>) payload.get("items");
        List<PedidoItem> items = new ArrayList<>();

        if (itemsRaw != null) {
            for (Map<String, Object> itemMap : itemsRaw) {
                PedidoItem item = new PedidoItem();
                item.setNombre((String) itemMap.get("nombre"));
                item.setCantidad(((Number) itemMap.get("cantidad")).intValue());
                item.setPrecio(((Number) itemMap.get("precio")).intValue());
                item.setImagen((String) itemMap.get("imagen"));

                // Campos opcionales de personalización
                if (itemMap.get("cantidadPersonas") != null) 
                    item.setCantidadPersonas((String) itemMap.get("cantidadPersonas"));
                
                if (itemMap.get("colorGlaseado") != null) 
                    item.setColorGlaseado((String) itemMap.get("colorGlaseado"));
                
                if (itemMap.get("mensajeEspecial") != null) 
                    item.setMensajeEspecial((String) itemMap.get("mensajeEspecial"));

                item.setPedido(pedido); // Vinculamos el item al padre
                items.add(item);
            }
        }

        pedido.setItems(items);

        // Guardamos el padre (Hibernate guarda los hijos automáticamente por Cascade)
        return pedidoRepository.save(pedido);
    }
    
    // 3. PATCH: Actualizar estado (Para el Admin)
    @PatchMapping("/{id}")
    public Pedido updateEstado(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null && payload.containsKey("estado")) {
            pedido.setEstado(payload.get("estado"));
            return pedidoRepository.save(pedido);
        }
        return null;
    }
}