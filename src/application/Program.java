package application;

import dao.DaoFactory;
import dao.ISellerDao;
import entities.Department;
import entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {

        ISellerDao iSellerDao = DaoFactory.creatISellerDao();

        System.out.println("***Test #1 - findById***");
        Seller seller = iSellerDao.findById(3);
        System.out.println(seller);

        System.out.println("***Test #2 - findByDepartment***");
        Department department = new Department(2, null);
        List<Seller> list = iSellerDao.findByDepartment(department);
        for (Seller s:list) {
            System.out.println(s);

        }
    }
}
