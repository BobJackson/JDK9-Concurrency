package com.wangyousong.concurrency.ch5.recipe01;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class generates a product list of a determined size.
 * Each product is initialized with a predefined name and price.
 */
public class ProductListGenerator {

    /**
     * This method generates the list of products
     *
     * @param size the size of the product list
     * @return the generated list of products
     */
    public List<Product> generate(int size) {
        List<Product> ret = new ArrayList<>();
        IntStream.range(0, size).forEach(i -> {
            Product product = new Product();
            product.setName("Product " + i);
            product.setPrice(10);
            ret.add(product);
        });
        return ret;
    }

}
