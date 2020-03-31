package dao;

import db.DB;
import db.DbExceptions;
import entities.Department;
import entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements IDepartmentDao {

    private Connection conn = null;

    public DepartmentDaoJDBC(Connection connection){
        conn=connection;
    }

    PreparedStatement statement = null;
    ResultSet resultSet = null;


    @Override
    public void insert(Department department) {
        try {
            statement = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", statement.RETURN_GENERATED_KEYS);
            statement.setString(1, department.getName());
            int affRows = statement.executeUpdate();
            if(affRows>0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    department.setId(resultSet.getInt(1));
                }
                DB.closeResultSet(resultSet);
            }
            else {
                throw new DbExceptions("Unexpected Error: no row got inserted");
            }
        }
        catch (SQLException e){
            throw new DbExceptions(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Department department) {
        try{
            statement = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
            statement.setString(1, department.getName());
            statement.setInt(2, department.getId());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new DbExceptions(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try{
            statement = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new DbExceptions(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public Department findById(Integer id) {
        try {
            statement = conn.prepareStatement("SELECT * FROM department WHERE department.Id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Department department = setDepartment(resultSet);
                return department;
            }
            return null;
        }
        catch (SQLException e){
            throw new DbExceptions(e.getMessage());
        }
        finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(statement);
        }
    }

    @Override
    public List<Department> findAll() {
        try {
            statement = conn.prepareStatement("SELECT * FROM department");
            resultSet = statement.executeQuery();

            List<Department> departments = new ArrayList<>();
            while (resultSet.next()) {
                Department department = setDepartment(resultSet);
                departments.add(department);
            }
            return departments;
        }
        catch (SQLException e){
            throw new DbExceptions(e.getMessage());
        }
        finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(statement);
        }
    }

    private Department setDepartment(ResultSet resultSet) throws SQLException {
        return new Department(resultSet.getInt(1), resultSet.getString(2));
    }
}
