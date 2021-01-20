package shopmanager;

import java.util.Iterator;
import java.util.Optional;
import exceptions.NoEnoughStock;
import exceptions.NotEmpty;
import exceptions.NotInStock;
import exceptions.UnknownRepo;
import model.Product;
import persistency.ProductRepository;

/**
 * @author Isabel Román
 * @version 0.0.
 * Primera versión de la interfaz StockManager, la forma de gestionar el stock compartido por distintas instancias hazelcast
 *
 */
public interface StockManager {
	/**
	 * Establece el repositorio e inicializa el stock con el contenido del mismo, siempre que estuviera vacío
	 * @param repo repositorio para la persistencia de productos
	 * @throws NotEmpty si el stock no estaba vacío
	 */
	public void init(ProductRepository repo) throws NotEmpty;
	
	/**
	 * Inicializa el stock con el contenido del repositorio, si no está establecido lanza una excepción
	 *
	 * @throws UnknownRepo si no está establecido el repositorio
	 * @throws NotEmpty si no está vacío el stock (como medida des eguridad para no borrar lo que ya hay)
	 */
	public void init() throws NotEmpty,UnknownRepo;
	/**
	 * Establece el repostorio para la persistencia
	 * @param repo repositorio de productos
	 */
	public void setRepository(ProductRepository repo);

	/**
	 * Persiste el stock (guarda lo que hay en memoria en el repositorio
	 * @throws UnknownRepo si no se estableció el repositorio
	 */
	public void save() throws UnknownRepo;
	/**
	 * 
	 * @param newProduct
	 * El producto a insertar en el stock, el campo de unidades dentro del producto indica cuántos se insertan.
	 * Si ya estaba en el mapa se suma el número de unidades.
	 * Si no estaba en el mapa se inserta el producto nuevo con el número de unidades indicado.
	 */
	void addProduct(Product newProduct);
	/**
	 * Disminuye el número de unidades que se indique en el producto que se pasa
	 * @param product producto del que se quieren disminuir las unidades
	 * @return devuelve el producto tal y como ha quedado en el stock (el número de unidades actualizado)
	 * @throws NoEnoughStock en caso de que no hubiera unidades suficientes, deja el producto como estaba
	 * @throws NotInStock si el producto no estaba en el stock
	 */
	
	Product lessProduct(Product product) throws NoEnoughStock, NotInStock;
	/**
	 * 
	 * Devuelve el producto 
	 * @param id Identificador del producto a buscar
	 * @return El producto buscado (con el número de unidades existentes), encapsulado en un objeto Optional
	 * @see java.util.Optional
	 */
	Optional<Product> searchProduct(String id);
	/**
	 * Vacia el stock
	 */
	void clean();
	/**
	 * Devuelve una instancia de StockManager
	 * 
	 * @return objeto único StockManager
	 */
	StockManager getManager();
	
}

