package com.milsabores.learning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido_items")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Coincide con tu OrderItem de TS
    private String nombre;
    private Integer cantidad;
    private Integer precio;
    private String imagen;

    // Personalización (Opcionales)
    private String cantidadPersonas;
    private String mensajeEspecial;
    private String colorGlaseado;

    // Relación con el Padre (Pedido)
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonBackReference 
    private Pedido pedido;
}