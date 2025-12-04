package com.milsabores.learning.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milsabores.learning.model.Pedido;
import com.milsabores.learning.model.PedidoItem;
import com.milsabores.learning.repository.PedidoRepository;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    // GET: Obtener pedidos (con filtro opcional por usuario)
    @GetMapping
    public List<Pedido> getAllPedidos(@RequestParam(required = false) String userId) {
        if (userId != null) {
            return pedidoRepository.findByUserId(userId);
        }
        return pedidoRepository.findAll();
    }

    // POST: Crear pedido (Recibe el JSON exacto de tu React)
    @PostMapping
    public Pedido createPedido(@RequestBody Map<String, Object> payload) {
        
        Pedido pedido = new Pedido();
        pedido.setUserId((String) payload.get("userId"));
        pedido.setTotal((Integer) payload.get("total"));
        pedido.setEstado("pendiente");

        // Mapeo manual del objeto 'cliente' que viene del JSON
        Map<String, String> cliente = (Map<String, String>) payload.get("cliente");
        if (cliente != null) {
            pedido.setClienteNombre(cliente.get("nombre"));
            pedido.setClienteEmail(cliente.get("email"));
            pedido.setClienteTelefono(cliente.get("telefono"));
            pedido.setClienteComuna(cliente.get("comuna"));
            // Guardamos dirección completa
            pedido.setClienteDireccion(cliente.get("direccion")); 
        }

        // Mapeo de la lista de items
        List<Map<String, Object>> itemsRaw = (List<Map<String, Object>>) payload.get("items");
        
        List<PedidoItem> items = itemsRaw.stream().map(i -> {
            PedidoItem item = new PedidoItem();
            item.setNombre((String) i.get("nombre"));
            item.setCantidad((Integer) i.get("cantidad"));
            item.setPrecio((Integer) i.get("precio"));
            item.setImagen((String) i.get("imagen"));
            
            // Campos opcionales (pueden ser nulos)
            item.setCantidadPersonas((String) i.get("cantidadPersonas"));
            item.setColorGlaseado((String) i.get("colorGlaseado"));
            item.setMensajeEspecial((String) i.get("mensajeEspecial"));
            
            item.setPedido(pedido); // Enlazar con el padre
            return item;
        }).collect(Collectors.toList());

        pedido.setItems(items);

        return pedidoRepository.save(pedido);
    }
}