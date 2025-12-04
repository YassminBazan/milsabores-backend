package com.milsabores.learning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milsabores.learning.model.BlogPost;
import com.milsabores.learning.repository.BlogPostRepository;

@RestController
@RequestMapping("/blog") // URL: http://localhost:8080/blog
@CrossOrigin(origins = "http://localhost:5173")
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogRepository;

    // 1. Obtener todos los posts
    @GetMapping
    public List<BlogPost> getAllPosts() {
        return blogRepository.findAll();
    }

    // 2. Obtener un post por ID (Para el modal si lo necesitaras por separado)
    @GetMapping("/{id}")
    public BlogPost getPostById(@PathVariable Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    // 3. Crear un post (Para poblar la base de datos)
    @PostMapping
    public BlogPost createPost(@RequestBody BlogPost post) {
        return blogRepository.save(post);
    }
}