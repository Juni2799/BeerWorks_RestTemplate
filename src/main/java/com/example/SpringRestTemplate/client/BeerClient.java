package com.example.SpringRestTemplate.client;

import com.example.SpringRestTemplate.model.BeerDTO;
import com.example.SpringRestTemplate.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BeerClient {
    //Page<BeerDTO> listBeers();

    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber,
                            Integer pageSize);

    BeerDTO getBeerById(UUID beerId);

    void deleteBeer(UUID beerId);

    BeerDTO updateBeer(UUID beerId, BeerDTO beerDto);

    BeerDTO createBeer(BeerDTO beerDTO);
}