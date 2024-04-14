package dao;

import java.io.Serializable;
import java.util.List;

public interface DAO<T> {
	List<T> findALL();
	T findByid(Serializable id);
	boolean delete(Serializable id);
	boolean add(T t);
	boolean update(T t);
}
