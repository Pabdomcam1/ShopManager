/**
 * 
 */
package model;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Isabel Román
 */
public class MyOrder implements Order {

	/**
	 * 
	 */
	public MyOrder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProducts(Iterator<Product> products) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setProducts(Collection<Product> products) {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<Product> getProductsById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Product> getProductsByPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Product> getProductsByUnits() {
		// TODO Auto-generated method stub
		return null;
	}

}
