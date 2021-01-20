/**
 * 
 */
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
 * Clase para los tests de MyStockManager
 * @author Isabel Román
 * 
 *
 */

public class MyStockManagerTest {

	StockManager underTest;
	String underTestAsString;
	
	
		
	@BeforeEach
	public void setup() {
		MyStockManager.getInstance().clean();
	}

	/**
	 * Test para probar {@link shopmanager.MyStockManager#getInstance()}.
	 */
	@Test
	@Tag("unidad")
	@Tag("integracion")
	@DisplayName("Prueba del método que devuelve la instancia única")
	public void testGetInstance() {
		underTest=MyStockManager.getInstance();
		underTestAsString=underTest.toString();
				
		assertNotNull(underTest,"getInstance no devuelve una referencia válida");
		assertEquals(underTest,MyStockManager.getInstance(),"getInstance debe devolver siempre la misma instancia");
	}

	/**
	 * Test para probar {@link shopmanager.MyStockManager#addProduct(model.Product)}.
	 */
	@Test
	@Tag("integracion")
	@DisplayName("Prueba para el método que añade un producto")
	public void testAddProduct() {
		Product product=new MyProduct("nuevoProducto",1);
	
		MyStockManager.getInstance().addProduct(product);
	
		assertEquals(product.toString(),MyStockManager.getInstance().searchProduct("nuevoProducto").get().toString(),"El objeto obtenido debe ser igual al introducido");
		assertEquals("nuevoProducto", MyStockManager.getInstance().searchProduct("nuevoProducto").get().getId(),"El id del producto recuperado no es el buscado");
		assertEquals(1, MyStockManager.getInstance().searchProduct("nuevoProducto").get().getNumber(),"El número de unidades del producto recuperado no es el esperado");	
		
		MyStockManager.getInstance().addProduct(product);
		assertEquals(2, MyStockManager.getInstance().searchProduct("nuevoProducto").get().getNumber(),"Si añado un producto que ya estaba se añade el número de unidades");	
	}
	
	/**
	 * Test para probar {@link shopmanager.MyStockManager#searchProduct(java.lang.String)}.
	 */
	@Test
	@Tag("integracion")
	@DisplayName("Prueba para el método de búsqueda de un producto")
	public void testSearchProduct() {
		assertTrue(MyStockManager.getInstance().searchProduct("noexiste").isEmpty(),"No debe encontrar un producto que no existe");
		
		Product product=new MyProduct("nuevoProducto",1);
		MyStockManager.getInstance().addProduct(product);
		
		assertFalse(MyStockManager.getInstance().searchProduct("nuevoProducto").isEmpty(),"Optional no debe estar vacío");
		assertEquals(product.toString(),MyStockManager.getInstance().searchProduct("nuevoProducto").get().toString(),"El objeto obtenido debe ser igual al introducido");
	}
	
	/**
	 * Test para probar  {@link shopmanager.MyStockManager#lessProduct(Product)}
	 * @throws NoEnoughStock si se intenta eliminar unidades de un producto sin suficiente cantidad lanzar la excepción NoEnoughStock, el método de test lo verifica, pero no gestiona la excepción
	 * @throws NotInStock si se intenta eliminar unidades de un producto que no existe en el stock se debe lanzar NotInStock, el método de test lo verifica, pero no gestiona la excepción
	 */
	@Test
	@Tag("integracion")
	@DisplayName("Prueba para el método que reduce el número de unidades en stock")
	
	public void testLessProduct() throws NoEnoughStock, NotInStock {
		
		 //creamos un producto tipo "id1" con 5 unidades, todavía no lo introduzco en el stock
		 Product product1=new MyProduct("id1",5);
	
		//Si intento reducir unidades de un producto que no existe debe lanzar la excepción NotInStock
		 assertThrows(NotInStock.class,()->{MyStockManager.getInstance().lessProduct(product1);},"Debería lanzar una excepción de tipo NotInStock si no existe este producto");
		 
		 //Ahora sí, lo añado al stock
		 
		 MyStockManager.getInstance().addProduct(product1);
	
		 //Si se intentan eliminar más unidades de las que hay debe lanzar la excepción NoEnoughStock
		 product1.setNumber(8);
		 assertThrows(NoEnoughStock.class,()->{MyStockManager.getInstance().lessProduct(product1);},"Debería lanzar una excepción de tipo NoEnougStock si no hay suficientes unidades de este producto");
		 product1.setNumber(1);
		 //Si a 5 que había le quito 1 deberían quedar 4
		 assertEquals(4,MyStockManager.getInstance().lessProduct(product1).getNumber(),"Debería restar el número de unidades pasadas a las que había y devolver el producto actualizado"); 		
	}
	
	/**
	 *  <P>Test para probar  {@link shopmanager.MyStockManager#clone()}
	 *  
	 *  Usa expresiones <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html">Expresiones lambda en Oracle</a>
	 *  </P>
	 *  @see java.lang.reflect.Executable
	 */
	@Test
	@Tag("unidad")
	@Tag("integracion")
	@DisplayName ("Aseguro que no se puede clonar el StockManager")
	public void testClone() {
		assertThrows(CloneNotSupportedException.class,()->{((MyStockManager)MyStockManager.getInstance()).clone();},"Debe lanzar la excepción clone no soportado al intentar clonar");
		
	}
}
