package service.products;

import model.Product;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase maneja los productos ya manejados en un archivo CSV
 */

public class InFileProductManager implements ProductRepository {

    /**
     * 1. Singleton, nos permite que la clase solo se pueda instanciar una vez
     * 2. Lista que almacena los productos cargados desde un archivo CSV
     * 3. Direccion del archivo CSV, que se encuentra en la carpeta resources
     */

    private static final InFileProductManager INSTANCE = new InFileProductManager(); // 1. ...
    private final List<Product> product = new ArrayList<>(); // 2. ...
    private static final String PATH_FILE = "products.csv"; // 3. ...

    /**
     * Constructor privado, esto permite no crear más de una instancia de esta clase
     */

    private InFileProductManager() {
        loadProducts();
    }

    /**
     * Esta función es la unica manera de la que podemos obtener la instancia de esta clase
     * @return Instancia de la clase
     */

    public static InFileProductManager getInstance() {
        return INSTANCE;
    }

    /**
     *  Obtiene los productos que esten disponibles en el sistema
     * @return Lista de Productos
     */

    @Override
    public List<Product> getProduct() {
        return product;
    }

    /**
     * Obtiene un producto, proporcionando simplemente su Id
     * @param id Corresponde al id del producto que se quiere buscar
     * @return Producto encontrado por el sistema, o null en el caso de no encontrarlo
     */

    @Override
    public Product findProductById(Integer id) {
        return product.stream().filter(
                        p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Modifica la cantidad de stock de un producto, proporcionando su Id y la nueva cantidad que se quiere asignar
     *
     * @param id       El id del producto que se quiere modificar
     * @param quantity La nueva cantidad que se quiere asignar al producto
     */

    @Override
    public void updateStock(Integer id, Integer quantity) {
        for (Product item : product) {
            if (item.getId().equals(id)) {
                if (quantity < 0) {
                    return;
                }
                item.setStock(quantity);
                return;
            }
        }
    }

    /**
     * Carga los productos desde un archivo CSV
     * Ignora la primera línea que es la que trae la información del archivo, para nuestro caso:
     * id;name;description;price;stock
     * Obtiene los 5 datos que corresponden a cada producto y los guarda en una lista
     */

    private void loadProducts() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PATH_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = line.split(";");
                if (data.length == 5) {
                    product.add(new Product(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            Double.parseDouble(data[3]),
                            Integer.parseInt(data[4])
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los productos: " + e.getMessage());
        }
    }
}