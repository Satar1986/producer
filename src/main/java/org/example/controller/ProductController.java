package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.ProductService;
import org.example.service.dto.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class ProductController {
    private final ProductService productService;
    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
        String externalId = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(externalId);
    }

}

