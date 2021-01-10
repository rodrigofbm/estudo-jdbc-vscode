package application;

import java.util.Arrays;

import db.DB;
import db.DbException;
import model.dao.DaoFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class App {
	public static void main(String[] args) throws Exception {
		DepartmentDAO departmentDAO = DaoFactory.createDepartmentDAO();

		try {
			System.out.println("===== TEST INSERT =====");
			Department obj = new Department(null, "Comercial");
			//departmentDAO.insert(obj);

			System.out.println(obj);

			System.out.println("===== TEST UPDATE =====");
			Department updatedObj = new Department(5, "Human Resources");
			//departmentDAO.update(updatedObj);

			System.out.println(updatedObj);

			System.out.println("===== TEST FINB BY ID =====");
			var sel = departmentDAO.findById(4);
			System.out.println(sel);

			System.out.println("===== TEST FINB BY ID =====");
			var deps = departmentDAO.findAll();
			System.out.println(Arrays.toString(deps.toArray()));

			System.out.println("===== TEST DELETE =====");
			//departmentDAO.deleteById(5);
			System.out.println("Deleted");
		} catch (DbException e) {
			e.printStackTrace();
		} finally {
			DB.closeConnection();
		}
	}
}
