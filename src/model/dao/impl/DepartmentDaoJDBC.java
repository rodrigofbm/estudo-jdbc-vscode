package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDAO {
  Connection conn;

  public DepartmentDaoJDBC(Connection conn) {
    this.conn = conn;
  }

  @Override
  public void insert(Department obj) {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      ps = this.conn.prepareStatement(
        "INSERT INTO department(Name) VALUES(?)",
        Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, obj.getName());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        rs = ps.getGeneratedKeys();
        if(rs.next()) {
          int id = rs.getInt(1);
          obj.setId(id);
        }
      } else {
        throw new DbException("Unexptected error. No rows affected");
      }
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(ps);
      DB.closeResultSet(rs);
    }
    
  }

  @Override
  public void update(Department obj) {
    PreparedStatement ps = null;

    try {
      ps = this.conn.prepareStatement(
        "UPDATE department SET Name = ?"
        + "WHERE Id = ?");

      ps.setString(1, obj.getName());
      ps.setInt(2, obj.getId());

      int rowsAffected = ps.executeUpdate();

      if(rowsAffected == 0) {
        throw new DbException("Unexpcted error. No rows affected.");
      }
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(ps);
    }
  }

  @Override
  public void deleteById(int id) {
    PreparedStatement ps = null;
    try {
      ps = this.conn.prepareStatement("DELETE FROM department WHERE Id = ?");

      ps.setInt(1, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(ps);
    }
  }

  @Override
  public Department findById(int id) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Department dep = null;

    try {
      ps = this.conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
      ps.setInt(1, id);

      rs = ps.executeQuery();

      if(rs.next()) {
        dep = instantiateDepartment(rs);
      }
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(ps);
      DB.closeResultSet(rs);
    }

    return dep;
  }

  @Override
  public List<Department> findAll() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Department> deps = new ArrayList<>();
    
    try {
      ps = this.conn.prepareStatement("SELECT * FROM department");

      rs = ps.executeQuery();

      while(rs.next()) {
        Department dep = instantiateDepartment(rs);
        deps.add(dep);
      }
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(ps);
      DB.closeResultSet(rs);
    }

    return deps;
  }
  
  private Department instantiateDepartment(ResultSet rs) throws SQLException {
    return new Department(rs.getInt("Id"), rs.getString("Name"));
  }

}
