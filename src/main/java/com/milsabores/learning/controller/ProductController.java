package com.milsabores.learning.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milsabores.learning.model.Producto;
import com.milsabores.learning.service.ProductoService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/productos") // Todas las rutas de esta clase empiezan con /productos
public class ProductController {

    private final ProductoService productService;

    // Utilizamos un constructor para la Inyección de Dependencias
    public ProductController(ProductoService productService) {
        this.productService = productService;
    }

    // 1. Endpoint: GET /productos (Listar todos)
    @GetMapping
    public List<Producto> obtenerTodos() {
        // Equivalente al SELECT * FROM productos
        return productService.listarTodos();
    }

    // 2. Endpoint: GET /productos/{id} (Obtener uno solo)
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productService.buscarPorId(id)
                .map(producto -> ResponseEntity.ok(producto)) // Si existe: 200 OK
                .orElse(ResponseEntity.notFound().build());   // Si no: 404 Not Found
    }

    // 3. Endpoint: POST /productos (Crear nuevo)
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto nuevoProducto) {
        
        // Guardamos el producto en la base de datos (el ID se genera solo)
        Producto productoGuardado = productService.guardar(nuevoProducto);

        // Devolvemos el producto guardado con código 201 CREATED
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productoGuardado);
    }

    // 4. Endpoint: PUT /productos/{id} (Actualizar un producto existente)
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@Valid @PathVariable Long id, @RequestBody Producto productoActualizado) {
        
        // Primero verificamos si existe
        if (!productService.existe(id)) {
            return ResponseEntity.notFound().build();
        }

        // Importante: Forzamos el ID del objeto para asegurar que actualice ese y no cree otro
        productoActualizado.setId(id);

        // Al usar .save() con un ID que ya existe, JPA hace un UPDATE en vez de INSERT
        Producto productoGuardado = productService.guardar(productoActualizado);
        
        return ResponseEntity.ok(productoGuardado);
    }

    // 5. Endpoint: DELETE /productos/{id} (Borrar)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        
        if (!productService.existe(id)) {
            return ResponseEntity.notFound().build();
        }

        productService.eliminar(id);
        
        // Devolvemos 204 No Content (Estándar para borrados exitosos: "Lo hice, no hay nada más que decir")
        return ResponseEntity.noContent().build();
    }
}