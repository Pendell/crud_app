package com.justcode.alex.CRUDApp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.justcode.alex.CRUDApp.Models.User;

public interface UserRepo extends JpaRepository<User, Long>{
    
}
