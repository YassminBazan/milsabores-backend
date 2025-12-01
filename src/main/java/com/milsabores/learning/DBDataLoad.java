package com.milsabores.learning;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.milsabores.learning.model.Producto;
import com.milsabores.learning.repository.ProductRepository;

@Component // Le dice a spring gestiona esta clase tu mismo
public class DBDataLoad implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DBDataLoad(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public void run(String... args) throws Exception {
        // Producto 1: Torta de Chocolate
        Producto p1 = new Producto();
        p1.setNombre("Torta de Chocolate");
        p1.setPrecio(45000);
        p1.setDescripcion("Deliciosa torta de chocolate belga con cobertura de ganache.");
        p1.setCategoria("tortas-cuadradas");
        p1.setStock(5);
        p1.setImagen("img/torta-chocolate.jpg");
        p1.setEstado("activo");

        // Producto 2: Cheesecake (Para tener variedad)
        Producto p2 = new Producto();
        p2.setNombre("Cheesecake de Frutos Rojos");
        p2.setPrecio(32000);
        p2.setDescripcion("Base crocante con suave crema de queso y salsa casera.");
        p2.setCategoria("cheesecakes");
        p2.setStock(8);
        p2.setImagen("img/cheesecake-frutos.jpg");
        p2.setEstado("activo");

        // Guardamos en la base de datos usando el Repositorio
        productRepository.save(p1);
        
        productRepository.save(p2);

        System.out.println("✅ Datos de prueba cargados exitosamente!");
    }

}
