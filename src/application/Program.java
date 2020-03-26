package application;

import dao.DaoFactory;
import dao.ISellerDao;
import entities.Department;
import entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {

        ISellerDao iSellerDao = DaoFactory.creatISellerDao();

        System.out.println("***Test #1 - findById***");
        Seller seller = iSellerDao.findById(3);
        System.out.println(seller);
    }
}
