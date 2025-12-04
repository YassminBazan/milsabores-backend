package com.milsabores.learning.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity; // Import necesario para la lista de colores
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data 
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // En el frontend lo llamamos 'codigo', pero el formulario envía 'sku'.
    // Spring Boot mapeará automáticamente el campo 'sku' del JSON a este campo.
    private String sku; 

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Integer precio;

    @Min(value = 0, message = "El descuento no puede ser negativo")
    private Integer descuento;

    @Column(length = 1000) // Ampliamos el tamaño para descripciones largas
    private String descripcion;

    @NotBlank(message = "La categoria es obligatoria")
    private String categoria;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser un número positivo")
    private Integer stock;

    private String imagen; // Ruta relativa o URL de la imagen

    private String estado; // 'activo' o 'inactivo'

    // --- NUEVOS CAMPOS (Para coincidir con el Frontend) ---

    private String icono; // Ej: "fa-solid fa-cake"
    
    private Integer rating; // Ej: 5 estrellas

    // Campos para Personalización de Tortas
    private Boolean personalizable; // true/false
    
    private Integer basePricePerPersona; // Precio base para el cálculo
    
    private Integer minPersonas; // Mínimo de porciones
    
    private Integer maxPersonas; // Máximo de porciones

    // Para guardar la lista de colores (['blanco', 'rosa']) en MySQL
    // JPA creará una tabla extra automáticamente para esto
    @ElementCollection 
    private List<String> coloresGlaseado;

    // Método para asignar valores por defecto si no vienen
    @PrePersist
    public void prePersist() {
        if (this.stock == null) this.stock = 0;
        if (this.descuento == null) this.descuento = 0;
        if (this.personalizable == null) this.personalizable = false;
        if (this.rating == null) this.rating = 0;
    }
}
