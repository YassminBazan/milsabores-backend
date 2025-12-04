package com.milsabores.learning.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "blog_posts")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    
    @Column(length = 500) // Resumen un poco más largo
    private String resumen;
    
    private String fecha; // Guardamos como String para facilitar compatibilidad con tu frontend actual "2024-01-15"
    private String autor;
    private String imagen;
    private String categoria; // 'recetas', 'noticias', etc.

    @Column(columnDefinition = "LONGTEXT") // ¡Importante para guardar el HTML del post!
    private String contenido;
}