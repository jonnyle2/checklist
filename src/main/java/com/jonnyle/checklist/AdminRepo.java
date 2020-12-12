package com.jonnyle.checklist;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminRepo extends CrudRepository<Admin, Integer>{

	UserDetails findByUsername(String username);
    
}