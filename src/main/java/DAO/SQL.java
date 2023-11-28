

package DAO;

import java.util.List;


/**
 * Interface for basic CURD operation
 * @author catty
 * @param <E>
 * @param <K>
 */

    public abstract class SQL<E, K> {

//    abstract public void insert(E entity);
//    abstract public void update(E entity);
//    abstract public void delete(K id);
    abstract public List<E> selectAll();
//    abstract public E selectByID(K id);
    abstract protected List<E> selectBySQL(String sql, Object...args);

    
}

