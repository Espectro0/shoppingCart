package service.orders;

import model.Order;
import model.OrderItem;
import model.Product;

import java.lang.Math;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryOrderManager implements OrderRepository {

    /**
     * Singleton, nos permite que la clase solo se pueda instanciar una vez
     * Por esto se utiliza una clase privada, para no permitir instanciarla desde fuera
     * y usar la funcion getInstance() para obtener la instancia de esta clase
     */

    private static final InMemoryOrderManager INSTANCE = new InMemoryOrderManager();
    List<Order> orders;

    private InMemoryOrderManager() {
        orders = new ArrayList<>();
    }

    public static InMemoryOrderManager getInstance() {
        return INSTANCE;
    }

    /**
     * Obtienes los carritos almacenados en el sistema, si no hay ninguno, se crea uno nuevo
     * @return Lista con todos los carritos almacenados en el sistema
     */

    @Override
    public List<Order> getOrders() {
        if (orders.isEmpty()) {
            newOrder();
            return orders;
        }
        return orders;
    }

    /**
     * Elimina un carrito del sistema
     * @param id Id del carrito que se quiere eliminar del sistema
     * @return True si el carrito se ha eliminado correctamente, false en caso contrario
     */

    @Override
    public Boolean removeOrder(String id) {
        Order order = getOrderById(id);

        if (order == null) {
            return false;
        }

        orders.remove(order);
        return true;
    }

    /**
     * Cierra un carrito y lo devuelve, cambiando el carrito actual por uno nuevo
     * @param order Carrito que se quiere cerrar
     * @return Carrito cerrado
     */

    @Override
    public Order closeOrder(Order order) {
        if (order == null || order.getOrderItems().isEmpty()) {
            return null;
        }

        double total = order.getTotal();
        double discount = (total > 100000.0) ? total * 0.05 : 0.0;

        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        order.setDate(date);
        order.setDiscount(discount);
        order.setCheckedOut(true);

        return order;
    }

    /**
     * Cancela un carrito y lo elimina del sistema
     * @param order Carrito que se quiere cancelar
     * @return True si el carrito se ha cancelado correctamente, false en caso contrario
     */

    @Override
    public Boolean cancelOrder(Order order) {
        if (order == null || order.getOrderItems().isEmpty()) {
            return false;
        }
        order.getOrderItems().clear();
        return true;
    }

    /**
     * Agrega un producto al carrito de compras, si existe en el sistema y tiene stock disponible
     * @param order Carrito al que se quiere agregar el producto
     * @param product producto que se quiere agregar al carrito
     * @param quantity cantidad que se quiere agregar al carrito
     * @return True si el producto se ha agregado correctamente, false en caso contrario
     */

    @Override
    public Boolean addProductToOrder(Order order, Product product, Integer quantity) {
        if (order == null || product == null || quantity == null || quantity <= 0 || product.getStock() < quantity) {
            return false;
        }
        for (OrderItem item : order.getOrderItems()) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return true;
            }
        }

        OrderItem orderItem = new OrderItem(product, quantity);
        order.getOrderItems().add(orderItem);
        return true;
    }

    /**
     * Elimina un producto del carrito de compras
     * @param order carrito que se quiere modificar
     * @param product Producto que se quiere eliminar del carrito
     * @return True si el producto se ha eliminado correctamente, false en caso contrario
     */

    @Override
    public Boolean removeProductFromOrder(Order order, Product product) {
        if (order == null || product == null) {
            return false;
        }

        for (OrderItem item : order.getOrderItems()) {
            if (item.getProduct().getId().equals(product.getId())) {
                order.getOrderItems().remove(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public Order getOrderById(String id) {
        return orders.stream().filter(
                o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Order newOrder() {
        Order order = new Order(UUID.randomUUID().toString(), new ArrayList<>(), 0.0);
        orders.add(order);
        return order;
    }

    /**
     * Actualiza la cantidad de un producto que hay dentro de un "Shopping Cart"
     * @param order El pedido que se quiere modificar
     * @param itemId El id del producto que se quiere modificar
     * @param quantity La nueva cantidad que se quiere asignar al producto
     * @return True si se ha actualizado correctamente, false en caso contrario
     */

    @Override
    public Boolean updateOrder(Order order, Integer itemId, Integer quantity) {
        for (OrderItem item : order.getOrderItems()) {
            if (item.getProduct().getId().equals(itemId) && item.getProduct().getStock() >= quantity) {
                if (quantity == 0) {
                    return true;
                }

                if (quantity < 0) {
                    int newQuantity = item.getQuantity() + quantity;
                    item.setQuantity(Math.max(0, newQuantity));
                    if (item.getQuantity() == 0) {
                        order.getOrderItems().remove(item);
                    }
                    return true;
                }

                item.setQuantity(item.getQuantity() + quantity);
                return true;
            }
        }
        return false;
    }
}