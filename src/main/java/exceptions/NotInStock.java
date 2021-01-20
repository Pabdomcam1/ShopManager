/**
 * Se lanza cuando se intenta modificar la cantidad de un producto que no está en el stock previamente
 */
package exceptions;

/**
 * @author Isabel Román
 */
public class NotInStock extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	public NotInStock(String productId){
		this.id=productId;
	}
	
	@Override
	public String getMessage() {
		return "El producto con id "+id+" no existe en el Stock";
	}
	

}
