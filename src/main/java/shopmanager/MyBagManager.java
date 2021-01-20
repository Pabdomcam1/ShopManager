/**
 * 
 */
package shopmanager;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Collection;
import java.util.HashMap;

import java.util.logging.Logger;

import exceptions.NoEnoughStock;
import exceptions.NotInStock;
import exceptions.UnknownRepo;
import model.MyOrder;
import model.MyProduct;
import model.Order;
import model.Product;
import persistency.OrderRepository;
import shopmanager.StockManager;
/**
 * @author Isabel Román
 * @author Pablo Domínguez
 *
 */
public class MyBagManager implements BagManager {
	private static Logger trazador=Logger.getLogger(MyStockManager.class.getName());
	private Map<String,Product> cesta;
	private OrderRepository repositorio;
	private StockManager stock;
	private Order order;
	
	/**
	 * Al hacer privado el constructor obligo a que la construcción del objeto se haga siempre a través de newBag
	 */
	public MyBagManager(){
		cesta=new HashMap<String,Product>();
	}

	public MyBagManager(OrderRepository repo,StockManager stockManager){
		cesta=new HashMap<String,Product>();
		repositorio=repo;
		stock=stockManager;
	}



	@Override
	public Product addProduct(Product newProduct)throws NoEnoughStock, NotInStock{
		String msg="El id del producto es "+newProduct.getId()+" y las unidades a añadir"+newProduct.getNumber();
		trazador.info(msg);
		//quito del stock las unidades solicitadas, si no hubiera suficientes lanza NoEnoughStock, si el producto no existe lanza NotInStock
		stock.lessProduct(newProduct);
		if(cesta.containsKey(newProduct.getId())){
				int antes=cesta.get(newProduct.getId()).getNumber();
				newProduct.setNumber(newProduct.getNumber()+antes);
				cesta.put(newProduct.getId(), newProduct);
			}
		else
			cesta.put(newProduct.getId(), newProduct);
		return newProduct;
	}
	
	@Override
	public Product lessProduct(Product oldProduct)throws NotInStock {
		String msg="El id del producto es "+oldProduct.getId()+" y las unidades a quitar "+oldProduct.getNumber();
		trazador.info(msg);
		
		//Creamos el nuevo producto a devolver
		Product newProduct = new MyProduct(oldProduct.getId(), 0);
		
		//Comprobamos si el producto no esta en el stock, en ese caso lanza la excepcion NotInStock
		if(stock.searchProduct(oldProduct.getId()) == null) {
			throw new NotInStock(oldProduct.getId());
		}else {
			
			//Comprobamos si el producto esta en la cesta
			if(cesta.containsKey(oldProduct.getId())){
			
				//Unidades que hay en la cesta
				int antes=cesta.get(oldProduct.getId()).getNumber();	
				
				//Comprobamos si el numero de unidades a eliminar es mayor que las que tenemos en la cesta
				if (antes>=oldProduct.getNumber()) {
						
						//añadimos al stock y eliminamos de la cesta las unidades indicadas
						stock.addProduct(oldProduct);
						newProduct.setNumber(antes - oldProduct.getNumber());
						cesta.put(newProduct.getId(), newProduct);
				}else {
					
					msg="El producto con id "+oldProduct.getId()+" tiene menos unidades de las que se desean borrar,por lo que se borran las que hay";
					trazador.info(msg);
					
					//Añadimos al stock las unidades actuales
					oldProduct.setNumber(antes);
					stock.addProduct(oldProduct);
					
					//Eliminamos todas las unidades del producto y lo devolvemos
					newProduct.setNumber(0);
					cesta.put(newProduct.getId(), newProduct);
				}
			}else {
				
			msg="El producto con "+oldProduct.getId()+" no se encuentra en la cesta y por tanto hay cero unidades";
			trazador.info(msg);
			newProduct.setNumber(0);
			/*Aqui podriamos añadir el producto con cero unidades a la cesta o no añadirlo. Hemos decidido no añadirlo*/
			}
		}
		return newProduct;
	}

	@Override
	public boolean removeProduct(Product oldProduct)throws NotInStock {
		// TODO Auto-generated method stub
		return false;

	}

	@Override
	public void removeProduct(String productId)throws NotInStock {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Product> getBag() {
			
		return (List<Product>) cesta.values();
	}

	@Override
	public Optional<Product> findProduct(String productId) {
		Product producto=null;
		if(cesta.containsKey(productId))
			producto=cesta.get(productId);
			
		return Optional.ofNullable(producto);
	}

	@Override
	public Optional<Product> findProduct(Product product) {
		return findProduct(product.getId());
	}
	
	@Override
	public Order order() {
		// No crea el objeto order, aún no está resuelto quién será el responsable de elegir el tipo concreto
		try{ 	       
		   trazador.info("Intento persistir el stock");
	       stock.save();	
	       trazador.info("Actualizo el pedido");
	       order.setProducts(cesta.values());	
	       trazador.info("Persisto el pedido");
	       repositorio.save(order);
	    
		} catch (UnknownRepo ex) {
			trazador.info("No ha sido posible guardar el pedido, no se estableció el repositorio en el stock");
		}
	    	
		return order;
	}

	@Override
	public void reset() {
		// Debería restaurar el stock, pero por ahora no se hace, sólo borra
		cesta.clear();
		
	}

}
