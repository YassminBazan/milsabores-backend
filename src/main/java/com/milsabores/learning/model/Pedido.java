package com.milsabores.learning.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // Email del usuario o "guest"
    private Integer total;
    private String estado; // 'pendiente', 'en-preparacion', etc.
    
    private LocalDateTime fechaCreacion; // Coincide con el cambio que hicimos en React

    // Datos del Cliente (Aplanados para SQL)
    // Estos campos guardarán lo que viene en el objeto 'cliente' de tu JSON
    private String clienteNombre;
    private String clienteEmail;
    private String clienteTelefono;
    private String clienteDireccion; 
    private String clienteComuna;

    // Relación: Un Pedido tiene muchos Items
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonManagedReference // Para que se serialice bien el JSON
    private List<PedidoItem> items;

    @PrePersist
    protected void onCreate() {
        if (this.fechaCreacion == null) this.fechaCreacion = LocalDateTime.now();
        if (this.estado == null) this.estado = "pendiente";
    }
}