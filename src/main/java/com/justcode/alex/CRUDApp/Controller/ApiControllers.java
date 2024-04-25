package com.justcode.alex.CRUDApp.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        return "market_index";
    }

    /** getPrice grabs the price of the request symbol on a given market
     * @param symbol the symbol to search for 
     * @param index the market where the symbol exists
     * @return the last price of the symbol (per https://www.google.com/finance/quote/${symbol}:${index})
     * @throws IOException 
     */
    @ResponseBody @GetMapping("/market_lookup")
    public String getPrice(@RequestParam("search_symbol") String symbol, @RequestParam("market_index") String index) throws IOException {
        
        // Uppercase the market and symbol
        symbol = symbol.toUpperCase();
        index = index.toUpperCase();

        // Sanity check the input
        System.out.println("Looking for SYMBOL " + symbol);
        System.out.println("... on the " + index + " market.");
        System.out.println("Trying to get market price from https://www.google.com/finance/quote/"+symbol+":"+index);

        // Setup browsing options to run chromium/chrome in a docker container
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        // Spin up the web driver and get the html
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.google.com/finance/quote/"+symbol+":"+index);

        // Parse the html, looking for the 'data-last-price' attribute
        // This is the attribute that holds google's last known price of the symbol:index
        Document doc = Jsoup.parse(driver.getPageSource());
        Element div = doc.select("div[data-last-price]").first();
        String price = div.attr("data-last-price");

        return symbol + " : " + index + " = " + price;
    }

    /** userSearch(String search_string)
     * @param search_string the string to crawl the web searching for
     * @return ... the same string at the moment, happy to be getting
     *             information from the html/javascript page
     * @throws IOException 
     */
    @ResponseBody @PostMapping("/search")
    public String userSearch(@RequestBody String search_string) throws IOException {
        
        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        return "Hello, you searched for " + search_string.split("=")[1];
    }

    /**
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
