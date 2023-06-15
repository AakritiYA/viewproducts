package com.springrest.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.demo.entities.Product;
import com.springrest.demo.entities.ProductAttribute;
import com.springrest.demo.services.ProductService;

@RestController
public class MyController {

	@Autowired
	private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProduct();
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	@PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
		Product newProduct = Product.getInstance(product.getTitle(), product.getPrice(), product.getDescription());

        for (ProductAttribute attribute : product.getAttributes()) {
        	ProductAttribute attributeReq = new ProductAttribute(attribute.getSize(), attribute.getColor());
            product.addAttribute(attributeReq);
        }
        return productService.addProduct(newProduct);
    }
	
}
