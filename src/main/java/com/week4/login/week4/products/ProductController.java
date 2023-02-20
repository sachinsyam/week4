package com.week4.login.week4.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/products")
    private String listProducts(Model model){
        model.addAttribute("products", productService.listAll());
    return "products";
    }
}
