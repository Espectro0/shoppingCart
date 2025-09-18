package usecase;

import model.Order;
import model.OrderItem;
import model.Product;
import service.orders.InMemoryOrderManager;
import service.orders.OrderRepository;
import service.products.InFileProductManager;
import service.products.ProductRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Este caso de uso maneja todas las operaciones relacionadas con el carrito de compras
 * y los pedidos realizados, permitiendo en un futuro guardar los pedidos en base de datos, o una lista fácilmente.
 */

public class ShoppingCartUseCase {

    /**
     * Permite conectarse a las funciones de producto dentro del sistema
     * estos es una forma de interactuar con los repositorios de product y order
     */

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    /**
     *  Inicializa el carrito de compras con una nueva orden
     */

    private Order order;

    /**
     * Constructor que nos vas a permitir conectarnos a las funcionalidades desde "application"
     */

    public ShoppingCartUseCase() {
        this.productRepository = InFileProductManager.getInstance();
        this.orderRepository = InMemoryOrderManager.getInstance();
    }

    /**
     * Obtiene todos los carritos que se han realizado en el sistema
     * @return Lista con todos los carritos que se han realizado en el sistema
     */

    public List<Order> getCarts() {
        return new ArrayList<>(this.orderRepository.getOrders());
    }

    /**
     * Obtiene el carrito de compras que se está manipulando actualmente
     * @return Carrito de compras actual
     */

    public Order getCart() {
        return this.order;
    }

    /**
     * Crea un nuevo carrito de compras y lo devuelve
     * @return Nuevo carrito de compras
     */

    public Order newCart() {
        return this.orderRepository.newOrder();
    }

    /**
     * Establece el carrito de compras que se quiere manipular actualmente
     * @param id Id del carrito de compras que se quiere manipular
     * @return True si el carrito se ha establecido correctamente, false en caso contrario
     */

    public boolean setCart(String id) {
        Order newOrder = this.orderRepository.getOrderById(id);
        if (this.orderRepository.getOrders().isEmpty() || newOrder == null) {
            return false;
        }

        this.order = newOrder;
        return true;
    }


    /**
     * Agrega un producto al carrito de compras, si existe en el sistema y tiene stock disponible
     * @param productId Id del producto que se quiere agregar al carrito
     * @param quantity Cantidad que se quiere agregar al carrito
     * @return True si el producto se ha agregado correctamente, false en caso contrario
     */

    public boolean addProductToCart(Integer productId, Integer quantity) {
        Product product = this.productRepository.findProductById(productId);
        if (product == null || quantity <= 0 || product.getStock() < quantity) {
            return false;
        }

        if (this.orderRepository.addProductToOrder(this.order, product, quantity)) {
            this.productRepository.updateStock(productId, product.getStock() - quantity);
            return true;
        }
        return false;
    }

    /**
     * Elimina un producto del carrito de compras, si existe en el sistema y tiene stock disponible
     * @param productId Id del producto que se quiere eliminar del carrito
     * @return True si el producto se ha eliminado correctamente, false en caso contrario
     */

    public boolean removeProductFromCart(Integer productId) {
        Product product = this.productRepository.findProductById(productId);
        if (this.orderRepository.removeProductFromOrder(this.order, product)) {
            this.productRepository.updateStock(productId, product.getStock() + this.order.getOrderItems().stream()
                    .filter(p -> p.getProduct().getId().equals(productId))
                    .mapToInt(OrderItem::getQuantity)
                    .findFirst()
                    .orElse(0)
            );

            return true;
        }
        return false;
    }

    /**
     * Actualiza la cantidad de un producto en el carrito de compras, si existe en el sistema y tiene stock disponible
     * @param itemId Id del producto que se quiere actualizar
     * @param quantity Cantidad que se quiere asignar al producto
     * @return True si el producto se ha actualizado correctamente, false en caso contrario
     */

    public boolean updateProductInCart(Integer itemId, Integer quantity) {
        if (this.orderRepository.updateOrder(order, itemId, quantity)) {
            this.productRepository.updateStock(itemId, Math.max(0, (this.productRepository.findProductById(itemId).getStock() - quantity)));
            return true;
        }
        return false;
    }

    /**
     * Cierra el carrito de compras y lo devuelve, cambiando el carrito actual por uno nuevo
     * @return Carrito de compras cerrado
     */

    public Order closeOrder() {
        Order oldOrder = this.orderRepository.closeOrder(this.order);
        this.order = this.orderRepository.newOrder();
        return oldOrder;
    }

    /**
     * Cancela el carrito de compras actual
     * @return True si el carrito se ha cancelado correctamente, false en caso contrario
     */

    public boolean cancelOrder() {
        if (order.getOrderItems().isEmpty()) {
            return false;
        }
        for (OrderItem item : order.getOrderItems()) {
            Product product = this.productRepository.findProductById(item.getProduct().getId());
            if (product != null) {
                this.productRepository.updateStock(product.getId(), (product.getStock() + item.getQuantity()));
            }
        }

        return this.orderRepository.cancelOrder(this.order);
    }
}
