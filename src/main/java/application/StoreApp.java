package application;

import model.Order;
import model.OrderItem;
import model.Product;
import usecase.ProductsUseCase;
import usecase.ShoppingCartUseCase;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Esta clase contiene la aplicación principal del sistema de carrito de compras
 * Se encarga de manejar los casos de uso de los UseCases
 * y tambien se puede decir que es la clase principal de la aplicación para permitir el uso del usuario
 */

public class StoreApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ProductsUseCase productsUseCase = new ProductsUseCase();
        ShoppingCartUseCase shoppingCartUseCase = new ShoppingCartUseCase();

        boolean running = true;

        while (running) {
            System.out.println("\nBienvenido al Sistema de Carrito de Compras\nQue desea realizar?");
            System.out.println("1. Crear un nuevo carrito de compras");
            System.out.println("2. Mostrar la lista de carritos existentes");
            System.out.println("3. Seleccionar un carrito existente");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int option;

            try {
                option = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Opción inválida.");
                sc.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    createCart(shoppingCartUseCase);
                    break;
                case 2:
                    showCarts(shoppingCartUseCase);
                    break;
                case 3:
                    showCarts(shoppingCartUseCase);
                    System.out.print("\nIngrese el ID del carrito: ");
                    if (sc.hasNextLine()) {
                        String cartId = sc.nextLine();
                        selectCart(shoppingCartUseCase, cartId);
                    }
                    break;
                case 4:
                    running = false;
                    System.out.println("\nGracias por utilizar el Sistema de Carrito de Compras.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        sc.close();
    }

    static DecimalFormat df = new DecimalFormat("#,###.00");

    private static void createCart(ShoppingCartUseCase shoppingCartUseCase) {
        List<Order> orders = shoppingCartUseCase.getCarts();
        Order order = shoppingCartUseCase.newCart();
        orders.add(order);
        System.out.println("Se ha creado un nuevo carrito de compras con ID " + order.getId());
    }

    private static void showCarts(ShoppingCartUseCase shoppingCartUseCase) {
        List<Order> orders = shoppingCartUseCase.getCarts();
        if (orders.isEmpty()) {
            System.out.println("No hay carritos abiertos.");
            return;
        }

        System.out.println("--------------------------------------------------");
        System.out.println("           Lista de carritos abiertos:");
        System.out.println("--------------------------------------------------");
        System.out.println("# | Id del Carrito                       | Estado");
        System.out.println("--------------------------------------------------");
        int c = 0;
        for (Order order : orders) {
            System.out.println(c + " | " + order.getId() + " | " + ((order.getCheckedOut()) ? "Cerrado" : "Abierto"));
            c++;
        }
    }

    private static void selectCart(ShoppingCartUseCase shoppingCartUseCase, String cartId) {
        if (!shoppingCartUseCase.setCart(cartId) || cartId == null) {
            System.out.println("Debe de seleccionar un carrito existente abierto.");
            return;
        }

        System.out.println("Se ha seleccionado el carrito con ID " + cartId + "\n");
        cartMenu(shoppingCartUseCase);
    }

    private static void cartMenu(ShoppingCartUseCase shoppingCartUseCase) {
        Scanner sc = new Scanner(System.in);
        boolean cartMenuRunning = true;
        ProductsUseCase productsUseCase = new ProductsUseCase();

        while (cartMenuRunning) {
            System.out.println("\nCarrito: " + shoppingCartUseCase.getCart().getId() + "\nEstado: " + (shoppingCartUseCase.getCart().getCheckedOut() ? "Cerrado" : "Abierto") + "\n");

            if (!shoppingCartUseCase.getCart().getCheckedOut()) {
                System.out.println("1. Agregar producto al carrito");
                System.out.println("2. Eliminar producto del carrito");
                System.out.println("3. Mostrar productos en el carrito");
                System.out.println("4. Actualizar Cantidades de un producto");
                System.out.println("5. Checkout");
                System.out.println("6. Cancelar carrito");
                System.out.println("7. Volver al menú principal");
            } else {
                System.out.println("1. Mostrar productos en el carrito");
                System.out.println("2. Volver al menú principal");
            }

            System.out.print("Seleccione una opción: ");

            int option;
            try {
                option = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Opción inválida.");
                sc.nextLine();
                continue;
            }

            if (!shoppingCartUseCase.getCart().getCheckedOut()) {
                switch (option) {
                    case 1:
                        addProductToCart(shoppingCartUseCase, productsUseCase);
                        break;
                    case 2:
                        removeProductFromCart(shoppingCartUseCase);
                        break;
                    case 3:
                        showCartProducts(shoppingCartUseCase);
                        break;
                    case 4:
                        updateProductQuantity(shoppingCartUseCase);
                        break;
                    case 5:
                        checkoutCart(shoppingCartUseCase);
                        break;
                    case 6:
                        cancelCart(shoppingCartUseCase);
                        break;
                    case 7:
                        cartMenuRunning = false;
                        break;
                    default:
                        System.out.println("Opción inválida");
                        break;
                }
            } else {
                switch (option) {
                    case 1:
                        showCartProducts(shoppingCartUseCase);
                        break;
                    case 2:
                        cartMenuRunning = false;
                        break;
                    default:
                        System.out.println("Opción inválida");
                        break;
                }
            }
        }
    }

    private static void addProductToCart(ShoppingCartUseCase shoppingCartUseCase, ProductsUseCase productsUseCase) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--------------------------------------------------");
        System.out.println("           Productos Disponibles:");
        System.out.println("--------------------------------------------------");
        System.out.println("Id | Nombre | Descripción | Precio | Stock ");
        System.out.println("--------------------------------------------------");

        List<Product> products = productsUseCase.getStockProducts();
        for (Product product : products) {
                System.out.println(product);
        }

        try {
            System.out.print("\nIngrese el ID del producto: ");
            int productId = sc.nextInt();
            System.out.print("Ingrese la cantidad: ");
            int quantity = sc.nextInt();

            boolean added = shoppingCartUseCase.addProductToCart(productId, quantity);
            if (added) {
                System.out.println("Producto agregado exitosamente al carrito.");
            } else {
                System.out.println("No se pudo agregar el producto, verifique que haya ingresado una ID valida o una cantidad dentro las existencias.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar valores numéricos.");
            sc.nextLine();
        }
    }

    private static void removeProductFromCart(ShoppingCartUseCase shoppingCartUseCase) {
        Scanner sc = new Scanner(System.in);

        showCartProducts(shoppingCartUseCase);

        if (shoppingCartUseCase.getCart().getOrderItems().isEmpty()) {
            return;
        }

        try {
            System.out.print("\nIngrese el ID del producto a eliminar: ");
            int productId = sc.nextInt();

            boolean removed = shoppingCartUseCase.removeProductFromCart(productId);
            if (removed) {
                System.out.println("Producto eliminado exitosamente del carrito.");
            } else {
                System.out.println("No se pudo eliminar el producto, verifique que haya ingresado una ID valida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un valor numérico.");
            sc.nextLine();
        }
    }

    private static void showCartProducts(ShoppingCartUseCase shoppingCartUseCase) {
        Order cart = shoppingCartUseCase.getCart();

        if (cart.getOrderItems().isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }

        System.out.println("--------------------------------------------------");
        System.out.println("           Productos en el carrito:");
        System.out.println("--------------------------------------------------");
        for (OrderItem item : cart.getOrderItems()) {
            System.out.println(item);
        }

        System.out.println("\nTotal del carrito: $" + df.format(cart.getTotal()));

        if (cart.getCheckedOut()) {
            System.out.println("Fecha: " + cart.getDate());
            System.out.println("Descuento: $" + df.format(cart.getDiscount()));
            System.out.println("Total con descuento: $" + df.format((cart.getTotal() - cart.getDiscount())));
        }
    }

    private static void updateProductQuantity(ShoppingCartUseCase shoppingCartUseCase) {
        Scanner sc = new Scanner(System.in);

        showCartProducts(shoppingCartUseCase);

        if (shoppingCartUseCase.getCart().getOrderItems().isEmpty()) {
            return;
        }

        try {
            System.out.print("\nIngrese el ID del producto a actualizar: ");
            int productId = sc.nextInt();
            System.out.print("Ingrese la cantidad a agregar (Ingrese números negativos para restar y positivos para agregar): ");
            int quantity = sc.nextInt();

            boolean updated = shoppingCartUseCase.updateProductInCart(productId, quantity);
            if (updated) {
                System.out.println("Cantidad actualizada exitosamente.");
            } else {
                System.out.println("No se pudo actualizar la cantidad, verifique que haya ingresado una ID valida, o una cantidad dentro las existencias.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar valores numéricos.");
            sc.nextLine();
        }
    }

    private static void checkoutCart(ShoppingCartUseCase shoppingCartUseCase) {
        if (shoppingCartUseCase.getCart().getOrderItems().isEmpty()) {
            System.out.println("No se puede realizar el checkout de un carrito vacío.");
            return;
        }

        Order orderClosed = shoppingCartUseCase.closeOrder();
        if (orderClosed != null) {
            System.out.println("--------------------------------------------------");
            System.out.println("           Checkout Exitoso:");
            System.out.println("--------------------------------------------------");
            System.out.println("Factura generada en fecha: " + orderClosed.getDate());
            System.out.println("ID del pedido: " + orderClosed.getId());
            System.out.println("\nProductos:");
            for (OrderItem item : orderClosed.getOrderItems()) {
                System.out.println("- " + item);
            }
            System.out.println("\nSubtotal: $" + df.format(orderClosed.getTotal()));
            System.out.println("Descuento: $" + df.format(orderClosed.getDiscount()));
            System.out.println("Total: $" + df.format((orderClosed.getTotal() - orderClosed.getDiscount())));
        } else {
            System.out.println("No se pudo completar el checkout.");
        }
    }

    private static void cancelCart(ShoppingCartUseCase shoppingCartUseCase) {
        if (shoppingCartUseCase.getCart().getOrderItems().isEmpty()) {
            System.out.println("El carrito ya está vacío.");
            return;
        }

        boolean cancelled = shoppingCartUseCase.cancelOrder();
        if (cancelled) {
            System.out.println("Carrito cancelado exitosamente. Todos los productos han sido devueltos al inventario.");
        } else {
            System.out.println("No se pudo cancelar el carrito.");
        }
    }

}