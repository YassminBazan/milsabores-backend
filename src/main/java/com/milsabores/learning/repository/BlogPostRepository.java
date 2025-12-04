package com.milsabores.learning.repository;

import com.milsabores.learning.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    // Aquí podríamos agregar filtros después, ej: findByCategoria(String categoria)
}