package dk.kea.webshop.Controller;

import dk.kea.webshop.Model.Product;
import dk.kea.webshop.Repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        //tilbage til produktlisten
        return "redirect:/";
    }

    //vis updateside for produkt ud fra parameter i URL
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") int updateId, Model model) {
        //Find produkt med id lig update id i databasen
        Product updateProduct = productRepository.findProductById(updateId);

        //TIlføj produkt til viewmodel, så det kan bruges o thymeleaf
        model.addAttribute("product", updateProduct);

        //Fortæl Spring hvilken HTML-side, der skal vises.
        return "update";
    }

    @PostMapping("/update")
    public String updateProduct(@RequestParam("product-name") String updateName,
                                @RequestParam("product-price") double updatePrice,
                                @RequestParam("product-id") int updateId){

        //LAve produkt ud fra parameter
        Product updateProduct = new Product(updateId, updateName, updatePrice);

        //Kald opdater i Repository
        productRepository.updateProduct(updateProduct)
        ;
        //Rediriger til index
        return "redirect:/";
    }

}
