/**
 * Esta clase están "inspirada" en https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
 * Interfaz para manejar la persistencia de entidades de tipo T identificadas con un identificdor de tipo ID
 */
package persistency;
import java.util.Optional;
/**
 * @author Isabel Román
 *
 */
public interface CrudRepository <T,ID>{
	/**
	 * 
	 * Elimina la entidad pasada como parámetro
	 * @param entity la entidad que se quiere eliminar
	 * @throws IllegalArgumentException si la entidad es nula
	 */
	void delete(T entity)throws IllegalArgumentException;
	/**
	 * Devuelve el número de entidades manejadas por este repositorio
	 * @return número de entidades
	 */
	long count();
	/**
	 * Elimina las entidades que se pasan, de clase T o alguna subclase, de la base de datos, el argumento no puede ser nulo ni contener elementos nulos
	 * @param entities un iterador con el conjunto de entidades a borrar, de tipo T o alguna subclase
	 * @throws IllegalArgumentException si el argumento, o alguna de sus entidades, es nulo
	 */
	void deleteAll(Iterable<? extends T> entities)throws IllegalArgumentException;
	/**
	 * Elimina todas las entidades que maneja este repositorio
	 */
	void deleteAll();
	/**
	 * 
	 * Borra la entidad que se corresponde al identificador pasado como parámetro
	 * @param id Identificador de la entidad de tipo T a borrar
	 * @throws IllegalArgumentException si el id es nulo
	 */
	void deleteById(ID id)throws IllegalArgumentException;
	/**
	 * 
	 * Verfica si existe una entidad con el id indicado en el parámetro
	 * @param id verifica si existe la entidad con identificador id
	 * @return true si existe /false si no existe
	 * @throws IllegalArgumentException si el id es nulo
	 */
	boolean existsByID(ID id) throws IllegalArgumentException;
	/**
	 * Devuelve todas las entidades manejadas por este repositorio
	 * @return devuelve un iterador con todos las entidades de tipo T de la base de datos
	 */
	Iterable<T> findAll();
	/**
	 * 
	 * Devuelve el conjunto de entidades manejadas por este repositorio que corresponden al conjunto de identificadores pasados como parámetros
	 * @param id identificador de las entidades buscadas
	 * @return devbuelve un iterador con todas las entidades guardadas que tengan ese identificador
	 * @throws IllegalArgumentException si alguno de los id es nulo (o el iterador)
	 */
	Iterable<T> findAllById(Iterable<ID> id) throws IllegalArgumentException;
	
	/**
	 * 
	 * Devuelve la entidad con el identificador pasado como parámetro
	 * @param id identificador de la entidad buscada
	 * @return devuelve un objeto tipo Optional. Si Optional.isPresent()=true Optional.get() devuelve la entidad
	 * @throws IllegalArgumentException si el id es nulo
	 */
	Optional<T> findById(ID id) throws IllegalArgumentException;
	/**
	 * 
	 * Persiste la entidad que se pasa como parámetro
	 * @param entity entidad a guardar
	 * @param <S> la entidad puede ser de clase T o cualquier subclase
	 * @return devuelve la entidad guardada, porque al guardar puede haber hecho cambios
	 * @throws IllegalArgumentException si alguna de las entidades es null
	 */
	<S extends T> S save(S entity) throws IllegalArgumentException;
    /**
     * 
     * Persiste las entidades que se pasan como parámetro, en un iterador
     * @param <S> los elementos del iterador deben ser de tipo T o alguna subclase
	 * @param entities Iterador de las entidades a guardar (podrá ser de clase T o cualquier subclase)
	 * @return devuelve el iterador de las entidades guardadas, porque al guardar puede haber hecho cambios
	 * @throws IllegalArgumentException si alguna de las entidades es null
	 */
 
    <S extends T> Iterable<S> saveAll(Iterable<S> entities) throws IllegalArgumentException;
}
