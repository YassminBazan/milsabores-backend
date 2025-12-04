package com.milsabores.learning.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final String UPLOAD_DIR = "img/";

    @PostMapping("/upload")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        
        // 1. Crear la carpeta si no existe
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 2. Generar un nombre único para evitar colisiones (torta.jpg -> torta_12345.jpg)
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);

        // 3. Guardar el archivo en el disco
        Files.copy(file.getInputStream(), filePath);

        // 4. Devolver la URL pública
        // El frontend recibirá: { "url": "images/1709999_torta.jpg" }
        Map<String, String> response = new HashMap<>();
        response.put("url", "img/" + filename);
        
        return response;
    }
}