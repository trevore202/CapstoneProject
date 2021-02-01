package com.hcl.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String productName;
    private String productDescription;
    private String productCategory;
    private String productGenre;
    private double productPrice;
    private String productImage;

    
    
    @Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Product or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Product)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Product p = (Product) o; 
          
        // Compare the data members and return accordingly  
        return p.id == this.id; 
    } 

}