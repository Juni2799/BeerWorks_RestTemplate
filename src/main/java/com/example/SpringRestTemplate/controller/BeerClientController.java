package com.example.SpringRestTemplate.controller;

import com.example.SpringRestTemplate.client.BeerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/beerClient")
public class BeerClientController {
    @Autowired
    private BeerClient beerClient;
}
