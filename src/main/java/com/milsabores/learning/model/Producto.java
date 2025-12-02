package com.milsabores.learning.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "El nombre es obligatorio")
    @NotEmpty(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precio;

    private String descripcion;

    // @NotBlank(message = "La categoria es obligatoria") --> Hay una categoria por defecto "Sin categoria"
    private String categoria;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser un número psotivo")
    private Integer stock;

    private String imagen; //Guardaremos la ruta para la imagen "img/torta-chocolate.jpg"

    private String estado;


    
}
