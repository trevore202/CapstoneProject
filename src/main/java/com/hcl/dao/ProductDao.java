package com.hcl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcl.model.Product;


@Repository
public interface ProductDao extends CrudRepository<Product, Integer> {

	@Query
	("SELECT pr FROM Product pr WHERE LOWER(pr.productName) LIKE %?1%"
            + " OR LOWER(pr.productDescription) LIKE %?1%")
    public List<Product> searchProducts(String keyword);
}
