package org.example.service;

import org.example.service.dto.ProductDTO;
import org.example.service.dto.ProductUpdateDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    String createProduct(ProductDTO productDTO);
    String updateProduct(ProductUpdateDTO productUpdateDTO);
}
