package com.milsabores.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.milsabores.learning.model.Producto;

/*
Nos permite guardar y sacar informacion en la base de datos, con esto
podemos acceder sin necesidad de escribir código SQL como SELECT 
*/

@Repository
public interface ProductRepository extends JpaRepository<Producto, Long> {
    /*
    Al extender de JpaRepository, ya heredamos métodos como:
     .findAll()  -> Trae todo
     .save()     -> Guarda
     .findById() -> Busca por ID
     .delete()   -> Borra
    
    */

     //aqui va el metodo buscar por nombre y por sku para vallidar que no exista antes de guardalo
     
}
