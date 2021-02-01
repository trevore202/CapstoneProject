package com.hcl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.dao.ProductDao;
import com.hcl.model.Product;

@Service
public class ProductService {
	
	@Autowired
	ProductDao productDao;
	

    public List<Product> retrieveProducts() {
    	List<Product> allProducts = (List<Product>) productDao.findAll();
    	return allProducts;
    }
    
    public Product retrieveProduct(int id) {
    	Product t = productDao.findById(id).get();
    	return t;
    }

    public void updateProduct(Product product){
    	productDao.deleteById(product.getId());
    	productDao.save(product);
    }

    public void addProduct(Product product) {
        try {
    		productDao.save(product);
        }catch(Exception e) {
        }
    }

    public void deleteProduct(int id) {
    	productDao.deleteById(id);
        
    }
    
    public List<Product> searchProducts(String search){
    	List<Product> searchProducts = (List<Product>) productDao.searchProducts(search);
    	return searchProducts;
    }
}