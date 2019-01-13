package com.minsoo.autocompletedata.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductPubSub {

    private final int id_sku ;
    private final String n_product;
    private final int n_popurality;

    @JsonCreator
    public ProductPubSub(@JsonProperty("n_product") String n_product, @JsonProperty("id_sku") int id_sku, @JsonProperty("n_popurality") int n_popurality){
        this.id_sku = id_sku;
        this.n_product = n_product;
        this.n_popurality = n_popurality;
        System.out.println("Product.id_sku=" + this.id_sku);
        System.out.println("Product.n_product=" + this.n_product);
        System.out.println("Product.n_popurality=" + this.n_popurality);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPubSub product = (ProductPubSub) o;
        return this.id_sku == product.id_sku &&
                Objects.equals(this.n_product, product.n_product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id_sku, this.n_product, this.n_popurality);
    }

    @Override
    public String toString() {
        return "Product{" +
                "n_product='" + this.n_product + '\'' +
                ", id_sku=" + this.id_sku +
                ", n_popurality=" + this.n_popurality +
                '}';
    }

}
