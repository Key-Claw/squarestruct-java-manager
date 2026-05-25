package com.squarestruct.manager.ui.menu;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion();

            switch (opcion) {
                case 1 -> mostrarSubmenu("productos");
                case 2 -> mostrarSubmenu("proveedores");
                case 3 -> mostrarSubmenu("pedidos");
                case 4 -> mostrarSubmenu("facturas");
                case 5 -> mostrarSubmenu("presupuestos");
                case 6 -> mostrarSubmenu("plantillas constructivas");
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
        System.out.println("1. Gestión de productos");
        System.out.println("2. Gestión de proveedores");
        System.out.println("3. Gestión de pedidos");
        System.out.println("4. Gestión de facturas");
        System.out.println("5. Gestión de presupuestos");
        System.out.println("6. Gestión de plantillas constructivas");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private void mostrarSubmenu(String modulo) {
        int opcion;

        do {
            System.out.println();
            System.out.println("----- Gestión de " + modulo + " -----");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menú principal");
            System.out.print("Selecciona una opción: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1 -> mostrarFuncionalidadPendiente("Listar " + modulo);
                case 2 -> mostrarFuncionalidadPendiente("Crear " + modulo);
                case 3 -> mostrarFuncionalidadPendiente("Actualizar " + modulo);
                case 4 -> mostrarFuncionalidadPendiente("Eliminar " + modulo);
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

        } while (opcion != 0);
    }

    private void mostrarFuncionalidadPendiente(String accion) {
        System.out.println("Funcionalidad pendiente: " + accion);
        System.out.println("Esta opción se conectará con la capa de servicios en futuras iteraciones.");
    }

    private int leerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada no válida. Introduce un número.");
            scanner.next();
            System.out.print("Selecciona una opción: ");
        }

        int opcion = scanner.nextInt();
        scanner.nextLine();

        return opcion;
    }
}