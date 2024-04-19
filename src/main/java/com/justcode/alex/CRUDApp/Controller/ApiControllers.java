package com.justcode.alex.CRUDApp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.justcode.alex.CRUDApp.Models.User;
import com.justcode.alex.CRUDApp.Repo.UserRepo;

/* 
 * ApiControllers is a class to outline how the CRUD routines work
 * for my API.
 * CRUD stands for Create(POST), Read(GET), Update(PUT), and Delete(DELETE)
 */
@RestController
public class ApiControllers {
    
    @Autowired
    private UserRepo userRepo;
    
    @GetMapping("/")
    public String getPage() {
        return "Welcome to my CRUD API";
    }

    // C... is for create
    @PostMapping("/create")
    public String createUser(@RequestBody User user) {
        userRepo.save(user);
        return "Saved " + user.getName();
    }

    // R... is for read
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    // U... is for update
    @PutMapping("update/{id}")
    public String updateUser(@PathVariable long id, @RequestBody User user) {
        User upUser = userRepo.findById(id).get();
        upUser.setName(user.getName());
        userRepo.save(upUser);
        return "Updated user " + upUser.getName();
    }

    // D... is for delete
    @DeleteMapping("delete/{id}")
    public String deleteUser(@PathVariable long id) {
        User delUser = userRepo.findById(id).get();
        userRepo.delete(delUser);
        return "Deleted user " + delUser.getName() + ", id " + id;
    }
}
