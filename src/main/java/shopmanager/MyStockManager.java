package shopmanager;
import com.hazelcast.core.*;

import exceptions.NoEnoughStock;
import exceptions.NotInStock;
import model.Product;
import persistency.ProductRepository;
import exceptions.UnknownRepo;
import exceptions.NotEmpty;

import com.hazelcast.config.*;

import java.util.Iterator;
import java.util.Optional;
import java.util.logging.*;
//va a seguir el patrón singleton (Una instancia sólo tiene un manager, pero puede haber varias instancias...)

/**
 * @author Isabel Román
 * @version 0.0. 
 * Primera versión de MyStockManager, clase que implementa StockManager usando el patrón singleton
 *
 */
public class MyStockManager implements StockManager{
	
	private static Logger trazador=Logger.getLogger(MyStockManager.class.getName());
	private static StockManager myInstance;
	private HazelcastInstance hzInstance;
	private Config cfg;
	//Mapa de productos que será compartido por todos los stockManager que se instancien
	private IMap<String, Product> mapProducts;
    /**
     * Repositorio para persistir el stock, los elementos del mismo serán productos
     */
	private ProductRepository repository;
	
	/**
	 * El constructor crea la instancia hazelcast, pero es privado, sólo la propia clase puede crear una instancia (un objeto)
	 */
	
	private MyStockManager(){
		cfg = new Config();
		
		hzInstance = Hazelcast.newHazelcastInstance(cfg);
		trazador.info("Acabo de crear una instancia hazelcast para el MyStockManager");
		mapProducts = hzInstance.getMap("products");
        trazador.info("Se ha recuperado referencia al mapa products, si no se había usado se ha creado");
	}
	
	public void setRepository(ProductRepository repo) {
		repository=repo;
	}
    //Inicializa el stock en memoria a partir de un repositorio que persistía los productos y que se pasa como parámetro
	public void init(ProductRepository repo) throws NotEmpty{
		repository=repo;
		//Si el mapa está vacio, recupera todos los productos del repositorio y los introduce en el stock (en memoria)
		if (mapProducts.isEmpty()) {
			Iterator<Product> products=repository.findAll().iterator();
	
			while(products.hasNext()) {
				addProduct(products.next());
			}
		}
		//Si el mapa no está vacío avisa de que no se puede iniciar, porque ya tiene productos y los cambios en el stock se perderían
		else 
			throw new NotEmpty();
	}	
	//Inicializa el stock en memoria a partir del repositorio configurado, si no se ha establecido el repositorio lanza una excepción advirtiéndolo
	public void init() throws NotEmpty,UnknownRepo{
		if(repository!=null) {
			if (mapProducts.isEmpty()) {
				Iterator<Product> products=repository.findAll().iterator();
		
				while(products.hasNext()) {
					addProduct(products.next());
				}
			}
			else 
				throw new NotEmpty();
		}
		else
			throw new UnknownRepo();
		
	}
	//Persiste los datos del stock en memoria en el repositorio, si el repositorio no estaba establecido lanza una excepción advirtiéndolo
	public void save() throws UnknownRepo{
		if (repository!=null)
			repository.saveAll(mapProducts.values());
		else
			throw new UnknownRepo();
	}
	
	@Override
	public StockManager getManager() {
		return getInstance();
	}
	@Override
	public void clean() {
		mapProducts.clear();
	}
	/**
	 * @deprecated
	 * @see 
	 * java.lang.Object
	 * El compilador me avisa de que uso algo "deprecated" por eso añado la opción de compilación en gradle, para ver más detalle
	*/
	@Override
	@Deprecated(since="0,0")
	protected void finalize() throws Throwable{
		trazador.info("Finalizando el MyStockManager, apaga la instancia hazelcast");
		hzInstance.shutdown();
		}
	
	public static StockManager getInstance() {
		if (myInstance==null){
			myInstance=new MyStockManager();
		}
		return myInstance;
	}
	@Override
	public void addProduct(Product newProduct) {
		Product productTmp;
		String msg="El tamaño del mapa al entrar es " + mapProducts.size();
		trazador.info(msg);
		if(mapProducts.containsKey(newProduct.getId())){
			productTmp=searchProduct(newProduct.getId()).get();
			newProduct.setNumber(productTmp.getNumber()+newProduct.getNumber());
		}
		mapProducts.put(newProduct.getId(), newProduct);
		msg="El mapa de tamaño " + mapProducts.size()+" incluye "+newProduct;
		trazador.info(msg);
	}
	@Override
	public Optional<Product> searchProduct(String id) {
		Product product=null;
		String msg;
		if(mapProducts.containsKey(id)){
			product=mapProducts.get(id);
			msg="Hay "+ product;
			trazador.info(msg);
		
		}else {
			msg="El "+product+" no está en el mapa";
			trazador.info(msg);
		}
		return Optional.ofNullable(product);
	}
	/**
	 * @throws CloneNotSupportedException avisa de que no se ha podido clonar 
	 * @see java.lang.Object
	 * 
	 * El método clone permite crear una instancia a partir de otra, lo que en una clase que siga el patrón singleton no se puede permitir
	 */
	@Override
	public MyStockManager clone() throws CloneNotSupportedException {
		trazador.warning("No se puede clonar un objeto de la clase MyStockManager, sigue patrón singleton");
	    throw new CloneNotSupportedException();
		}
	@Override
	/**
	 * <P>La clase Optional es un contenedor que dentro tendrá un producto o no (según se haya encontrado o no en el stock), el método isPresent permite saber si está o no
	 * Puede consultar <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html">Optional en la api de Java</a></P>
	 */
	public Product lessProduct(Product product) throws NoEnoughStock,NotInStock {
		Optional<Product> productTmp = searchProduct(product.getId());
		
		if(productTmp.isPresent())
		{
			if(productTmp.get().getNumber()<product.getNumber()) {
				throw new NoEnoughStock(productTmp.get().getNumber());
			}
			product.setNumber(productTmp.get().getNumber()-product.getNumber());
			mapProducts.put(product.getId(), product);
		}
		//Si no está debería lanzar la excepción no está en stock
		else throw new NotInStock(product.getId());
		return product;
	}
	
}
