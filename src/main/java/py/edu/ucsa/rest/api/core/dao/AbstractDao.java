package py.edu.ucsa.rest.api.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDao<PK extends Serializable, T> {

	private final Class<T> persistentClass;
	protected Logger logger = null;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
		this.logger = LoggerFactory.getLogger(persistentClass);
	}

	@PersistenceContext
	EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	protected T getById(PK id) {
		return (T) entityManager.find(persistentClass, id);
	}

	protected void insertar(T entity) {
		entityManager.persist(entity);
	}

	protected void actualizar(T entity) {
		entityManager.merge(entity);
	}

	protected void eliminar(T entity) {
		entityManager.remove(entity);
	}
}
