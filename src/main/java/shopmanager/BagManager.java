/**
 * 
 */
package shopmanager;

/**
 * @author isa
 *
 */

import java.util.Collection;
import java.util.Optional;
import exceptions.NoEnoughStock;
import exceptions.NotInStock;
import model.Product;
import model.Order;

/**
 * @author Isabel Román
 * Gestor de la cesta de la compra
 *
 */
public interface BagManager {
	

	/**
	 * 
	 * Añade a la cesta tantas unidades del producto pasado como parámetro como indique getNumber() del mismo, actualiza Stock eliminado las unidades añadidas
	 * 
	 * @param newProduct producto a añadir, en el número de unidades se indica cuántas unidades se añaden
	 * debe verificar si hay en stock, si no no se añaden y debería lanzar la excepción NoEnoughStock
	 * actualiza stock disminuyendo el número de unidades añadidas y aumenta el número de unidades en la cesta
	 * @return El producto tal y como está en la cesta
	 * @throws NoEnoughStock si el número de unidades en el stock no es suficiente
	 * @throws NotInStock si el producto no existe en el stock
	 */
	Product addProduct(Product newProduct) throws NoEnoughStock,NotInStock;
	/**
	 * 
	 * Elimina de la cesta tantas unidades del producto pasado como parámetro como indique getNumber() del mismo, el número mínimo de unidades final es cero
	 * 
	 * @param oldProduct producto a eliminar, se eliminan las unidades que se marquen, actualiza stock aumentando estas unidades liberadas
	 * @return newProduct, el producto, indicando el número de unidades que quedan en la cesta
	 * @throws NotInStock si el producto no estaba en el stock
	 */
	Product lessProduct(Product oldProduct) throws NotInStock;
	/**
	 * Elimina completamente el producto, actualiza stock sumando las unidades liberadas
	 * 
	 * @param oldProduct el producto con el número de elementos que se quieren liberar
	 * @return devuelve true si se eliminó, false si el producto no estaba en la cesta
	 * @throws NotInStock si no existía en el stock este producto
	 * 
	 */
	boolean removeProduct(Product oldProduct) throws NotInStock;
	/**
	 * 
	 * Elimina completamente el producto, actualiza stock sumando las unidades liberadas
	 * 
	 * @param productId el producto que se quiere eliminar
	 * @throws NotInStock si el producto no estaba en el stock
	 */
	void removeProduct(String productId) throws NotInStock;
	
	/**
	 * 
	 * Obtiene la cesta como una colección (Sea cual sea la forma interna de almacenar los productos)
	 * @return devuelve la cesta como una lista de productos
	 */
	Collection<Product> getBag();
	/**
	 * 
	 * Devuelve el producto cuyo id se pasa como parámetro encapsulado en un objeto Optional
	 * @see java.util.Optional
	 * @param productId el id del producto a buscar
	 * @return el producto, con el número de unidades del mismo, si es cero es que no estaba en la cesta
	 */
	Optional<Product> findProduct(String productId);
	/**
	 * 
	 * Devuelve el producto de la bolsa que se corresponde con el id del pasado, encapsulado en un objeto Optional
	 * @see java.util.Optional
	 * @param product producto a buscar
	 * @return el producto, con el número de unidades del mismo, si es cero es que no estaba en la cesta
	 */
	Optional<Product> findProduct(Product product);
	/**
	 * Realiza el pedido, persistiendo los datos del mismo en el repositorio
	 * @return devuelve el pedido
	 */
	Order order();
	
	/**
	 * Inicializa la cesta a cero, borra todo lo que había restaurando el stock
	 */
	void reset();

}
