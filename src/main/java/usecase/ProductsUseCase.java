package usecase;

import model.Product;
import service.products.InFileProductManager;
import service.products.ProductRepository;

import java.util.List;

/**
 * Este caso de uso maneja los productos ya leeidos desde un archivo CSV
 */

public class ProductsUseCase {
    private final ProductRepository productRepository;

    public ProductsUseCase() {
        this.productRepository = InFileProductManager.getInstance();
    }

    /**
     * Obtiene todos los productos extraidos del archivo CSV
     * @return Una lista con todos los productos extraidos del archivo CSV
     */
    public List<Product> getStockProducts(){
        return productRepository.getProduct().stream()
                .toList();
    }
}
