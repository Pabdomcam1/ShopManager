/**
 * 
 */
package shopManager;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import model.MyProduct;
import model.Product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import java.util.logging.*;


/**
 * Pruebas unidad para el casi POJO myProduct
 * Pueden servir para cualquier implementación de Product siempre y cuando se cambie el método setUp, que instancia el objeto concreto
 * @author Isabel Román
 *
 */
@Tag("unidad")
public class ProductTest {
	private static Logger trazador=Logger.getLogger(ProductTest.class.getName());
	static Product producto;


	/**
	 * @throws java.lang.Exception el constructor puede lanzar excepciones, no las gestiona internamente
	 * @see org.junit.jupiter.api
	 * @see org.junit.jupiter.api.BeforeAll
	 */
	@BeforeAll
	@DisplayName("Construye el objeto y verifica constructor")
	public static void setUp() throws Exception {
		producto=new MyProduct("primerId");
		
	    assertEquals(1,producto.getNumber(),"Al crear producto sin número de unidades debe tener 1");
	    assertEquals("primerId",producto.getId(),"El id del producto es el pasado en el constructor");
		
	}

	/**
	 *  Prueba el método {@link model.Product#getId()}
	 *  @see org.junit.jupiter.api.Test
	 *  @see org.junit.jupiter.api.Tag
	 *  @see org.junit.jupiter.api.DisplayName
	 */
	@Test
	@Tag("unidad")
	@DisplayName("Prueba para el método que establece el id del producto")
	public void TestSetId() {
		trazador.info("Test del setId");
		producto.setId("nuevoId");
		assertEquals("nuevoId",producto.getId(),"El setId no funciona ");
		
	}
	/**
	*  Prueba el método {@link model.Product#setNumber(int)}.
	*  
	*/
	
	@Test
	@Tag("unidad")
	@DisplayName("Prueba para el método que establece el número de unidades del producto")
	public void TestSetNumber() {
		trazador.info("Test del setNumber");
		producto.setNumber(33);
		assertEquals(33,producto.getNumber(),"El setNumber no funciona ");
	}
	
	/**
	*  Prueba el método {@link model.Product#oneMore()}.
	*  
	*/
	
	@Test
	@Tag("unidad")
	@DisplayName("Prueba para el método que añade una unidad al producto")
	public void TestOneMore() {
		trazador.info("Test del oneMore");
		producto.setNumber(33);
		
		assertEquals(34,producto.oneMore(),"El oneMore no funciona ");
	}
	
	
	/**
	*  Prueba el método {@link model.Product#oneLess()}.
	*  
	*/
	
	@Test
	@Tag("unidad")
	@DisplayName("Prueba para el método que elimina una unidad del producto")
	public void TestOneLess() {
		trazador.info("Test del OneLess");
		producto.setNumber(1);
		
		assertEquals(0,producto.oneLess(),"El oneLess no funciona cuando es distinto de 0 ");
		producto.oneLess();
		assertEquals(0,producto.oneLess(),"El oneLess no funciona cuando es 0 ");
	}
	

}
