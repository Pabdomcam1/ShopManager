/**
 * Excepción devuelta si se intenta eliminar o modificar un producto que no está en la cesta
 */
package exceptions;

/**
 * @author Isabel Román
 *
 */
public class NotInBag extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	public NotInBag(String productId){
		this.id=productId;
	}
	
	@Override
	public String getMessage() {
		return "El producto con id "+id+" no existe en la cesta";
	}

}
