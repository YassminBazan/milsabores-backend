package com.milsabores.learning;

//import com.milsabores.learning.model.Pedido;
import com.milsabores.learning.model.Producto;
//import com.milsabores.learning.repository.PedidoRepository;
import com.milsabores.learning.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DBDataLoad implements CommandLineRunner {

    private final ProductService productService;
    //private final PedidoRepository pedidoRepository;

    // Inyectamos el Service de productos y el Repo de pedidos
    public DBDataLoad(ProductService productoService ){//PedidoRepository pedidoRepository) {
        this.productService = productoService;
        //this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
        // 1. Evitamos duplicar datos si reinicias el servidor y usas una BD persistente
        if (!productService.listarTodos().isEmpty()) {
            System.out.println("ℹ️ La base de datos ya tiene productos. Saltando carga inicial.");
            return;
        }

        System.out.println("🚀 Iniciando carga masiva del catálogo Mil Sabores...");

        // --- CARGA DE PRODUCTOS (Datos extraídos de tu JSON) ---

        // Tortas Cuadradas
        crearProducto("Torta Cuadrada de Frutas", "TC002", 50000, "tortas-cuadradas", 10, "img/torta-cuadrada-frutas-crema-chantilly.jpg", "Una mezcla de frutas frescas y crema chantilly sobre un suave bizcocho de vainilla.");
        
        // Tortas Circulares
        crearProducto("Torta Circular de Vainilla", "TT001", 40000, "tortas-circulares", 8, "img/torta-circular-vainilla.jpg", "Bizcocho de vainilla clásico relleno con crema pastelera y cubierto con un glaseado dulce.");
        crearProducto("Torta Circular de Manjar", "TT002", 42000, "tortas-circulares", 8, "img/torta-circular-manjar.jpg", "Torta tradicional chilena con manjar y nueces, un deleite para los amantes de los sabores dulces.");
        
        // Postres Individuales
        crearProducto("Mousse de Chocolate", "PI001", 5000, "postres-individuales", 20, "img/mousse-chocolate-cremoso-individual.jpg", "Postre individual cremoso y suave, hecho con chocolate de alta calidad.");
        crearProducto("Tiramisú Clásico", "PI002", 5500, "postres-individuales", 15, "img/tiramisu-italiano-cafe-mascarpone.jpg", "Un postre italiano individual con capas de café, mascarpone y cacao.");
        
        // Sin Azúcar
        crearProducto("Torta Sin Azúcar de Naranja", "PSA001", 48000, "sin-azucar", 5, "img/torta-naranja-sin-azucar-saludable.jpg", "Torta ligera y deliciosa, endulzada naturalmente.");
        crearProducto("Cheesecake Sin Azúcar", "PSA002", 47000, "sin-azucar", 5, "img/cheesecake.jpg", "Suave y cremoso, este cheesecake es una opción perfecta para disfrutar sin culpa.");
        
        // Tradicional
        crearProducto("Empanada de Manzana", "PT001", 3000, "tradicional", 30, "img/empanadas-manzana.jpg", "Pastelería tradicional rellena de manzanas especiadas.");
        crearProducto("Tarta de Santiago", "PT002", 6000, "tradicional", 12, "img/tarta-santiago-almendras-espa-ola.jpg", "Tradicional tarta española hecha con almendras, azúcar y huevos.");
        
        // Sin Gluten
        crearProducto("Brownie Sin Gluten", "PG001", 4000, "sin-gluten", 20, "img/brownie-sin-gluten-denso-chocolate.jpg", "Rico y denso, este brownie es perfecto para quienes necesitan evitar el gluten.");
        crearProducto("Pan Sin Gluten", "PG002", 3500, "sin-gluten", 15, "img/pan-sin-gluten-esponjoso.jpg", "Suave y esponjoso, ideal para sándwiches.");
        
        // Vegana
        crearProducto("Torta Vegana de Chocolate", "PV001", 50000, "vegana", 7, "img/torta-vegana-chocolate-sin-productos-animales.jpg", "Torta de chocolate húmeda y deliciosa, hecha sin productos de origen animal.");
        crearProducto("Galletas Veganas de Avena", "PV002", 4500, "vegana", 40, "img/galletas-veganas-avena-crujientes.jpg", "Crujientes y sabrosas, una excelente opción para un snack saludable.");
        
        // Especiales
        crearProducto("Torta Especial de Cumpleaños", "TE001", 55000, "especiales", 3, "img/torta-cumplea-os-especial-decorada-personalizada.jpg", "Diseñada especialmente para celebraciones, personalizable.");
        crearProducto("Torta Especial de Boda", "TE002", 60000, "especiales", 2, "img/torta-boda-especial.jpg", "Elegante y deliciosa, diseñada para ser el centro de atención en cualquier boda.");

        
        /*// --- CARGA DE PEDIDOS DE EJEMPLO (Para que el Dashboard muestre números) ---
        // Usamos datos de tu JSON de pedidos
        
        // Pedido 1: Wacoldo Soto (Entregado)
        Pedido ord1 = new Pedido();
        ord1.setCliente("Wacoldo Soto");
        ord1.setFecha(LocalDateTime.now().minusDays(2)); // Hace 2 días
        ord1.setTotal(45500);
        ord1.setEstado("entregado");
        pedidoRepository.save(ord1);

        // Pedido 2: Cliente Feliz (En preparación)
        Pedido ord2 = new Pedido();
        ord2.setCliente("Cliente Feliz");
        ord2.setFecha(LocalDateTime.now().minusHours(4)); // Hoy
        ord2.setTotal(1000);
        ord2.setEstado("en-preparacion");
        pedidoRepository.save(ord2);

        System.out.println("✅ ¡Catálogo completo y pedidos cargados exitosamente!");

        */
    }

    // --- MÉTODO AYUDANTE (Helper) ---
    // Recibe los datos, crea el objeto y lo guarda usando el Service
    private void crearProducto(String nombre, String sku, Integer precio, String categoria, Integer stock, String rutaImagen, String descripcion) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setSku(sku);
        p.setPrecio(precio);
        p.setDescuento(0); // Por defecto 0
        p.setStock(stock);
        p.setCategoria(categoria);
        p.setDescripcion(descripcion);
        p.setImagen(rutaImagen); // Guardamos la ruta tal cual viene (img/...)
        p.setEstado("activo");

        productService.guardar(p);
    }
}