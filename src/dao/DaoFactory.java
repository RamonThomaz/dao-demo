package dao;

public class DaoFactory {
    public static ISellerDao creatISellerDao(){
        return new SellerDaoJDBC();
    }
}
