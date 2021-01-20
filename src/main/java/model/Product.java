package model;
/**
 * 
 * @author Isabel Román
 * @version 0.0. 
 * Primera versión de la interfaz Product, los elementos que vende nuestra tienda
 *
 */

public interface Product{
	
	    /**
	     * @param id identificador que se le quiere poner al producto
	     */
	    public void setId(String id);
	    /**
	     * 
	     * @return devuelve el identificador del producto
	     */
	    public String getId();
	    /**
	     * 
	     * @param number número de unidades del producto
	     */
	    public void setNumber(int number);
	    /**
	     * 
	     * @return devuelve el número de unidades del producto
	     */
	    public int getNumber();
	    /**
	     * Suma uno
	     * @return int new value
	     */
	    public int oneMore();
	    /**
	     * Resta 1
	     * @return int new value
	     */
	    public int oneLess();
	    }