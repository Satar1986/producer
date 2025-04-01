package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.service.ProductService;
import org.example.service.dto.ProductDTO;
import org.example.service.dto.ProductUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Producer accounts_requisites")
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @Operation(
            summary = "отправляет данные accounts,requisites в партицию кафки"
    )
    @PostMapping()
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
        String externalId = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(externalId);
    }
    @Operation(
            summary = "обновляет данные accounts,requisites по externalId"
    )
    @PutMapping()
    public ResponseEntity<String> updateProduct(@RequestBody ProductUpdateDTO productUpdateDTO) {
        String externalId = productService.updateProduct(productUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(externalId);
}
}

