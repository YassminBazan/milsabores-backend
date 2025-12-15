package com.milsabores.learning.service;

import com.milsabores.learning.model.Pedido;
import com.milsabores.learning.model.PedidoItem;
import com.milsabores.learning.model.Producto;
import com.milsabores.learning.repository.PedidoRepository;
import com.milsabores.learning.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Pedido> listarPedidos(String userId) {
        if (userId != null) {
            return pedidoRepository.findByUserId(userId);
        }
        return pedidoRepository.findAll();
    }

    @Transactional // ¡CRÍTICO! Si falla el stock, se cancela todo
    public Pedido procesarPedido(Map<String, Object> payload) {
        Pedido pedido = new Pedido();

        // 1. Mapear Datos Generales
        pedido.setUserId((String) payload.get("userId"));
        pedido.setTotal(((Number) payload.get("total")).intValue());
        pedido.setEstado("pendiente");

        // 2. Mapear Datos del Cliente
        Map<String, String> cliente = (Map<String, String>) payload.get("cliente");
        if (cliente != null) {
            pedido.setClienteNombre(cliente.get("nombre"));
            pedido.setClienteEmail(cliente.get("email"));
            pedido.setClienteTelefono(cliente.get("telefono"));
            pedido.setClienteComuna(cliente.get("comuna"));
            pedido.setClienteDireccion(cliente.get("direccion"));
        }

        // 3. Procesar Items y RESTAR STOCK
        List<Map<String, Object>> itemsRaw = (List<Map<String, Object>>) payload.get("items");
        List<PedidoItem> items = new ArrayList<>();

        if (itemsRaw != null) {
            for (Map<String, Object> itemMap : itemsRaw) {
                PedidoItem item = new PedidoItem();

                // A. Obtenemos datos básicos
                Long productoId = ((Number) itemMap.get("id")).longValue();
                int cantidadSolicitada = ((Number) itemMap.get("cantidad")).intValue();

                // B. Lógica de Stock (Validación + Resta)
                Producto productoReal = productoRepository.findById(productoId)
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado ID: " + productoId));

                if (productoReal.getStock() < cantidadSolicitada) {
                    throw new RuntimeException("Stock insuficiente para: " + productoReal.getNombre());
                }

                // Restamos y actualizamos estado
                int nuevoStock = productoReal.getStock() - cantidadSolicitada;
                productoReal.setStock(nuevoStock);
                
                if (nuevoStock == 0) {
                    productoReal.setEstado("agotado");
                }
                
                // Guardamos el cambio de stock inmediatamente
                productoRepository.save(productoReal);

                // C. Configurar el Item del Pedido
                item.setNombre((String) itemMap.get("nombre"));
                item.setCantidad(cantidadSolicitada);
                item.setPrecio(((Number) itemMap.get("precio")).intValue());
                item.setImagen((String) itemMap.get("imagen"));

                // Opcionales
                if (itemMap.get("cantidadPersonas") != null)
                    item.setCantidadPersonas((String) itemMap.get("cantidadPersonas"));
                if (itemMap.get("colorGlaseado") != null)
                    item.setColorGlaseado((String) itemMap.get("colorGlaseado"));
                if (itemMap.get("mensajeEspecial") != null)
                    item.setMensajeEspecial((String) itemMap.get("mensajeEspecial"));

                item.setPedido(pedido);
                items.add(item);
            }
        }

        pedido.setItems(items);

        // 4. Guardar Pedido final
        return pedidoRepository.save(pedido);
    }

    public Pedido actualizarEstado(Long id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null) {
            pedido.setEstado(nuevoEstado);
            return pedidoRepository.save(pedido);
        }
        return null;
    }
}