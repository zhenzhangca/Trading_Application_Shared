package ca.jrvs.apps.trading.repositoris;

import ca.jrvs.apps.trading.excptions.ResourceNotFoundException;

/**
 * Interface for generic CRUD operations on a repository for a specific type
 */
public interface CrudRepository<E, ID> {
    /**
     * Create a given entity, return saved entity.
     *
     * @param entity must not be {@literal null}
     * @return the saved entity will never be {@literal null}
     * @throws IllegalArgumentException  if entity is valid
     * @throws java.sql.SQLException     if SQL execution failed
     * @throws ResourceNotFoundException if no entity is found in DB
     */
    E save(E entity);

    /**
     * Retrieves an entity by id
     *
     * @param id must not be {@literal null}
     * @return the entity with given id or null if not found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     * @throws java.sql.SQLException    if SQL execution failed
     */
    E findById(ID id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}
     * @return {@literal true} if an entity with the given id exists, otherwise {@literal false}
     * @throws IllegalArgumentException  if {@code id} is {@literal null}
     * @throws java.sql.SQLException     if SQL execution failed
     * @throws ResourceNotFoundException if no entity is found in DB
     */
    boolean existsById(ID id);

    /**
     * Delete the entity with the given id.
     *
     * @param id must not be {@literal null}
     * @throws IllegalArgumentException  if {@code id} is {@literal null}
     * @throws java.sql.SQLException     if SQL execution failed
     * @throws ResourceNotFoundException if no entity is found in DB
     */
    void deleteById(ID id);
}