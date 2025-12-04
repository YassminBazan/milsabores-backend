package com.milsabores.learning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; // Importante
import org.springframework.stereotype.Service;

import com.milsabores.learning.model.Producto;
import com.milsabores.learning.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired // Inyección de dependencias automática
    private ProductoRepository productRepository;

    public List<Producto> listarTodos() {
        return productRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        // --- Validaciones de Negocio ---
        
        // 1. Categoría por defecto
        if (producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
            producto.setCategoria("Sin categoria");
        }

        // 2. Descuento por defecto
        if (producto.getDescuento() == null) {
            producto.setDescuento(0);
        }

        // 3. Precio por defecto (evitar nulos)
        if (producto.getPrecio() == null) {
            producto.setPrecio(0);
        }

        // 4. Validar lógica descuento vs precio
        if (producto.getDescuento() > producto.getPrecio()) {
            // Si el descuento es mayor al precio, lo ajustamos al máximo posible (precio total)
            producto.setDescuento(producto.getPrecio());
        }
        
        // 5. Stock por defecto
        if (producto.getStock() == null) {
            producto.setStock(0);
        }

        // Guardamos en la base de datos
        return productRepository.save(producto);
    }

    public void eliminar(Long id) {
        productRepository.deleteById(id);
    }
    
    public boolean existe(Long id) {
        return productRepository.existsById(id);
    }
}