package foxminded.dzaimenko.schoolspring.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    Integer create(T entity);

    List<T> getAll();

    void update(T entity);

    void deleteById(int id);

    Optional<T> findById(int id);

}