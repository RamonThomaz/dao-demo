package dao;

import db.DB;

public class DaoFactory {
    public static ISellerDao creatISellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }
}
