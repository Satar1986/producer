package org.example.service;

import org.example.service.dto.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    String createProduct(ProductDTO productDTO);
}
