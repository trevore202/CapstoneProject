package com.hcl.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcl.model.User;

@Repository
public interface UserDao extends CrudRepository<User, String>{

}
