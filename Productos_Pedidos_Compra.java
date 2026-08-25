import java.util.ArrayList;
import java.util.Scanner;

public class StockFlow {

    // ==============================
    // CLASE PRODUCTO
    // ==============================
    static class Producto {

        int id;
        String nombre;
        double precio;
        int stock;

        public Producto(int id, String nombre, double precio, int stock) {
            this.id = id;
            this.nombre = nombre;
            this.precio = precio;
            this.stock = stock;
        }

        @Override
        public String toString() {
            return "ID: " + id +
                    " | Nombre: " + nombre +
                    " | Precio: $" + precio +
                    " | Stock: " + stock;
        }
    }

    // ==============================
    // CLASE COMPRA
    // ==============================
    static class Compra {

        int id;
        Producto producto;
        int cantidad;
        double total;

        public Compra(int id, Producto producto, int cantidad) {
            this.id = id;
            this.producto = producto;
            this.cantidad = cantidad;
            this.total = producto.precio * cantidad;

            // La compra aumenta el inventario
            producto.stock += cantidad;
        }

        public void mostrarCompra() {

            System.out.println("\n===== COMPRA =====");
            System.out.println("ID Compra: " + id);
            System.out.println("Producto: " + producto.nombre);
            System.out.println("Cantidad: " + cantidad);
            System.out.println("Precio unitario: $" + producto.precio);
            System.out.println("Total: $" + total);
        }
    }

    // ==============================
    // CLASE PEDIDO
    // ==============================
    static class DetallePedido {

        Producto producto;
        int cantidad;
        double subtotal;

        public DetallePedido(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.subtotal = producto.precio * cantidad;
        }
    }

    static class Pedido {

        int id;
        String cliente;
        ArrayList<DetallePedido> detalles;
        double total;

        public Pedido(int id, String cliente) {
            this.id = id;
            this.cliente = cliente;
            this.detalles = new ArrayList<>();
            this.total = 0;
        }

        public boolean agregarProducto(Producto producto, int cantidad) {

            // Verificar stock
            if (producto.stock < cantidad) {
                System.out.println(
                        "No hay suficiente stock de " + producto.nombre
                );
                return false;
            }

            // Descontar stock
            producto.stock -= cantidad;

            // Crear detalle
            DetallePedido detalle =
                    new DetallePedido(producto, cantidad);

            detalles.add(detalle);

            // Actualizar total
            total += detalle.subtotal;

            return true;
        }

        public void mostrarPedido() {

            System.out.println("\n================================");
            System.out.println("           PEDIDO");
            System.out.println("================================");

            System.out.println("ID Pedido: " + id);
            System.out.println("Cliente: " + cliente);

            System.out.println("\nProductos:");

            for (DetallePedido detalle : detalles) {

                System.out.println(
                        detalle.producto.nombre +
                        " | Cantidad: " + detalle.cantidad +
                        " | Subtotal: $" + detalle.subtotal
                );
            }

            System.out.println("--------------------------------");
            System.out.println("TOTAL: $" + total);
            System.out.println("================================");
        }
    }

    // ==============================
    // LISTAS DEL SISTEMA
    // ==============================

    static ArrayList<Producto> productos =
            new ArrayList<>();

    static ArrayList<Compra> compras =
            new ArrayList<>();

    static ArrayList<Pedido> pedidos =
            new ArrayList<>();

    static Scanner scanner =
            new Scanner(System.in);

    static int siguienteProducto = 1;
    static int siguienteCompra = 1;
    static int siguientePedido = 1;


    // ==============================
    // AGREGAR PRODUCTO
    // ==============================

    public static void agregarProducto() {

        scanner.nextLine();

        System.out.println("\n===== AGREGAR PRODUCTO =====");

        System.out.print("Nombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Stock inicial: ");
        int stock = scanner.nextInt();

        Producto producto =
                new Producto(
                        siguienteProducto,
                        nombre,
                        precio,
                        stock
                );

        productos.add(producto);

        System.out.println(
                "\nProducto agregado correctamente."
        );

        System.out.println(producto);

        siguienteProducto++;
    }


    // ==============================
    // MOSTRAR PRODUCTOS
    // ==============================

    public static void mostrarProductos() {

        System.out.println("\n===== PRODUCTOS =====");

        if (productos.isEmpty()) {

            System.out.println(
                    "No existen productos registrados."
            );

            return;
        }

        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }


    // ==============================
    // BUSCAR PRODUCTO
    // ==============================

    public static Producto buscarProducto(int id) {

        for (Producto producto : productos) {

            if (producto.id == id) {
                return producto;
            }
        }

        return null;
    }


    // ==============================
    // REGISTRAR COMPRA
    // ==============================

    public static void registrarCompra() {

        System.out.println("\n===== REGISTRAR COMPRA =====");

        mostrarProductos();

        if (productos.isEmpty()) {
            return;
        }

        System.out.print(
                "\nIngrese el ID del producto: "
        );

        int id = scanner.nextInt();

        Producto producto =
                buscarProducto(id);

        if (producto == null) {

            System.out.println(
                    "Producto no encontrado."
            );

            return;
        }

        System.out.print(
                "Cantidad comprada: "
        );

        int cantidad = scanner.nextInt();

        if (cantidad <= 0) {

            System.out.println(
                    "La cantidad debe ser mayor que cero."
            );

            return;
        }

        Compra compra =
                new Compra(
                        siguienteCompra,
                        producto,
                        cantidad
                );

        compras.add(compra);

        System.out.println(
                "\nCompra registrada correctamente."
        );

        compra.mostrarCompra();

        siguienteCompra++;
    }


    // ==============================
    // CREAR PEDIDO
    // ==============================

    public static void crearPedido() {

        System.out.println("\n===== CREAR PEDIDO =====");

        scanner.nextLine();

        System.out.print("Nombre del cliente: ");

        String cliente =
                scanner.nextLine();

        Pedido pedido =
                new Pedido(
                        siguientePedido,
                        cliente
                );

        boolean continuar = true;

        while (continuar) {

            mostrarProductos();

            if (productos.isEmpty()) {
                return;
            }

            System.out.print(
                    "\nID del producto: "
            );

            int id = scanner.nextInt();

            Producto producto =
                    buscarProducto(id);

            if (producto == null) {

                System.out.println(
                        "Producto no encontrado."
                );

                continue;
            }

            System.out.print(
                    "Cantidad: "
            );

            int cantidad =
                    scanner.nextInt();

            if (cantidad <= 0) {

                System.out.println(
                        "Cantidad inválida."
                );

                continue;
            }

            if (pedido.agregarProducto(
                    producto,
                    cantidad)) {

                System.out.println(
                        "Producto agregado al pedido."
                );
            }

            System.out.print(
                    "\n¿Desea agregar otro producto? (1 = Sí / 2 = No): "
            );

            int opcion = scanner.nextInt();

            if (opcion == 2) {
                continuar = false;
            }
        }

        if (pedido.detalles.isEmpty()) {

            System.out.println(
                    "El pedido no tiene productos."
            );

            return;
        }

        pedidos.add(pedido);

        System.out.println(
                "\nPedido creado correctamente."
        );

        pedido.mostrarPedido();

        siguientePedido++;
    }


    // ==============================
    // MOSTRAR COMPRAS
    // ==============================

    public static void mostrarCompras() {

        System.out.println("\n===== HISTORIAL DE COMPRAS =====");

        if (compras.isEmpty()) {

            System.out.println(
                    "No hay compras registradas."
            );

            return;
        }

        for (Compra compra : compras) {
            compra.mostrarCompra();
        }
    }


    // ==============================
    // MOSTRAR PEDIDOS
    // ==============================

    public static void mostrarPedidos() {

        System.out.println("\n===== HISTORIAL DE PEDIDOS =====");

        if (pedidos.isEmpty()) {

            System.out.println(
                    "No hay pedidos registrados."
            );

            return;
        }

        for (Pedido pedido : pedidos) {
            pedido.mostrarPedido();
        }
    }


    // ==============================
    // MENÚ PRINCIPAL
    // ==============================

    public static void menu() {

        int opcion;

        do {

            System.out.println("\n");
            System.out.println("======================================");
            System.out.println("          SISTEMA STOCKFLOW");
            System.out.println("======================================");

            System.out.println("1. Agregar producto");
            System.out.println("2. Mostrar productos");
            System.out.println("3. Registrar compra");
            System.out.println("4. Crear pedido");
            System.out.println("5. Mostrar compras");
            System.out.println("6. Mostrar pedidos");
            System.out.println("7. Salir");

            System.out.println("======================================");

            System.out.print(
                    "Seleccione una opción: "
            );

            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    agregarProducto();
                    break;

                case 2:
                    mostrarProductos();
                    break;

                case 3:
                    registrarCompra();
                    break;

                case 4:
                    crearPedido();
                    break;

                case 5:
                    mostrarCompras();
                    break;

                case 6:
                    mostrarPedidos();
                    break;

                case 7:
                    System.out.println(
                            "\nGracias por utilizar STOCKFLOW."
                    );
                    break;

                default:
                    System.out.println(
                            "\nOpción inválida."
                    );
            }

        } while (opcion != 7);
    }


    // ==============================
    // MAIN
    // ==============================

    public static void main(String[] args) {

        // Productos iniciales de ejemplo

        productos.add(
                new Producto(
                        siguienteProducto++,
                        "Teclado",
                        80000,
                        10
                )
        );

        productos.add(
                new Producto(
                        siguienteProducto++,
                        "Mouse",
                        50000,
                        15
                )
        );

        productos.add(
                new Producto(
                        siguienteProducto++,
                        "Monitor",
                        600000,
                        5
                )
        );

        // Iniciar sistema
        menu();

        scanner.close();
    }
}