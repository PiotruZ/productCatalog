package com.example.productcatalog.service;

import com.example.productcatalog.dto.ProducerResponse;
import com.example.productcatalog.repository.ProducerRepository;
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
}
