package com.example.productcatalog.service;

import com.example.productcatalog.dto.CreateProductRequest;
import com.example.productcatalog.dto.ProducerResponse;
import com.example.productcatalog.dto.ProductResponse;
import com.example.productcatalog.dto.UpdateProductRequest;
import com.example.productcatalog.entity.Product;
import com.example.productcatalog.repository.ProducerRepository;
import com.example.productcatalog.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;

    public List<ProductResponse> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAllWithProducer()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        log.info("Fetching product with id {}", id);
        return productRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        log.info("Creating product: {}", request.name());

        var producer = producerRepository.findById(request.producerId())
                .orElseThrow(() -> new RuntimeException("Producer not found with id: " + request.producerId()));

        var product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setProducer(producer);
        if (request.attributes() != null) {
            product.setAttributes(request.attributes());
        }

        var saved = productRepository.save(product);
        log.info("Created product with id {}", saved.getId());
        return toResponse(saved);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        log.info("Updating product with id {}", id);

        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (request.name() != null) product.setName(request.name());
        if (request.price() != null) product.setPrice(request.price());
        if (request.attributes() != null) product.setAttributes(request.attributes());
        if (request.producerId() != null) {
            var producer = producerRepository.findById(request.producerId())
                    .orElseThrow(() -> new RuntimeException("Producer not found with id: " + request.producerId()));
            product.setProducer(producer);
        }

        var saved = productRepository.save(product);
        log.info("Updated product with id {}", saved.getId());
        return toResponse(saved);
    }

    @Transactional
    public void deleteProduct(Long id) {
        log.info("Deleting product with id {}", id);
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        log.info("Deleted product with id {}", id);
    }

    private ProductResponse toResponse(Product product) {
        var producerResponse = new ProducerResponse(
                product.getProducer().getId(),
                product.getProducer().getName(),
                product.getProducer().getCountry()
        );
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                producerResponse,
                product.getAttributes()
        );
    }
}
