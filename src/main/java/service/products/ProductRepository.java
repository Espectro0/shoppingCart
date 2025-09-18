package service.products;
import model.Product;

import java.util.List;

/**
 * En este repositorio se contienen las funciones principales para poder manejar correctamente los productos
 */

public interface ProductRepository {

    /**
     *  Obtiene los productos que esten disponibles en el sistema
     * @return Lista de Productos
     */

    List<Product> getProduct();

    /**
     * Busca el id de un producto, y si lo encuentra devuelve el producto
     * @param id Corresponde al id del producto que se quiere buscar
     * @return Producto encontrado en el sistema
     */

    Product findProductById(Integer id);

    /**
     * Busca el producto por su id y actualiza su stock
     * @param id El id del producto que se quiere modificar
     * @param quantity La nueva cantidad que se quiere asignar al producto
     */

    void updateStock(Integer id, Integer quantity);
}
