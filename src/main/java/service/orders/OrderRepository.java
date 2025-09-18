package service.orders;

import model.Order;
import model.OrderItem;
import model.Product;

import java.util.List;

/**
 * Este repositorio contiene las funciones principales para manejar los carritos
 */

public interface OrderRepository {

    /**
     * Crea un nuevo carrito de compras y lo devuelve
     * @return Un carrito de compras nuevo
     */

    Order newOrder();

    /**
     * Elimina un carrito del sistema, si existe en el sistema y si ya está cerrado
     * @param id Id del carrito que se quiere eliminar del sistema
     * @return True si el carrito se ha eliminado correctamente, false en caso contrario
     */

    Boolean removeOrder(String id);

    /**
     * Cierra un carrito de compras y lo devuelve
     * @param order Carrito que se quiere cerrar
     * @return Carrito cerrado
     */

    Order closeOrder(Order order);

    /**
     * Cancela un carrito de compras, eliminando todos los pedidos que contiene
     * @param order Carrito que se quiere cancelar
     * @return True si el carrito se ha cancelado correctamente, false en caso contrario
     */

    Boolean cancelOrder(Order order);

    /**
     * Agrega un producto a un carrito
     * @param order Carrito al que se quiere agregar el producto
     * @param product producto que se quiere agregar al carrito
     * @param quantity cantidad que se quiere agregar al carrito
     * @return True si el producto se ha agregado correctamente, false en caso contrario
     */

    Boolean addProductToOrder(Order order, Product product, Integer quantity);

    /**
     * Elimina un producto en especifico de una orden
     * @param order carrito que se quiere modificar
     * @param product Producto que se quiere eliminar del carrito
     * @return True si el producto se ha eliminado correctamente, false en caso contrario
     */

    Boolean removeProductFromOrder(Order order, Product product);

    /**
     * Busca un carrito por su id y lo devuelve, si existe en el sistema
     * @param id Id del carrito que se quiere buscar
     * @return carrito encontrado en el sistema, o null en el caso de no encontrarlo
     */

    Order getOrderById(String id);

    List<Order> getOrders();

    /**
     * Actualiza la cantidad de un producto que hay dentro de un "Shopping Cart"
     * @param order El carrito que se quiere modificar
     * @param itemId El id del producto que se quiere modificar
     * @param quantity La nueva cantidad que se quiere asignar al producto
     * @return True si se ha actualizado correctamente, false en caso contrario
     */

    Boolean updateOrder(Order order, Integer itemId, Integer quantity);

}
