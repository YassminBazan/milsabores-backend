package com.milsabores.learning.controller;

import com.milsabores.learning.model.Usuario;
import com.milsabores.learning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios") // Coincide con tu frontend: http://localhost:8080/usuarios
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. Listar todos los usuarios (Para la tabla de Admin)
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // 2. Eliminar usuario (Para el botón de borrar)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // 3. Cambiar Rol (Para el botón de ascender/degradar)
    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> updateRol(@PathVariable Long id, @RequestBody Usuario partialUpdate) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                if (partialUpdate.getRol() != null) {
                    usuario.setRol(partialUpdate.getRol());
                }
                // Aquí podrías actualizar otros campos si quisieras
                return ResponseEntity.ok(usuarioRepository.save(usuario));
            })
            .orElse(ResponseEntity.notFound().build());
    }
}