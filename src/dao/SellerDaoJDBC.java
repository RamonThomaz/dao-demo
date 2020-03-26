package dao;

import db.DB;
import db.DbExceptions;
import entities.Department;
import entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements ISellerDao {

    private Connection conn = null;

    public SellerDaoJDBC(Connection connection){
        conn=connection;
    }

    PreparedStatement statement = null;
    ResultSet resultSet = null;

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        try {
            statement = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "WHERE seller.Id = ?");

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Department department = setDepartment(resultSet);
                Seller seller = setSeller(resultSet, department);
                return seller;
            }
        }
        catch (SQLException e){
            throw new DbExceptions(e.getMessage());
        }
        finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(statement);
        }
        return null;
    }

    private Seller setSeller(ResultSet resultSet, Department department) throws SQLException {
        return new Seller(resultSet.getInt("Id"), resultSet.getString("Name"), resultSet.getString("Email"), resultSet.getDate("BirthDate"), resultSet.getDouble("BaseSalary"), department);
    }

    private Department setDepartment(ResultSet resultSet) throws SQLException {
        return new Department(resultSet.getInt("DepartmentId"), resultSet.getString("DepName"));
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
