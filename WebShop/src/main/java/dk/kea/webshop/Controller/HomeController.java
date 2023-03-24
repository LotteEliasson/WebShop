package dk.kea.webshop.Controller;

import dk.kea.webshop.Model.Product;
import dk.kea.webshop.Repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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

    @GetMapping("/create")
    public String showCreate(){
        return "create";
    }

    @PostMapping("/create")
    public String createproduct(@RequestParam("product-name") String newName, @RequestParam("product-price") double newPrice){
        //lave et nyt produkt
        Product newProduct = new Product();
        newProduct.setName(newName);
        newProduct.setPrice(newPrice);

        //gem nyt produkt
        productRepository.addProduct(newProduct);

        //tilbage til productlisten
        return "redirect:/";
    }
}
