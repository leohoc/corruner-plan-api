package com.corruner.plan.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plans")
@Tag(name = "Plans", description = "Plans module endpoints")
public class HelloController {

    @GetMapping("/hello")
    @Operation(summary = "Hello World", description = "Returns a hello world message from the Plans module")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from CoRunner Plans API!");
    }
}