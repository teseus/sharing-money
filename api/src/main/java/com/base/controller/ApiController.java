package com.base.controller;

import com.base.dto.GreetingDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/v1/greet/{name}")
    public Mono<ResponseEntity<GreetingDTO>> greeting(@PathVariable String name){
        return Mono.just(ResponseEntity.ok(GreetingDTO.ofName(name)));
    }
}
