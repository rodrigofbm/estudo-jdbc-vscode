package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDAO {
	void insert(Department obj);
	void update(Department obj);
	void deleteById(int id);
	Department findById(int id);
	List<Department> findAll();
}
