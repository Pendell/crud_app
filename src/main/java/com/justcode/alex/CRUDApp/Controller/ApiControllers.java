package com.justcode.alex.CRUDApp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.justcode.alex.CRUDApp.Models.User;
import com.justcode.alex.CRUDApp.Repo.UserRepo;

/* 
 * ApiControllers is a class to outline how the CRUD and other routines work
 * for my API
 * CRUD stands for Create(POST), Read(GET), Update(PUT), and Delete(DELETE)
 */
@Controller
public class ApiControllers {
    
    @Autowired
    private UserRepo userRepo;
    
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /** userSearch(String search_string)
     * @param search_string the string to crawl the web searching for
     * @return ... the same string at the moment, happy to be getting
     *             information from the html/javascript page
     */
    @ResponseBody @PostMapping("/search")
    public String userSearch(@RequestBody String search_string) {
        
        return "Hello, you searched for " + search_string.split("=")[1];
    }

    /*
     * The functions below are the basic CRUD functions. Currently they're
     * not really important, as this is shaping up to be a search engine.
     * HOWEVER, it would be good to save search_string's (from the func above)
     * against their results, and the time the search took place. It might
     * be wise to check results against the database if a repeat search is
     * made faster than necessary.
     * 
     * These functions can still be requested against their current mappings
     */

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
