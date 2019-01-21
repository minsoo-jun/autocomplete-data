package com.minsoo.autocompletedata;

import com.minsoo.autocompletedata.domain.ProductPubSub;
import com.minsoo.autocompletedata.service.EsUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebController {

    private final PubSubProductGateway pubSubProductGateway;

    @Autowired
    private EsUpdateService esUpdateService;

    @Autowired
    @Qualifier("ProcessedProductList")
    private ArrayList<ProductPubSub> processedProductList;

    public WebController(PubSubProductGateway pubSubProductGateway) {
        this.pubSubProductGateway = pubSubProductGateway;
    }

    @PostMapping("/createProduct")
    public RedirectView createUser(@RequestParam("n_product") String n_product, @RequestParam("id_sku") int id_sku, @RequestParam("n_popurality") int n_popurality) {
        System.out.println("n_product:" + n_product);
        System.out.println("id_sku:" + id_sku);
        System.out.println("n_popurality:" + n_popurality);
        ProductPubSub product = new ProductPubSub(n_product, id_sku, n_popurality);
        System.out.println("Product=" + product.toString());
        this.pubSubProductGateway.sendProductToPubSub(product);
        return new RedirectView("/ecdata");
    }

    @GetMapping("/listProducts")
    public List<ProductPubSub> listPersons() {
        return this.processedProductList;
    }

    @PostMapping("/datasync")
    public String  dataSyncForWord(@RequestParam("n_product") String n_product, @RequestParam("id_sku") int id_sku, @RequestParam("n_popurality") int n_popurality){
        ProductPubSub product = new ProductPubSub(n_product, id_sku, n_popurality);
        System.out.println("n_product:" + n_product);
        System.out.println("id_sku:" + id_sku);
        System.out.println("n_popurality:" + n_popurality);
        esUpdateService.syncDoucmet(product);
        return "done";
    }

    @GetMapping("/langApiTest")
    public String langApiTest(@RequestParam("keyword") String keyword){
        System.out.println("GOOGLE_APPLICATION_CREDENTIALS -> " + System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
        System.out.println("JAVA_HOME -> " + System.getenv("JAVA_HOME"));
        return esUpdateService.langApiTestCall(keyword);
    }
}
