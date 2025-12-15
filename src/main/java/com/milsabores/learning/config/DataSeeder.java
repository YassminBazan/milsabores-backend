package com.milsabores.learning.config;

import com.milsabores.learning.model.BlogPost;
import com.milsabores.learning.model.Producto;
import com.milsabores.learning.model.Usuario;
import com.milsabores.learning.repository.BlogPostRepository;
import com.milsabores.learning.repository.ProductoRepository;
import com.milsabores.learning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BlogPostRepository blogPostRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        cargarUsuarios();
        cargarProductos();
        cargarBlogs();
    }

    private void cargarUsuarios() {
        if (usuarioRepository.count() == 0) {
            System.out.println("--- Cargando Usuarios Iniciales... ---");

            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setEmail("admin@milsabores.cl");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol("admin");
            usuarioRepository.save(admin);

            Usuario cliente = new Usuario();
            cliente.setNombre("Cliente Feliz");
            cliente.setEmail("cliente@gmail.com");
            cliente.setPassword(passwordEncoder.encode("cliente123"));
            cliente.setRol("cliente");
            usuarioRepository.save(cliente);
        }
    }

    private void cargarProductos() {
        if (productoRepository.count() == 0) {
            System.out.println("--- Cargando Productos del Catálogo... ---");

            // 1. Torta Cuadrada de Chocolate
            crearProducto("TC001", "tortas-cuadradas", "Torta Cuadrada de Chocolate", 45000,
                    "Deliciosa torta de chocolate con capas de ganache y un toque de avellanas.",
                    "img/torta-cuadrada-chocolate-ganache.jpg", "fa-solid fa-birthday-cake", true, 5);

            // 2. Torta Cuadrada de Frutas
            crearProducto("TC002", "tortas-cuadradas", "Torta Cuadrada de Frutas", 50000,
                    "Una mezcla de frutas frescas y crema chantilly sobre un suave bizcocho de vainilla.",
                    "img/torta-cuadrada-frutas-crema-chantilly.jpg", "fa-solid fa-birthday-cake", true, 4);

            // 3. Torta Circular de Vainilla
            crearProducto("TT001", "tortas-circulares", "Torta Circular de Vainilla", 40000,
                    "Bizcocho de vainilla clásico relleno con crema pastelera y cubierto con glaseado dulce.",
                    "img/torta-circular-vainilla.jpg", "fa-solid fa-birthday-cake", true, 6);

            // 4. Torta Circular de Manjar
            crearProducto("TT002", "tortas-circulares", "Torta Circular de Manjar", 42000,
                    "Torta tradicional chilena con manjar y nueces, un deleite para los amantes de los sabores dulces.",
                    "img/torta-circular-manjar.jpg", "fa-solid fa-birthday-cake", true, 3);

            // 5. Mousse de Chocolate
            crearProducto("PI001", "postres-individuales", "Mousse de Chocolate", 5000,
                    "Postre individual cremoso y suave, hecho con chocolate de alta calidad.",
                    "img/mousse-chocolate-cremoso-individual.jpg", "fa-solid fa-ice-cream", false, 30);

            // 6. Tiramisú Clásico
            crearProducto("PI002", "postres-individuales", "Tiramisú Clásico", 5500,
                    "Un postre italiano individual con capas de café, mascarpone y cacao.",
                    "img/tiramisu-italiano-cafe-mascarpone.jpg", "fa-solid fa-ice-cream", false, 25);

            // 7. Torta Sin Azúcar de Naranja
            crearProducto("PSA001", "sin-azucar", "Torta Sin Azúcar de Naranja", 48000,
                    "Torta ligera y deliciosa, endulzada naturalmente, ideal para quienes buscan opciones más saludables.",
                    "img/torta-naranja-sin-azucar-saludable.jpg", "fa-solid fa-leaf", true, 5);

            // 8. Cheesecake Sin Azúcar
            crearProducto("PSA002", "sin-azucar", "Cheesecake Sin Azúcar", 47000,
                    "Suave y cremoso, este cheesecake es una opción perfecta para disfrutar sin culpa.",
                    "img/cheesecake.jpg", "fa-solid fa-leaf", true, 5);

            // 9. Empanada de Manzana
            crearProducto("PT001", "tradicional", "Empanada de Manzana", 3000,
                    "Pastelería tradicional rellena de manzanas especiadas.",
                    "img/empanadas-manzana.jpg", "fa-solid fa-bread-slice", false, 50);

            // 10. Tarta de Santiago
            crearProducto("PT002", "tradicional", "Tarta de Santiago", 6000,
                    "Tradicional tarta española hecha con almendras, azúcar, y huevos.",
                    "img/tarta-santiago-almendras-espa-ola.jpg", "fa-solid fa-bread-slice", false, 10);

            // 11. Brownie Sin Gluten
            crearProducto("PG001", "sin-gluten", "Brownie Sin Gluten", 4000,
                    "Rico y denso, este brownie es perfecto para quienes necesitan evitar el gluten.",
                    "img/brownie-sin-gluten-denso-chocolate.jpg", "fa-solid fa-wheat-awn-circle-exclamation", false, 20);

            // 12. Pan Sin Gluten
            crearProducto("PG002", "sin-gluten", "Pan Sin Gluten", 3500,
                    "Suave y esponjoso, ideal para sándwiches o para acompañar cualquier comida.",
                    "img/pan-sin-gluten-esponjoso.jpg", "fa-solid fa-wheat-awn-circle-exclamation", false, 20);

            // 13. Torta Vegana de Chocolate
            crearProducto("PV001", "vegana", "Torta Vegana de Chocolate", 50000,
                    "Torta de chocolate húmeda y deliciosa, hecha sin productos de origen animal.",
                    "img/torta-vegana-chocolate-sin-productos-animales.jpg", "fa-solid fa-seedling", true, 3);

            // 14. Galletas Veganas de Avena
            crearProducto("PV002", "vegana", "Galletas Veganas de Avena", 4500,
                    "Crujientes y sabrosas, estas galletas son una excelente opción para un snack saludable.",
                    "img/galletas-veganas-avena-crujientes.jpg", "fa-solid fa-seedling", false, 40);

            // 15. Torta Especial de Cumpleaños
            crearProducto("TE001", "especiales", "Torta Especial de Cumpleaños", 55000,
                    "Diseñada especialmente para celebraciones, personalizable con decoraciones y mensajes únicos.",
                    "img/torta-cumplea-os-especial-decorada-personalizada.jpg", "fa-solid fa-gift", true, 2);

            // 16. Torta Especial de Boda
            crearProducto("TE002", "especiales", "Torta Especial de Boda", 60000,
                    "Elegante y deliciosa, esta torta está diseñada para ser el centro de atención en cualquier boda.",
                    "img/torta-boda-especial.jpg", "fa-solid fa-gift", true, 2);
            System.out.println("--- Productos Cargados ---");
        }
    }

    private void cargarBlogs() {
        if (blogPostRepository.count() == 0) {
            System.out.println("--- Cargando Artículos del Blog... ---");

            crearBlog("Secretos para el Bizcocho Perfecto", "recetas", "2025-01-15", "Chef María González",
                    "img/blog-bizcocho-perfecto.jpg",
                    "Descubre los trucos profesionales para lograr un bizcocho esponjoso y delicioso en casa.",
                    "<h4>Los Fundamentos del Bizcocho Perfecto</h4><p>Un bizcocho perfecto es la base de cualquier torta exitosa...</p>");

            crearBlog("Nueva Sucursal en Concepción Centro", "noticias", "2025-01-10", "Equipo Mil Sabores",
                    "img/blog-nueva-sucursal.jpg",
                    "Estamos emocionados de anunciar la apertura de nuestra nueva sucursal en el corazón de Concepción.",
                    "<h4>¡Gran Apertura!</h4><p>Después de meses de preparación, finalmente abrimos nuestras puertas...</p>");

            crearBlog("Cómo Conservar Tortas Frescas por Más Tiempo", "consejos", "2025-01-08", "Chef Carlos Mendoza",
                    "img/blog-conservar-tortas.jpg",
                    "Tips profesionales para mantener tus tortas frescas y deliciosas durante más días.",
                    "<h4>La Importancia del Almacenamiento</h4><p>Una torta bien conservada puede mantener su frescura...</p>");

            crearBlog("Taller de Repostería para Principiantes", "eventos", "2025-01-05", "Equipo Mil Sabores",
                    "img/blog-taller-reposteria.jpg",
                    "Únete a nuestro taller mensual donde aprenderás las técnicas básicas de la repostería.",
                    "<h4>¡Aprende con los Expertos!</h4><p>Cada último sábado del mes realizamos talleres...</p>");

            crearBlog("Ganache de Chocolate: La Receta Definitiva", "recetas", "2025-01-03", "Chef Ana Rodríguez",
                    "img/blog-ganache-chocolate.jpg",
                    "Aprende a preparar el ganache de chocolate perfecto para tus tortas y postres.",
                    "<h4>El Arte del Ganache</h4><p>El ganache es una de las preparaciones más versátiles...</p>");

            crearBlog("Nuevos Sabores de Temporada", "noticias", "2025-01-01", "Equipo Mil Sabores",
                    "img/blog-sabores-temporada.jpg",
                    "Descubre nuestros nuevos sabores inspirados en los frutos de la temporada de verano.",
                    "<h4>Sabores que Celebran el Verano</h4><p>Con la llegada del verano, hemos creado una línea especial...</p>");

            crearBlog("Alianza Culinaria: Mil Sabores y los Futuros Talentos del DuocUC", "eventos", "2025-02-28",
                    "Equipo Mil Sabores",
                    "img/blog-duocuc.jpg",
                    "Descubre cómo nuestra pastelería se une a los estudiantes de gastronomía del DuocUC.",
                    "<h4>Cultivando el Sabor del Mañana</h4><p>En Pastelería Mil Sabores, siempre hemos creído en el poder de la tradición...</p>");

            System.out.println("--- Blogs Cargados ---");
        }
    }

    // --- Helpers ---

    private void crearProducto(String codigo, String categoria, String nombre, Integer precio,
            String descripcion, String imagen, String icono, Boolean personalizable, Integer stock) {
        Producto p = new Producto();
        p.setCodigo(codigo);
        p.setCategoria(categoria);
        p.setNombre(nombre);
        p.setPrecio(precio); 
        p.setDescripcion(descripcion);
        p.setImagen(imagen);
        p.setIcono(icono);
        p.setStock(stock);
        p.setRating(5);
        p.setPersonalizable(personalizable);
        p.setEstado(stock > 0 ? "disponible" : "agotado"); // Si stock es 0, nace agotado

        if (personalizable) {
            p.setBasePricePerPersona(2500); 
            p.setMinPersonas(10);
            p.setMaxPersonas(50);
            p.setColoresGlaseado(List.of("blanco", "rosa", "azul", "chocolate"));
        }
        productoRepository.save(p);
    }

    private void crearBlog(String titulo, String categoria, String fecha, String autor,
            String imagen, String resumen, String contenido) {
        BlogPost post = new BlogPost();
        post.setTitulo(titulo);
        post.setCategoria(categoria);
        post.setFecha(fecha);
        post.setAutor(autor);
        post.setImagen(imagen);
        post.setResumen(resumen);
        post.setContenido(contenido);
        blogPostRepository.save(post);
    }
}