/**
 * excepción devuelta cuando se intenta sacar del stock una cantidad que no existe
 */
package exceptions;

/**
 * @author Isabel Román
 */
public class NoEnoughStock extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int unidades;
	/**
	 * 
	 * @param unidades el número de unidades que quedan en el stock
	 */
	public NoEnoughStock(int unidades){
		this.unidades=unidades;
	}
	@Override
	public String getMessage(){
		return "No hay suficientes unidades en el Stock, sólo quedan "+unidades;
	}
}
