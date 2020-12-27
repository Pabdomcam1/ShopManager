package shopManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import shopmanager.MyStockManager;
import shopmanager.StockManager;

import exceptions.NotInStock;
import model.MyProduct;
import model.Product;
import exceptions.NoEnoughStock;

/**
 * 
 * @author Pablo Dominguez
 * Test de MyStockManagerTestClean 
 */
class MyStockManagerTestClean {
	
	StockManager underTest;
	String underTestAsString;
	
	@BeforeEach
	public void setup() {
		MyStockManager.getInstance().clean();
	}
	
	/**
	 * Test para probar  {@link shopmanager.MyStockManager#clean()}
	 * 
	 */
	@Test
	@Tag("integracion")
	@DisplayName("Prueba para el método que limpia el stock")
	void testClean() {
		
		//Creamos un producto tipo "id1" con 5 unidades y un producto tipo "id2" con 3 unidades
		 Product product1 = new MyProduct("id1",5);
		 Product product2 = new MyProduct("id2",3);
		 
		 //Los añado al stock
		 MyStockManager.getInstance().addProduct(product1);
		 MyStockManager.getInstance().addProduct(product2);
		 
		 //Vaciamos el stock
		 MyStockManager.getInstance().clean();
		 
		 //Comprobamos que no existe ninguno de los productos añadidos
		 assertTrue(MyStockManager.getInstance().searchProduct("id1").isEmpty(),"Debe estar vacío");
		 assertTrue(MyStockManager.getInstance().searchProduct("id2").isEmpty(),"Debe estar vacío");
	}

}
