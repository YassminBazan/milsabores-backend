package com.milsabores.learning.model;

import jakarta.persistence.*;
import lombok.Data;

/*El model se encarga de definir la estructura de los datos 
(que debe coincidir con los que espera el front), algo asi como se verian las tablas en la base de datos.
*/

@Entity
@Table(name = "productos")
@Data 
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer precio;

    private String descripcion;

    private String categoria;

    private Integer stock;

    private String imagen; //Guardaremos la ruta para la imagen "img/torta-chocolate.jpg"

    private String estado;


    
}
