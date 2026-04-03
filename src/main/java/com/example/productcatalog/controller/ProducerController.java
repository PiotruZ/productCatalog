package com.example.productcatalog.controller;

import com.example.productcatalog.dto.CreateProducerRequest;
import com.example.productcatalog.dto.ProducerResponse;
import com.example.productcatalog.service.ProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producers")
@RequiredArgsConstructor
@Slf4j
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping
    public ResponseEntity<List<ProducerResponse>> getAllProducers() {
        return ResponseEntity.ok(producerService.getAllProducers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducerResponse> getProducerById(@PathVariable Long id) {
        return ResponseEntity.ok(producerService.getProducerById(id));
    }

    @PostMapping
    public ResponseEntity<ProducerResponse> createProducer(@Valid @RequestBody CreateProducerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(producerService.createProducer(request));
    }
}