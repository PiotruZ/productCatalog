package com.example.productcatalog.service;

import com.example.productcatalog.dto.CreateProducerRequest;
import com.example.productcatalog.dto.ProducerResponse;
import com.example.productcatalog.entity.Producer;
import com.example.productcatalog.repository.ProducerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {

    private final ProducerRepository producerRepository;

    public List<ProducerResponse> getAllProducers() {
        log.info("Fetching all producers");
        return producerRepository.findAll()
                .stream()
                .map(p -> new ProducerResponse(p.getId(), p.getName(), p.getCountry()))
                .toList();
    }

    public ProducerResponse getProducerById(Long id) {
        log.info("Fetching producer with id {}", id);
        return producerRepository.findById(id)
                .map(p -> new ProducerResponse(p.getId(), p.getName(), p.getCountry()))
                .orElseThrow(() -> new RuntimeException("Producer not found with id: " + id));
    }

    @Transactional
    public ProducerResponse createProducer(CreateProducerRequest request) {
        log.info("Creating producer: {}", request.name());
        if (producerRepository.existsByName(request.name())) {
            throw new RuntimeException("Producer already exists with name: " + request.name());
        }
        var producer = new Producer();
        producer.setName(request.name());
        producer.setCountry(request.country());
        var saved = producerRepository.save(producer);
        log.info("Created producer with id {}", saved.getId());
        return new ProducerResponse(saved.getId(), saved.getName(), saved.getCountry());
    }
}
