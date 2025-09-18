## Sistema de Gestión de Carrito de Compras

Sistema desarrollado en Java que simula el flujo de un carrito de compras, aplicando principios de Programación Orientada a Objetos (POO) y Arquitectura Hexagonal, con análisis previo mediante diagramas UML.  
El resultado es una aplicación de consola que permite visualizar, agregar, eliminar y modificar la cantidad de productos en el carrito, así como cancelar o finalizar la compra de manera sencilla.

* ***Ejemplo de uso:***

```bash
Bienvenido al Sistema de Carrito de Compras
Que desea realizar?
1. Crear un nuevo carrito de compras
2. Mostrar la lista de carritos existentes
3. Seleccionar un carrito existente
4. Salir
Seleccione una opción: 3

--------------------------------------------------
           Lista de carritos abiertos:
--------------------------------------------------
# | Id del Carrito                       | Estado
--------------------------------------------------
0 | 1d904792-7304-4ec3-8be3-442163b76e93 | Abierto

Ingrese el ID del carrito: 1d904792-7304-4ec3-8be3-442163b76e93
Se ha seleccionado el carrito con ID 1d904792-7304-4ec3-8be3-442163b76e93

Carrito: 1d904792-7304-4ec3-8be3-442163b76e93
Estado: Abierto

1. Agregar producto al carrito
2. Eliminar producto del carrito
3. Mostrar productos en el carrito
4. Actualizar Cantidades de un producto
5. Checkout
6. Cancelar carrito
7. Volver al menú principal
Seleccione una opción: 5

--------------------------------------------------
           Checkout Exitoso:
--------------------------------------------------
Factura generada en fecha: 16-09-2025 11:49:06
ID del pedido: 1d904792-7304-4ec3-8be3-442163b76e93

Productos:
- Laptop Lenovo [ 2 ] - $ 5.600.000,00
- Mouse Logitech [ 3 ] - $ 255.000,00
- Smartphone Samsung [ 1 ] - $ 3.400.000,00

Subtotal: $9.255.000,00
Descuento: $462.750,00
Total: $8.792.250,00
```

### Configuración del sistema

**Catálogo de productos:** Crear el archivo `/resources/products.csv` con formato CSV:

```
id;name;description;price;stock
1;Laptop Lenovo;Portátil 15'' Ryzen 5 8GB RAM 512GB SSD;2800000;10
2;Mouse Logitech;Mouse inalámbrico óptico;85000;10
3;Teclado Mecánico;Teclado RGB switches azules;220000;10
```

**Para ejecutar el proyecto:** Compilar y ejecutar desde la clase principal:

```bash
javac -d bin src/main/java/**/*.java
java -cp bin application.StoreApp
```

### Tecnologías

* ***Java 24:*** Lenguaje de programación principal
* ***Maven:*** Build Tool
* ***POO:*** Encapsulamiento, herencia y polimorfismo
* ***Arquitectura Hexagonal:*** Separación de responsabilidades
* ***UML:*** Representación gráfica del planteamiento inicial del problema

### Diagrama UML
_El archivo svg se encuentra en la carpeta `src/main/resources`_

![Diagrama UML](./src/main/resources/Diagrama.svg)