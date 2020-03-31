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

public class SellerDaoJDBC implements ISellerDao {

    private Connection conn = null;

    public SellerDaoJDBC(Connection connection){
        conn=connection;
    }

    PreparedStatement statement = null;
    ResultSet resultSet = null;

    @Override
    public void insert(Seller seller) {
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement("INSERT INTO seller\n" +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId)\n" +
                    "VALUES\n" +
                    "(?, ?, ?, ?, ?)", statement.RETURN_GENERATED_KEYS);
            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            statement.setDouble(4, seller.getBaseSalary());
            statement.setInt(5, seller.getDepartment().getId());

            int affRows = statement.executeUpdate();
            if(affRows>0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    seller.setId(resultSet.getInt(1));
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
    public void update(Seller seller) {
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement("UPDATE seller\n" +
                    "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\n" +
                    "WHERE Id = ?");
            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            statement.setDouble(4, seller.getBaseSalary());
            statement.setInt(5, seller.getDepartment().getId());
            statement.setInt(6, seller.getId());

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
    public List<Seller> findByDepartment(Department department) {
        try {
            statement = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "WHERE DepartmentId = ?\n" +
                    "ORDER BY Name");

            statement.setInt(1, department.getId());
            resultSet = statement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (resultSet.next()) {
                Department test = map.get(resultSet.getInt("DepartmentId"));

                if(test==null){
                    test = setDepartment(resultSet);
                    map.put(test.getId(), test);
                }

                Seller seller = setSeller(resultSet, test);
                sellers.add(seller);
            }
            return sellers;
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
    public List<Seller> findAll() {
        try {
            statement = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "ORDER BY Name");

            resultSet = statement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (resultSet.next()) {
                Department test = map.get(resultSet.getInt("DepartmentId"));

                if(test==null){
                    test = setDepartment(resultSet);
                    map.put(test.getId(), test);
                }

                Seller seller = setSeller(resultSet, test);
                sellers.add(seller);
            }
            return sellers;
        }
        catch (SQLException e){
            throw new DbExceptions(e.getMessage());
        }
        finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(statement);
        }
    }

    private Seller setSeller(ResultSet resultSet, Department department) throws SQLException {
        return new Seller(resultSet.getInt("Id"), resultSet.getString("Name"), resultSet.getString("Email"), resultSet.getDate("BirthDate"), resultSet.getDouble("BaseSalary"), department);
    }

    private Department setDepartment(ResultSet resultSet) throws SQLException {
        return new Department(resultSet.getInt("DepartmentId"), resultSet.getString("DepName"));
    }
}
