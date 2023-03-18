package dk.kea.webshop.Controller;

import dk.kea.webshop.Repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController
{
    ProductRepository productRepository;
    public HomeController(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("products",productRepository.getAll());
        return "index";
    }
}
