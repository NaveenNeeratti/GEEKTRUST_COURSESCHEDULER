package com.geektrust.backend.repository;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T,ID> {
    /**
     * It finds all the objects of type T;
     * @return - list of objects of type T;
     */
    public List<T> findAll();
    /**
     * It finds a particular entity.
     * @param entity - entity is required to find object of type T based on entity
     * @return - return the object found based on entity.
     */
    public Optional<T> findById(ID entity);
    /**
     * It the record or object based on entity.If any object matches with the entity.
     * @param entity - It is required to delete an object or record based on entity.
     */
    public void delete(ID entity);
}
