package com.example.SpringRestTemplate.controller;

import com.example.SpringRestTemplate.client.BeerClient;
import com.example.SpringRestTemplate.model.BeerDTO;
import com.example.SpringRestTemplate.model.BeerStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<BeerDTO> createBeer(@RequestBody BeerDTO beerDTO){
        /* sample request body:
        {
            "price":10.99,
            "beerName":"beer1",
            "beerStyle":"IPA",
            "quantityOnHand":50,
            "upc":"123245"
        }
         */
        return ResponseEntity.ok(beerClient.createBeer(beerDTO));
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDTO> updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beerDTO){
        /* sample request body:
         {
            "beerName":"beer1-modified",
            "beerStyle":"IPA",
            "price":10.99,
            "upc":"123245"
          }
         */
        return ResponseEntity.ok(beerClient.updateBeer(beerId, beerDTO));
    }

    @DeleteMapping("/{beerId}")
    public void deleteBeer(@PathVariable("beerId") UUID beerId){
        beerClient.deleteBeer(beerId);
    }

    //TODO: try patch implementation as well

    /*
    @GetMapping
    public ResponseEntity<Page<BeerDTO>> getBeers(){
        return ResponseEntity.ok(beerClient.listBeers());
    }
    */
}
