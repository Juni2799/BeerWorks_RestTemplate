package com.example.SpringRestTemplate.controller;

import com.example.SpringRestTemplate.client.BeerClient;
import com.example.SpringRestTemplate.model.BeerDTO;
import com.example.SpringRestTemplate.model.BeerStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beerClient")
public class BeerClientController {
    @Autowired
    private BeerClient beerClient;

    @GetMapping("/{beerId}")
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId){
        return beerClient.getBeerById(beerId);
    }

    @GetMapping
    public Page<BeerDTO> getBeers(@RequestParam(required = false) String beerName,
                                  @RequestParam(required = false) BeerStyle beerStyle,
                                  @RequestParam(required = false) Boolean showInventory,
                                  @RequestParam(required = false) Integer pageNumber,
                                  @RequestParam(required = false) Integer pageSize){
        return beerClient.listBeers(beerName, beerStyle, showInventory, pageNumber, pageSize);
    }
}
