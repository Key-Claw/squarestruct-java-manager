package com.squarestruct.manager.ui.menu;

import com.squarestruct.application.dto.ProductoDTO;
import com.squarestruct.application.dto.ProveedorDTO;
import com.squarestruct.application.service.FacturaService;
import com.squarestruct.application.service.PedidoService;
import com.squarestruct.application.service.PlantillaService;
import com.squarestruct.application.service.PresupuestoService;
import com.squarestruct.application.service.ProductoService;
import com.squarestruct.application.service.ProveedorService;
import com.squarestruct.domain.enums.EstadoPedido;
import com.squarestruct.domain.enums.RolUsuario;
import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.BloquePlantilla;
import com.squarestruct.domain.model.Factura;
import com.squarestruct.domain.model.Pedido;
import com.squarestruct.domain.model.PedidoDetalle;
import com.squarestruct.domain.model.PlantillaConstructiva;
import com.squarestruct.domain.model.Presupuesto;
import com.squarestruct.domain.model.PresupuestoDetalle;
import com.squarestruct.domain.model.Producto;
import com.squarestruct.domain.model.Proveedor;
import com.squarestruct.domain.model.Usuario;
import com.squarestruct.domain.repository.CrudRepository;
import com.squarestruct.domain.repository.FacturaRepository;
import com.squarestruct.domain.repository.PedidoRepository;
import com.squarestruct.domain.repository.PlantillaRepository;
import com.squarestruct.domain.repository.PresupuestoRepository;
import com.squarestruct.domain.repository.ProductoRepository;
import com.squarestruct.domain.repository.ProveedorRepository;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Menu de consola de SquareStruct Java Manager.
 *
 * Recibe servicios ya construidos por Main y coordina el flujo interactivo:
 * menu principal, submenu de modulo y operaciones CRUD sobre los repositorios configurados.
 */
public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final ProductoService productoService;
    private final ProveedorService proveedorService;
    private final PedidoService pedidoService;
    private final FacturaService facturaService;
    private final PresupuestoService presupuestoService;
    private final PlantillaService plantillaService;
    private final String persistenceType;

    public MainMenu() {
        this(
                new ProductoService(),
                new ProveedorService(),
                new PedidoService(),
                new FacturaService(),
                new PresupuestoService(),
                new PlantillaService(),
                "sin configurar"
        );
    }

    public MainMenu(ProductoService productoService,
                    ProveedorService proveedorService,
                    PedidoService pedidoService,
                    FacturaService facturaService,
                    PresupuestoService presupuestoService,
                    PlantillaService plantillaService,
                    String persistenceType) {
        this.productoService = productoService;
        this.proveedorService = proveedorService;
        this.pedidoService = pedidoService;
        this.facturaService = facturaService;
        this.presupuestoService = presupuestoService;
        this.plantillaService = plantillaService;
        this.persistenceType = persistenceType;
    }

    /**
     * Ejecuta el bucle principal hasta que el usuario elige salir.
     */
    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion();

            switch (opcion) {
                case 1 -> mostrarSubmenu(Modulo.PRODUCTOS);
                case 2 -> mostrarSubmenu(Modulo.PROVEEDORES);
                case 3 -> mostrarSubmenu(Modulo.PEDIDOS);
                case 4 -> mostrarSubmenu(Modulo.FACTURAS);
                case 5 -> mostrarSubmenu(Modulo.PRESUPUESTOS);
                case 6 -> mostrarSubmenu(Modulo.PLANTILLAS);
                case 0 -> System.out.println("Cerrando SquareStruct Java Manager...");
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("====================================");
        System.out.println("  SQUARESTRUCT JAVA MANAGER");
        System.out.println("====================================");
        System.out.println("Persistencia: " + persistenceType);
        System.out.println("1. Gestión de productos");
        System.out.println("2. Gestión de proveedores");
        System.out.println("3. Gestión de pedidos");
        System.out.println("4. Gestión de facturas");
        System.out.println("5. Gestión de presupuestos");
        System.out.println("6. Gestión de plantillas constructivas");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private void mostrarSubmenu(Modulo modulo) {
        int opcion;

        do {
            System.out.println();
            System.out.println("----- Gestión de " + modulo.etiqueta + " -----");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menú principal");
            System.out.print("Selecciona una opción: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1 -> ejecutarOperacion(() -> listarModulo(modulo));
                case 2 -> ejecutarOperacion(() -> crearModulo(modulo));
                case 3 -> ejecutarOperacion(() -> actualizarModulo(modulo));
                case 4 -> ejecutarOperacion(() -> eliminarModulo(modulo));
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

        } while (opcion != 0);
    }

    private void listarModulo(Modulo modulo) {
        switch (modulo) {
            case PRODUCTOS -> listarProductos();
            case PROVEEDORES -> listarProveedores();
            case PEDIDOS -> listarPedidos();
            case FACTURAS -> listarFacturas();
            case PRESUPUESTOS -> listarPresupuestos();
            case PLANTILLAS -> listarPlantillas();
        }
    }

    private void crearModulo(Modulo modulo) {
        switch (modulo) {
            case PRODUCTOS -> crearProducto();
            case PROVEEDORES -> crearProveedor();
            case PEDIDOS -> crearPedido();
            case FACTURAS -> crearFactura();
            case PRESUPUESTOS -> crearPresupuesto();
            case PLANTILLAS -> crearPlantilla();
        }
    }

    private void actualizarModulo(Modulo modulo) {
        switch (modulo) {
            case PRODUCTOS -> actualizarProducto();
            case PROVEEDORES -> actualizarProveedor();
            case PEDIDOS -> actualizarPedido();
            case FACTURAS -> actualizarFactura();
            case PRESUPUESTOS -> actualizarPresupuesto();
            case PLANTILLAS -> actualizarPlantilla();
        }
    }

    private void eliminarModulo(Modulo modulo) {
        switch (modulo) {
            case PRODUCTOS -> eliminarProducto();
            case PROVEEDORES -> eliminarProveedor();
            case PEDIDOS -> eliminarPedido();
            case FACTURAS -> eliminarFactura();
            case PRESUPUESTOS -> eliminarPresupuesto();
            case PLANTILLAS -> eliminarPlantilla();
        }
    }

    private void listarProductos() {
        ProductoRepository repository = productoRepository();
        if (!repositorioDisponible(repository, "productos")) {
            return;
        }

        List<Producto> productos = repository.findAll();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        productos.forEach(this::mostrarProducto);
    }

    private void crearProducto() {
        ProductoRepository repository = productoRepository();
        if (!repositorioDisponible(repository, "productos")) {
            return;
        }

        Producto producto = leerProducto(null);
        productoService.validarProducto(new ProductoDTO(null, producto.getNombre(), producto.getPrecio()));
        Producto creado = repository.create(producto);

        System.out.println("Producto creado con ID: " + creado.getId());
    }

    private void actualizarProducto() {
        ProductoRepository repository = productoRepository();
        if (!repositorioDisponible(repository, "productos")) {
            return;
        }

        Long id = leerLongPositivo("ID del producto a actualizar: ");
        Optional<Producto> productoActual = repository.findById(id);
        if (productoActual.isEmpty()) {
            System.out.println("No existe un producto con ID " + id + ".");
            return;
        }

        mostrarProducto(productoActual.get());
        Producto producto = leerProducto(productoActual.get());
        productoService.validarProducto(new ProductoDTO(id, producto.getNombre(), producto.getPrecio()));
        repository.update(producto);

        System.out.println("Producto actualizado correctamente.");
    }

    private void eliminarProducto() {
        ProductoRepository repository = productoRepository();
        if (repositorioDisponible(repository, "productos")) {
            eliminarPorId(repository, "producto");
        }
    }

    private void listarProveedores() {
        ProveedorRepository repository = proveedorRepository();
        if (!repositorioDisponible(repository, "proveedores")) {
            return;
        }

        List<Proveedor> proveedores = repository.findAll();
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }

        proveedores.forEach(this::mostrarProveedor);
    }

    private void crearProveedor() {
        ProveedorRepository repository = proveedorRepository();
        if (!repositorioDisponible(repository, "proveedores")) {
            return;
        }

        Proveedor proveedor = leerProveedor(null);
        proveedorService.validarProveedor(new ProveedorDTO(null, proveedor.getNombreEmpresa(), proveedor.getTelefono()));
        Proveedor creado = repository.create(proveedor);

        System.out.println("Proveedor creado con ID: " + creado.getId());
    }

    private void actualizarProveedor() {
        ProveedorRepository repository = proveedorRepository();
        if (!repositorioDisponible(repository, "proveedores")) {
            return;
        }

        Long id = leerLongPositivo("ID del proveedor a actualizar: ");
        Optional<Proveedor> proveedorActual = repository.findById(id);
        if (proveedorActual.isEmpty()) {
            System.out.println("No existe un proveedor con ID " + id + ".");
            return;
        }

        mostrarProveedor(proveedorActual.get());
        Proveedor proveedor = leerProveedor(proveedorActual.get());
        proveedorService.validarProveedor(new ProveedorDTO(id, proveedor.getNombreEmpresa(), proveedor.getTelefono()));
        repository.update(proveedor);

        System.out.println("Proveedor actualizado correctamente.");
    }

    private void eliminarProveedor() {
        ProveedorRepository repository = proveedorRepository();
        if (repositorioDisponible(repository, "proveedores")) {
            eliminarPorId(repository, "proveedor");
        }
    }

    private void listarPedidos() {
        PedidoRepository repository = pedidoRepository();
        if (!repositorioDisponible(repository, "pedidos")) {
            return;
        }

        List<Pedido> pedidos = repository.findAll();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        pedidos.forEach(this::mostrarPedido);
    }

    private void crearPedido() {
        PedidoRepository repository = pedidoRepository();
        if (!repositorioDisponible(repository, "pedidos")) {
            return;
        }

        List<Producto> productos = obtenerProductosDisponibles();
        if (productos.isEmpty()) {
            return;
        }

        Usuario usuario = leerUsuario(null);
        EstadoPedido estado = leerEstadoPedido(EstadoPedido.PENDIENTE);
        LocalDate fechaPedido = leerFechaOpcional("Fecha del pedido", LocalDate.now());
        List<PedidoDetalle> detalles = leerDetallesPedido(productos);
        Pedido pedido = new Pedido(null, usuario, detalles, estado, fechaPedido, calcularTotalPedido(detalles));
        Pedido creado = repository.create(pedido);

        System.out.println("Pedido creado con ID: " + creado.getId());
    }

    private void actualizarPedido() {
        PedidoRepository repository = pedidoRepository();
        if (!repositorioDisponible(repository, "pedidos")) {
            return;
        }

        Long id = leerLongPositivo("ID del pedido a actualizar: ");
        Optional<Pedido> pedidoActual = repository.findById(id);
        if (pedidoActual.isEmpty()) {
            System.out.println("No existe un pedido con ID " + id + ".");
            return;
        }

        Pedido actual = pedidoActual.get();
        mostrarPedido(actual);

        Usuario usuario = leerBooleano("¿Actualizar usuario? (s/n): ")
                ? leerUsuario(actual.getUsuario())
                : actual.getUsuario();
        EstadoPedido estado = leerEstadoPedido(actual.getEstado());
        LocalDate fechaPedido = leerFechaOpcional("Fecha del pedido", actual.getFechaPedido());
        List<PedidoDetalle> detalles = actual.getDetalles();

        if (leerBooleano("¿Reemplazar líneas del pedido? (s/n): ")) {
            List<Producto> productos = obtenerProductosDisponibles();
            if (productos.isEmpty()) {
                return;
            }
            detalles = leerDetallesPedido(productos);
        }

        Pedido pedido = new Pedido(id, usuario, detalles, estado, fechaPedido, calcularTotalPedido(detalles));
        repository.update(pedido);

        System.out.println("Pedido actualizado correctamente.");
    }

    private void eliminarPedido() {
        PedidoRepository repository = pedidoRepository();
        if (repositorioDisponible(repository, "pedidos")) {
            eliminarPorId(repository, "pedido");
        }
    }

    private void listarFacturas() {
        FacturaRepository repository = facturaRepository();
        if (!repositorioDisponible(repository, "facturas")) {
            return;
        }

        List<Factura> facturas = repository.findAll();
        if (facturas.isEmpty()) {
            System.out.println("No hay facturas registradas.");
            return;
        }

        facturas.forEach(this::mostrarFactura);
    }

    private void crearFactura() {
        FacturaRepository repository = facturaRepository();
        if (!repositorioDisponible(repository, "facturas")) {
            return;
        }

        Pedido pedido = seleccionarPedido();
        if (pedido == null) {
            return;
        }

        if (repository.findByPedidoId(pedido.getId()).isPresent()) {
            System.out.println("Ya existe una factura asociada al pedido " + pedido.getId() + ".");
            return;
        }

        LocalDate fechaFactura = leerFechaOpcional("Fecha de la factura", LocalDate.now());
        String metodoPago = leerTextoObligatorio("Método de pago: ");
        Factura factura = new Factura(null, pedido, fechaFactura, pedido.getTotal(), metodoPago);
        Factura creada = repository.create(factura);

        System.out.println("Factura creada con ID: " + creada.getId());
    }

    private void actualizarFactura() {
        FacturaRepository repository = facturaRepository();
        if (!repositorioDisponible(repository, "facturas")) {
            return;
        }

        Long id = leerLongPositivo("ID de la factura a actualizar: ");
        Optional<Factura> facturaActual = repository.findById(id);
        if (facturaActual.isEmpty()) {
            System.out.println("No existe una factura con ID " + id + ".");
            return;
        }

        Factura actual = facturaActual.get();
        mostrarFactura(actual);

        Pedido pedido = actual.getPedido();
        if (leerBooleano("¿Cambiar pedido asociado? (s/n): ")) {
            Pedido pedidoSeleccionado = seleccionarPedido();
            if (pedidoSeleccionado == null) {
                return;
            }
            pedido = pedidoSeleccionado;
        }

        LocalDate fechaFactura = leerFechaOpcional("Fecha de la factura", actual.getFechaFactura());
        String metodoPago = leerTextoOpcional("Método de pago", actual.getMetodoPago());
        double total = pedido != null ? pedido.getTotal() : actual.getTotal();
        Factura factura = new Factura(id, pedido, fechaFactura, total, metodoPago);
        repository.update(factura);

        System.out.println("Factura actualizada correctamente.");
    }

    private void eliminarFactura() {
        FacturaRepository repository = facturaRepository();
        if (repositorioDisponible(repository, "facturas")) {
            eliminarPorId(repository, "factura");
        }
    }

    private void listarPresupuestos() {
        PresupuestoRepository repository = presupuestoRepository();
        if (!repositorioDisponible(repository, "presupuestos")) {
            return;
        }

        if (repository.findAll().isEmpty()) {
            System.out.println("No hay presupuestos registrados.");
            return;
        }

        presupuestoService.mostrarResumenesGuardados();
    }

    private void crearPresupuesto() {
        PresupuestoRepository repository = presupuestoRepository();
        if (!repositorioDisponible(repository, "presupuestos")) {
            return;
        }

        List<Producto> productos = obtenerProductosDisponibles();
        if (productos.isEmpty()) {
            return;
        }

        String nombreProyecto = leerTextoObligatorio("Nombre del proyecto: ");
        List<PresupuestoDetalle> detalles = leerDetallesPresupuesto(productos);
        Presupuesto presupuesto = presupuestoService.crearPresupuesto(nombreProyecto, detalles);

        System.out.println("Presupuesto creado con ID: " + presupuesto.getId());
        presupuestoService.mostrarResumen(presupuesto);
    }

    private void actualizarPresupuesto() {
        PresupuestoRepository repository = presupuestoRepository();
        if (!repositorioDisponible(repository, "presupuestos")) {
            return;
        }

        Long id = leerLongPositivo("ID del presupuesto a actualizar: ");
        Optional<Presupuesto> presupuestoActual = repository.findById(id);
        if (presupuestoActual.isEmpty()) {
            System.out.println("No existe un presupuesto con ID " + id + ".");
            return;
        }

        Presupuesto actual = presupuestoActual.get();
        presupuestoService.mostrarResumen(actual);

        String nombreProyecto = leerTextoOpcional("Nombre del proyecto", actual.getNombreProyecto());
        Presupuesto presupuesto;

        if (leerBooleano("¿Reemplazar líneas del presupuesto? (s/n): ")) {
            List<Producto> productos = obtenerProductosDisponibles();
            if (productos.isEmpty()) {
                return;
            }

            presupuesto = presupuestoService.calcularPresupuesto(nombreProyecto, leerDetallesPresupuesto(productos));
            presupuesto.setId(id);
            presupuesto.setFechaCreacion(actual.getFechaCreacion() != null
                    ? actual.getFechaCreacion()
                    : presupuesto.getFechaCreacion());
        } else {
            presupuesto = copiarPresupuestoConNombre(actual, nombreProyecto);
            presupuestoService.validarPresupuesto(presupuesto);
        }

        repository.update(presupuesto);
        System.out.println("Presupuesto actualizado correctamente.");
    }

    private void eliminarPresupuesto() {
        PresupuestoRepository repository = presupuestoRepository();
        if (repositorioDisponible(repository, "presupuestos")) {
            eliminarPorId(repository, "presupuesto");
        }
    }

    private void listarPlantillas() {
        PlantillaRepository repository = plantillaRepository();
        if (!repositorioDisponible(repository, "plantillas constructivas")) {
            return;
        }

        List<PlantillaConstructiva> plantillas = repository.findAll();
        if (plantillas.isEmpty()) {
            System.out.println("No hay plantillas constructivas registradas.");
            return;
        }

        plantillas.forEach(this::mostrarPlantilla);
    }

    private void crearPlantilla() {
        PlantillaRepository repository = plantillaRepository();
        if (!repositorioDisponible(repository, "plantillas constructivas")) {
            return;
        }

        List<Producto> productos = obtenerProductosDisponibles();
        if (productos.isEmpty()) {
            return;
        }

        String nombre = leerTextoObligatorio("Nombre de la plantilla: ");
        String descripcion = leerTextoOpcional("Descripción", "");
        List<BloquePlantilla> bloques = leerBloquesPlantilla(productos);
        PlantillaConstructiva plantilla = new PlantillaConstructiva(null, nombre, descripcion, bloques);

        plantillaService.validarPlantilla(plantilla);
        PlantillaConstructiva creada = repository.create(plantilla);

        System.out.println("Plantilla creada con ID: " + creada.getId());
    }

    private void actualizarPlantilla() {
        PlantillaRepository repository = plantillaRepository();
        if (!repositorioDisponible(repository, "plantillas constructivas")) {
            return;
        }

        Long id = leerLongPositivo("ID de la plantilla a actualizar: ");
        Optional<PlantillaConstructiva> plantillaActual = repository.findById(id);
        if (plantillaActual.isEmpty()) {
            System.out.println("No existe una plantilla con ID " + id + ".");
            return;
        }

        PlantillaConstructiva actual = plantillaActual.get();
        mostrarPlantilla(actual);

        String nombre = leerTextoOpcional("Nombre de la plantilla", actual.getNombre());
        String descripcion = leerTextoOpcional("Descripción", actual.getDescripcion());
        List<BloquePlantilla> bloques = actual.getBloques();
        if (leerBooleano("¿Reemplazar bloques de la plantilla? (s/n): ")) {
            List<Producto> productos = obtenerProductosDisponibles();
            if (productos.isEmpty()) {
                return;
            }
            bloques = leerBloquesPlantilla(productos);
        }

        PlantillaConstructiva plantilla = new PlantillaConstructiva(id, nombre, descripcion, bloques);
        plantillaService.validarPlantilla(plantilla);
        repository.update(plantilla);

        System.out.println("Plantilla actualizada correctamente.");
    }

    private void eliminarPlantilla() {
        PlantillaRepository repository = plantillaRepository();
        if (repositorioDisponible(repository, "plantillas constructivas")) {
            eliminarPorId(repository, "plantilla");
        }
    }

    private Producto leerProducto(Producto actual) {
        String nombre = actual == null
                ? leerTextoObligatorio("Nombre: ")
                : leerTextoOpcional("Nombre", actual.getNombre());
        String descripcion = actual == null
                ? leerTextoOpcional("Descripción", "")
                : leerTextoOpcional("Descripción", actual.getDescripcion());
        double precio = actual == null
                ? leerDoubleNoNegativo("Precio: ")
                : leerDoubleNoNegativoOpcional("Precio", actual.getPrecio());
        TipoProducto tipo = leerTipoProducto(actual != null ? actual.getTipo() : TipoProducto.BLOQUE);
        String material = actual == null
                ? leerTextoObligatorio("Material: ")
                : leerTextoOpcional("Material", actual.getMaterial());
        double alto = actual == null
                ? leerDoublePositivo("Alto: ")
                : leerDoublePositivoOpcional("Alto", actual.getAlto());
        double ancho = actual == null
                ? leerDoublePositivo("Ancho: ")
                : leerDoublePositivoOpcional("Ancho", actual.getAncho());
        double largo = actual == null
                ? leerDoublePositivo("Largo: ")
                : leerDoublePositivoOpcional("Largo", actual.getLargo());
        Proveedor proveedor = leerProveedorProducto(actual != null ? actual.getProveedor() : null);

        return new Producto(
                actual != null ? actual.getId() : null,
                nombre,
                descripcion,
                precio,
                tipo,
                material,
                alto,
                ancho,
                largo,
                proveedor
        );
    }

    private Proveedor leerProveedor(Proveedor actual) {
        String nombreEmpresa = actual == null
                ? leerTextoObligatorio("Nombre de empresa: ")
                : leerTextoOpcional("Nombre de empresa", actual.getNombreEmpresa());
        String telefono = actual == null
                ? leerTextoOpcional("Teléfono", "")
                : leerTextoOpcional("Teléfono", actual.getTelefono());
        String sitioWeb = actual == null
                ? leerTextoOpcional("Sitio web", "")
                : leerTextoOpcional("Sitio web", actual.getSitioWeb());
        boolean validado = actual == null
                ? leerBooleano("¿Proveedor validado? (s/n): ")
                : leerBooleanoOpcional("¿Proveedor validado?", actual.isValidado());

        return new Proveedor(
                actual != null ? actual.getId() : null,
                nombreEmpresa,
                telefono,
                sitioWeb,
                validado
        );
    }

    private Usuario leerUsuario(Usuario actual) {
        Long id = actual == null
                ? leerLongPositivo("ID del usuario: ")
                : leerLongPositivoOpcional("ID del usuario", actual.getId());
        String nombre = actual == null
                ? leerTextoObligatorio("Nombre del usuario: ")
                : leerTextoOpcional("Nombre del usuario", actual.getNombre());
        String email = actual == null
                ? leerTextoOpcional("Email del usuario", "")
                : leerTextoOpcional("Email del usuario", actual.getEmail());
        RolUsuario rol = leerRolUsuario(actual != null ? actual.getRol() : RolUsuario.EMPLEADO);
        String password = actual != null ? actual.getPassword() : "";

        return new Usuario(id, nombre, email, password, rol);
    }

    private Proveedor leerProveedorProducto(Proveedor actual) {
        List<Proveedor> proveedores = proveedoresDisponibles();
        if (!proveedores.isEmpty()) {
            System.out.println("Proveedores disponibles:");
            proveedores.forEach(this::mostrarProveedorResumen);
        }

        Long proveedorId = actual == null
                ? leerLongPositivo("ID del proveedor: ")
                : leerLongPositivoOpcional("ID del proveedor", actual.getId());
        Optional<Proveedor> proveedorEncontrado = buscarProveedor(proveedores, proveedorId);

        while (!proveedores.isEmpty() && proveedorEncontrado.isEmpty()) {
            System.out.println("No existe un proveedor con ID " + proveedorId + ".");
            proveedorId = actual == null
                    ? leerLongPositivo("ID del proveedor: ")
                    : leerLongPositivoOpcional("ID del proveedor", actual.getId());
            proveedorEncontrado = buscarProveedor(proveedores, proveedorId);
        }

        if (proveedorEncontrado.isPresent()) {
            return proveedorEncontrado.get();
        }

        if (actual != null && proveedorId.equals(actual.getId())) {
            return actual;
        }

        return new Proveedor(proveedorId, "Proveedor " + proveedorId, "", "", false);
    }

    private Optional<Proveedor> buscarProveedor(List<Proveedor> proveedores, Long proveedorId) {
        return proveedores.stream()
                .filter(proveedor -> proveedorId.equals(proveedor.getId()))
                .findFirst();
    }

    private Presupuesto copiarPresupuestoConNombre(Presupuesto actual, String nombreProyecto) {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setId(actual.getId());
        presupuesto.setNombreProyecto(nombreProyecto);

        if (actual.getDetalles() != null && !actual.getDetalles().isEmpty()) {
            presupuesto.setDetalles(actual.getDetalles());
        } else {
            presupuesto.setProductos(actual.getProductos());
        }

        presupuesto.setCosteTotal(actual.getCosteTotal());
        presupuesto.setFechaCreacion(actual.getFechaCreacion());
        return presupuesto;
    }

    private List<PedidoDetalle> leerDetallesPedido(List<Producto> productos) {
        List<PedidoDetalle> detalles = new ArrayList<>();

        do {
            Producto producto = seleccionarProducto(productos);
            int cantidad = leerEnteroPositivo("Cantidad: ");
            detalles.add(new PedidoDetalle(null, producto, cantidad, producto.getPrecio() * cantidad));
        } while (leerBooleano("¿Añadir otra línea? (s/n): "));

        return detalles;
    }

    private List<PresupuestoDetalle> leerDetallesPresupuesto(List<Producto> productos) {
        List<PresupuestoDetalle> detalles = new ArrayList<>();

        do {
            Producto producto = seleccionarProducto(productos);
            int cantidad = leerEnteroPositivo("Cantidad: ");
            detalles.add(new PresupuestoDetalle(null, producto, cantidad, producto.getPrecio() * cantidad));
        } while (leerBooleano("¿Añadir otra línea? (s/n): "));

        return detalles;
    }

    private List<BloquePlantilla> leerBloquesPlantilla(List<Producto> productos) {
        List<BloquePlantilla> bloques = new ArrayList<>();

        do {
            Producto producto = seleccionarProducto(productos);
            int posicionX = leerEntero("Posición X: ");
            int posicionY = leerEntero("Posición Y: ");
            int posicionZ = leerEntero("Posición Z: ");
            int cantidad = leerEnteroPositivo("Cantidad: ");
            bloques.add(new BloquePlantilla(null, producto, posicionX, posicionY, posicionZ, cantidad));
        } while (leerBooleano("¿Añadir otro bloque? (s/n): "));

        return bloques;
    }

    private List<Producto> obtenerProductosDisponibles() {
        ProductoRepository repository = productoRepository();
        if (!repositorioDisponible(repository, "productos")) {
            return Collections.emptyList();
        }

        List<Producto> productos = repository.findAll();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados para completar esta operación.");
        }
        return productos;
    }

    private Producto seleccionarProducto(List<Producto> productos) {
        System.out.println("Productos disponibles:");
        productos.forEach(this::mostrarProductoResumen);

        while (true) {
            Long productoId = leerLongPositivo("ID del producto: ");
            Optional<Producto> producto = productos.stream()
                    .filter(item -> productoId.equals(item.getId()))
                    .findFirst();

            if (producto.isPresent()) {
                return producto.get();
            }

            System.out.println("No existe un producto con ID " + productoId + ".");
        }
    }

    private Pedido seleccionarPedido() {
        PedidoRepository repository = pedidoRepository();
        if (!repositorioDisponible(repository, "pedidos")) {
            return null;
        }

        List<Pedido> pedidos = repository.findAll();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados para asociar una factura.");
            return null;
        }

        System.out.println("Pedidos disponibles:");
        pedidos.forEach(this::mostrarPedidoResumen);

        while (true) {
            Long pedidoId = leerLongPositivo("ID del pedido: ");
            Optional<Pedido> pedido = repository.findById(pedidoId);

            if (pedido.isPresent()) {
                return pedido.get();
            }

            System.out.println("No existe un pedido con ID " + pedidoId + ".");
        }
    }

    private List<Proveedor> proveedoresDisponibles() {
        ProveedorRepository repository = proveedorRepository();
        if (repository == null) {
            return Collections.emptyList();
        }

        return repository.findAll();
    }

    private void mostrarProducto(Producto producto) {
        System.out.println(String.format(
                Locale.ROOT,
                "[%d] %s | tipo: %s | material: %s | precio: %.2f | medidas: %.2f x %.2f x %.2f | proveedor: %s",
                producto.getId(),
                valor(producto.getNombre()),
                producto.getTipo(),
                valor(producto.getMaterial()),
                producto.getPrecio(),
                producto.getAlto(),
                producto.getAncho(),
                producto.getLargo(),
                resumenProveedor(producto.getProveedor())
        ));

        if (producto.getDescripcion() != null && !producto.getDescripcion().isBlank()) {
            System.out.println("  " + producto.getDescripcion());
        }
    }

    private void mostrarProductoResumen(Producto producto) {
        System.out.println(String.format(
                Locale.ROOT,
                "[%d] %s | %.2f | %s",
                producto.getId(),
                valor(producto.getNombre()),
                producto.getPrecio(),
                valor(producto.getMaterial())
        ));
    }

    private void mostrarProveedor(Proveedor proveedor) {
        System.out.println(String.format(
                Locale.ROOT,
                "[%d] %s | teléfono: %s | web: %s | validado: %s",
                proveedor.getId(),
                valor(proveedor.getNombreEmpresa()),
                valor(proveedor.getTelefono()),
                valor(proveedor.getSitioWeb()),
                proveedor.isValidado() ? "sí" : "no"
        ));
    }

    private void mostrarProveedorResumen(Proveedor proveedor) {
        System.out.println("[" + proveedor.getId() + "] " + valor(proveedor.getNombreEmpresa()));
    }

    private void mostrarPedido(Pedido pedido) {
        mostrarPedidoResumen(pedido);

        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            System.out.println("  Sin líneas de pedido.");
            return;
        }

        pedido.getDetalles().forEach(detalle -> System.out.println(String.format(
                Locale.ROOT,
                "  - %s | cantidad: %d | subtotal: %.2f",
                detalle.getProducto() != null ? valor(detalle.getProducto().getNombre()) : "Producto sin datos",
                detalle.getCantidad(),
                detalle.getSubtotal()
        )));
    }

    private void mostrarPedidoResumen(Pedido pedido) {
        System.out.println(String.format(
                Locale.ROOT,
                "[%d] fecha: %s | estado: %s | usuario: %s | total: %.2f",
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getEstado(),
                resumenUsuario(pedido.getUsuario()),
                pedido.getTotal()
        ));
    }

    private void mostrarFactura(Factura factura) {
        System.out.println(String.format(
                Locale.ROOT,
                "[%d] pedido: %s | fecha: %s | método: %s | total: %.2f",
                factura.getId(),
                factura.getPedido() != null ? factura.getPedido().getId() : "sin pedido",
                factura.getFechaFactura(),
                valor(factura.getMetodoPago()),
                factura.getTotal()
        ));
    }

    private void mostrarPlantilla(PlantillaConstructiva plantilla) {
        System.out.println(String.format(
                Locale.ROOT,
                "[%d] %s | bloques: %d",
                plantilla.getId(),
                valor(plantilla.getNombre()),
                plantilla.getBloques() != null ? plantilla.getBloques().size() : 0
        ));

        if (plantilla.getDescripcion() != null && !plantilla.getDescripcion().isBlank()) {
            System.out.println("  " + plantilla.getDescripcion());
        }

        if (plantilla.getBloques() == null || plantilla.getBloques().isEmpty()) {
            System.out.println("  Sin bloques definidos.");
            return;
        }

        plantilla.getBloques().forEach(bloque -> System.out.println(String.format(
                Locale.ROOT,
                "  - %s | posición: (%d, %d, %d) | cantidad: %d",
                bloque.getProducto() != null ? valor(bloque.getProducto().getNombre()) : "Producto sin datos",
                bloque.getPosicionX(),
                bloque.getPosicionY(),
                bloque.getPosicionZ(),
                bloque.getCantidad()
        )));
    }

    private <T> void eliminarPorId(CrudRepository<T, Long> repository, String entidad) {
        Long id = leerLongPositivo("ID de " + entidad + " a eliminar: ");

        if (!repository.existsById(id)) {
            System.out.println("No existe " + articulo(entidad) + " " + entidad + " con ID " + id + ".");
            return;
        }

        repository.deleteById(id);
        System.out.println(capitalizar(entidad) + " " + participioEliminado(entidad) + " correctamente.");
    }

    // Mantiene el menu vivo cuando una validacion o repositorio rechaza la operacion.
    private void ejecutarOperacion(Runnable operacion) {
        try {
            operacion.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("No se pudo completar la operación: " + e.getMessage());
        }
    }

    private boolean repositorioDisponible(Object repository, String modulo) {
        if (repository == null) {
            System.out.println("No hay repositorio de " + modulo + " configurado.");
            return false;
        }

        return true;
    }

    private ProductoRepository productoRepository() {
        return productoService.getProductoRepository();
    }

    private ProveedorRepository proveedorRepository() {
        return proveedorService.getProveedorRepository();
    }

    private PedidoRepository pedidoRepository() {
        return pedidoService.getPedidoRepository();
    }

    private FacturaRepository facturaRepository() {
        return facturaService.getFacturaRepository();
    }

    private PresupuestoRepository presupuestoRepository() {
        return presupuestoService.getPresupuestoRepository();
    }

    private PlantillaRepository plantillaRepository() {
        return plantillaService.getPlantillaRepository();
    }

    public ProductoService getProductoService() {
        return productoService;
    }

    public ProveedorService getProveedorService() {
        return proveedorService;
    }

    public PedidoService getPedidoService() {
        return pedidoService;
    }

    public FacturaService getFacturaService() {
        return facturaService;
    }

    public PresupuestoService getPresupuestoService() {
        return presupuestoService;
    }

    public PlantillaService getPlantillaService() {
        return plantillaService;
    }

    public String getPersistenceType() {
        return persistenceType;
    }

    private int leerOpcion() {
        while (true) {
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Introduce un número.");
                System.out.print("Selecciona una opción: ");
            }
        }
    }

    private String leerTextoObligatorio(String prompt) {
        while (true) {
            System.out.print(prompt);
            String valor = scanner.nextLine().trim();

            if (!valor.isBlank()) {
                return valor;
            }

            System.out.println("El valor no puede estar vacío.");
        }
    }

    private String leerTextoOpcional(String etiqueta, String actual) {
        System.out.print(etiqueta + textoActual(actual) + ": ");
        String valor = scanner.nextLine().trim();
        return valor.isBlank() ? actual : valor;
    }

    private int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String valor = scanner.nextLine().trim();

            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero válido.");
            }
        }
    }

    private int leerEnteroPositivo(String prompt) {
        while (true) {
            int valor = leerEntero(prompt);

            if (valor > 0) {
                return valor;
            }

            System.out.println("El valor debe ser mayor que cero.");
        }
    }

    private Long leerLongPositivo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String valor = scanner.nextLine().trim();

            try {
                long id = Long.parseLong(valor);
                if (id > 0) {
                    return id;
                }
            } catch (NumberFormatException ignored) {
                // El mensaje comun se muestra al final del bucle.
            }

            System.out.println("Introduce un identificador positivo válido.");
        }
    }

    private Long leerLongPositivoOpcional(String etiqueta, Long actual) {
        while (true) {
            System.out.print(etiqueta + textoActual(actual) + ": ");
            String valor = scanner.nextLine().trim();

            if (valor.isBlank() && actual != null) {
                return actual;
            }

            try {
                long id = Long.parseLong(valor);
                if (id > 0) {
                    return id;
                }
            } catch (NumberFormatException ignored) {
                // El mensaje comun se muestra al final del bucle.
            }

            System.out.println("Introduce un identificador positivo válido.");
        }
    }

    private double leerDoubleNoNegativo(String prompt) {
        while (true) {
            double valor = leerDouble(prompt);

            if (valor >= 0) {
                return valor;
            }

            System.out.println("El valor no puede ser negativo.");
        }
    }

    private double leerDoubleNoNegativoOpcional(String etiqueta, double actual) {
        while (true) {
            double valor = leerDoubleOpcional(etiqueta, actual);

            if (valor >= 0) {
                return valor;
            }

            System.out.println("El valor no puede ser negativo.");
        }
    }

    private double leerDoublePositivo(String prompt) {
        while (true) {
            double valor = leerDouble(prompt);

            if (valor > 0) {
                return valor;
            }

            System.out.println("El valor debe ser mayor que cero.");
        }
    }

    private double leerDoublePositivoOpcional(String etiqueta, double actual) {
        while (true) {
            double valor = leerDoubleOpcional(etiqueta, actual);

            if (valor > 0) {
                return valor;
            }

            System.out.println("El valor debe ser mayor que cero.");
        }
    }

    private double leerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String valor = scanner.nextLine().trim().replace(',', '.');

            try {
                return Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número decimal válido.");
            }
        }
    }

    private double leerDoubleOpcional(String etiqueta, double actual) {
        while (true) {
            System.out.print(etiqueta + textoActual(format(actual)) + ": ");
            String valor = scanner.nextLine().trim().replace(',', '.');

            if (valor.isBlank()) {
                return actual;
            }

            try {
                return Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número decimal válido.");
            }
        }
    }

    private boolean leerBooleano(String prompt) {
        while (true) {
            System.out.print(prompt);
            String valor = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

            if (valor.equals("s") || valor.equals("si") || valor.equals("sí")) {
                return true;
            }

            if (valor.equals("n") || valor.equals("no")) {
                return false;
            }

            System.out.println("Responde con s o n.");
        }
    }

    private boolean leerBooleanoOpcional(String etiqueta, boolean actual) {
        while (true) {
            System.out.print(etiqueta + textoActual(actual ? "s" : "n") + " (s/n): ");
            String valor = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

            if (valor.isBlank()) {
                return actual;
            }

            if (valor.equals("s") || valor.equals("si") || valor.equals("sí")) {
                return true;
            }

            if (valor.equals("n") || valor.equals("no")) {
                return false;
            }

            System.out.println("Responde con s o n.");
        }
    }

    private LocalDate leerFechaOpcional(String etiqueta, LocalDate actual) {
        while (true) {
            System.out.print(etiqueta + textoActual(actual) + " (YYYY-MM-DD): ");
            String valor = scanner.nextLine().trim();

            if (valor.isBlank()) {
                return actual;
            }

            try {
                return LocalDate.parse(valor);
            } catch (DateTimeParseException e) {
                System.out.println("Introduce una fecha válida con formato YYYY-MM-DD.");
            }
        }
    }

    private TipoProducto leerTipoProducto(TipoProducto actual) {
        return leerEnum("Tipo de producto", TipoProducto.values(), actual);
    }

    private EstadoPedido leerEstadoPedido(EstadoPedido actual) {
        return leerEnum("Estado del pedido", EstadoPedido.values(), actual);
    }

    private RolUsuario leerRolUsuario(RolUsuario actual) {
        return leerEnum("Rol del usuario", RolUsuario.values(), actual);
    }

    private <E extends Enum<E>> E leerEnum(String etiqueta, E[] valores, E actual) {
        String opciones = Arrays.stream(valores)
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        while (true) {
            System.out.print(etiqueta + textoActual(actual) + " [" + opciones + "]: ");
            String valor = scanner.nextLine().trim();

            if (valor.isBlank() && actual != null) {
                return actual;
            }

            for (E opcion : valores) {
                if (opcion.name().equalsIgnoreCase(valor)) {
                    return opcion;
                }
            }

            System.out.println("Valor no válido. Opciones: " + opciones + ".");
        }
    }

    private double calcularTotalPedido(List<PedidoDetalle> detalles) {
        if (detalles == null) {
            return 0.0;
        }

        return detalles.stream()
                .mapToDouble(PedidoDetalle::getSubtotal)
                .sum();
    }

    private String resumenProveedor(Proveedor proveedor) {
        if (proveedor == null) {
            return "sin proveedor";
        }

        return "#" + proveedor.getId() + " " + valor(proveedor.getNombreEmpresa());
    }

    private String resumenUsuario(Usuario usuario) {
        if (usuario == null) {
            return "sin usuario";
        }

        return "#" + usuario.getId() + " " + valor(usuario.getNombre());
    }

    private String valor(String valor) {
        return valor == null || valor.isBlank() ? "sin dato" : valor;
    }

    private String textoActual(Object actual) {
        if (actual == null) {
            return "";
        }

        if (actual instanceof String texto && texto.isBlank()) {
            return "";
        }

        return " [" + actual + "]";
    }

    private String format(double valor) {
        return String.format(Locale.ROOT, "%.2f", valor);
    }

    private String capitalizar(String texto) {
        if (texto == null || texto.isBlank()) {
            return "";
        }

        return texto.substring(0, 1).toUpperCase(Locale.ROOT) + texto.substring(1);
    }

    private String articulo(String entidad) {
        return entidad.endsWith("a") ? "una" : "un";
    }

    private String participioEliminado(String entidad) {
        return entidad.endsWith("a") ? "eliminada" : "eliminado";
    }

    private enum Modulo {
        PRODUCTOS("productos"),
        PROVEEDORES("proveedores"),
        PEDIDOS("pedidos"),
        FACTURAS("facturas"),
        PRESUPUESTOS("presupuestos"),
        PLANTILLAS("plantillas constructivas");

        private final String etiqueta;

        Modulo(String etiqueta) {
            this.etiqueta = etiqueta;
        }
    }
}
