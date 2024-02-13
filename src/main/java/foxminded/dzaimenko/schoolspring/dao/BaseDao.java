package foxminded.dzaimenko.schoolspring.dao;

import java.util.List;

public interface BaseDao<T> {

    void create(T entity);

    List<T> getAll();

    void update(T entity);

    void deleteById(int Id);

}