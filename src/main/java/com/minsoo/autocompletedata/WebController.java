package com.minsoo.autocompletedata;

import com.minsoo.autocompletedata.PubSubProductGateway;
import com.minsoo.autocompletedata.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebController {

    private final PubSubProductGateway pubSubProductGateway;

    @Autowired
    @Qualifier("ProcessedProductList")
    private ArrayList<Product> processedProductList;

    public WebController(PubSubProductGateway pubSubProductGateway) {
        this.pubSubProductGateway = pubSubProductGateway;
    }

    @PostMapping("/createProduct")
    public RedirectView createUser(@RequestParam("n_product") String n_product, @RequestParam("id_sku") int id_sku, @RequestParam("n_popurality") int n_popurality) {
        System.out.println("n_product:" + n_product);
        System.out.println("id_sku:" + id_sku);
        System.out.println("n_popurality:" + n_popurality);
        Product product = new Product(n_product, id_sku, n_popurality);
        System.out.println("Product=" + product.toString());
        this.pubSubProductGateway.sendProductToPubSub(product);
        return new RedirectView("/");
    }

    @GetMapping("/listProducts")
    public List<Product> listPersons() {
        return this.processedProductList;
    }
}
