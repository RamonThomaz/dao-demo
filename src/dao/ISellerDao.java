package dao;


import entities.Seller;

import java.awt.*;
import java.util.List;

public interface ISellerDao {
    void insert(Seller seller);
    void update(Seller seller);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
}
