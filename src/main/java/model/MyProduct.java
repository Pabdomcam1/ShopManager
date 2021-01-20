package model;

import java.io.Serializable;

/**
 * Producto es la clase que representa todos los productos de la tienda *
 * el id (string) se usará como clave del mapa hazelcast *
 */
 

import java.util.logging.*;
/**
 * @author Isabel Román
 * @version 0.0
 * Primera versión de la clase MyProduct que implementa la interfaz Product y Serializable, porque debe guardarse en un mapa hazelcast
 *
 */

public class MyProduct implements Product,Serializable{
	
	/**
	 * Obligatorio en objetos serializables, debe poder identificarse en el destino la clase exacta con la que se creó
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Objeto tipo logger para gestionar los mensajes durante la ejecución
	 */
	private static Logger trazador=Logger.getLogger(MyProduct.class.getName());
	
	/**
	 * Identificador del producto
	 */
	private String id;
	/**
	 * Número de unidades del producto
	 */
	private int number;
	
	public MyProduct(String id) {
		this.id=id;
		number=1;
	}
	
	public MyProduct(String id,int number) {
		this.id=id;
		this.number=number;
	}
	 @Override
    public void setId(String id) {
		String msg="Estableciendo id a "+id;
    	trazador.info(msg);
        this.id=id;
    }
    @Override
    public String getId() { 	
    	String msg="Devolviendo el id como "+id;
    	trazador.info(msg);
    	return this.id;
    }
    @Override
    public void setNumber(int number) {
    	this.number=number;
    }
    @Override
    public int getNumber() {
    	return number;
    }
    @Override
    public int oneMore() {
    	number++;
    	return number;
    }
    @Override
    public int oneLess(){
    	if (number!= 0){
    		number--;
    		trazador.info("El número de unidades del producto se reduce uno");
    	} else {
    		trazador.info("El número de unidades del producto no se puede reducir porque es cero");
    	}
    	String msg=toString();
    	trazador.info(msg);
    	return number;
    }
    
    @Override
    public String toString() {
    	return (number+" unidades del producto con id "+id);
    }
}
