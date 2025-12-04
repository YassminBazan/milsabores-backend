package com.milsabores.learning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.milsabores.learning.model.Producto;
import com.milsabores.learning.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Producto> listarTodos(){
        return productRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id){
        return productRepository.findById(id);
    }

    public Producto guardar(Producto producto){
        //Validaciones extras que se requieran para crear un nuevo producto
        if (producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
            producto.setCategoria("Sin categoria");
        }

        if (producto.getDescuento() == null) {
            producto.setDescuento(0);
            
        }

        if (producto.getPrecio() == null){
            producto.setPrecio(0);
        }

        //Validar que el descuento no sea mayor al precio
        if (producto.getDescuento() > producto.getPrecio()) {
            producto.setDescuento(producto.getPrecio());
        }

        //producto.setPrecioFinal(producto.getPrecio() - producto.getDescuento()); --> Se debe agregar el campo en el frontend y en model producto
        return productRepository.save(producto);
    }

    public void eliminar(Long id){
        productRepository.deleteById(id);
    }
    
    public boolean existe(Long id){
        return productRepository.existsById(id);
    }



}
