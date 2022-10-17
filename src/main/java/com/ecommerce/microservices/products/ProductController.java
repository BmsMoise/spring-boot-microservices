package com.ecommerce.microservices.products;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.microservices.response.Response;

@RequestMapping("/product")
@RestController
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProductDto productDto) {

        return new ResponseEntity<>(
                new Response<>(true, productService.saveProduct(productDto)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(new Response<>(true, productService.list()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getId(@PathVariable("id") Long idProduct) {
        try {
            return new ResponseEntity<>(new Response<>(true, productService.findById(idProduct)),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(false, "echec"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long idProduct, @RequestBody ProductDto productDto) {

        try {
            return new ResponseEntity<>(new Response<>(true,
                    productService.updateProduct(idProduct, productDto)), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(new Response<>(false, "Message.echec()"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long idProduct) throws IOException {

        productService.deleteProduct(idProduct);
        return new ResponseEntity<>(new Response<>(true, "success"), HttpStatus.OK);

    }

}
