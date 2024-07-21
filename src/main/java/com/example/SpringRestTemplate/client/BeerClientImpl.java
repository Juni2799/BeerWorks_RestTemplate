package com.example.SpringRestTemplate.client;

import com.example.SpringRestTemplate.model.BeerDTO;
import com.example.SpringRestTemplate.model.BeerPageImpl;
import com.example.SpringRestTemplate.model.BeerStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

public class BeerClientImpl implements BeerClient{
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${beer.works.base.url}")
    private String BASE_URL;
    @Value("${beer.works.get.beer.path}")
    private String GET_BEER_PATH;
    @Value("${beer.works.get.beer.by.id.path}")
    private String GET_BEER_BY_ID_PATH;

    //For working with Query Parameters, like /api?param1=val1&param2=val2
    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);

        if(StringUtils.hasText(beerName)){
            uriComponentsBuilder.queryParam("beerName", beerName);
        }

        if(beerStyle != null){
            uriComponentsBuilder.queryParam("beerStyle", beerStyle);
        }

        if(showInventory != null){
            uriComponentsBuilder.queryParam("showInventory", showInventory);
        }

        if(pageNumber != null){
            uriComponentsBuilder.queryParam("pageNumber", pageNumber);
        }

        if(pageSize != null){
            uriComponentsBuilder.queryParam("pageSize", pageSize);
        }

        ResponseEntity<BeerPageImpl> response = restTemplate.getForEntity(BASE_URL+uriComponentsBuilder.toUriString(), BeerPageImpl.class);
        return response.getBody();
    }

    //For working with URL parameters, like /api/{beerId}
    @Override
    public BeerDTO getBeerById(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(BASE_URL + GET_BEER_BY_ID_PATH, BeerDTO.class, beerId);
    }

    /*
    @Override
    public Page<BeerDTO> listBeers() {
        RestTemplate restTemplate = restTemplateBuilder.build();

    Various ways to return RestTemplate response and how to print their results
        ResponseEntity<String> stringResponse = restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, String.class);
    System.out.println(stringResponse.getBody());

        ResponseEntity<Map> mapResponse = restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, Map.class);
    System.out.println(mapResponse.getBody());

        ResponseEntity<JsonNode> jsonResponse =
                restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, JsonNode.class);

        jsonResponse.getBody().findPath("content")
                .elements().forEachRemaining(node -> {
                    System.out.println(node.get("beerName").asText());
                });

    For returning a Page<> response, we need to provide jackson a custom class which will be used for deserialization from Json to Page<>
    The custom class will extend PageImpl<T> class and provide a constructor which is annotated with @JsonCreator, check BeerPageImpl class
        ResponseEntity<BeerPageImpl> beerPageResponse = restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, BeerPageImpl.class);

        return beerPageResponse.getBody();
    }
    */

    @Override
    public void deleteBeer(UUID beerId) {

    }

    @Override
    public BeerDTO updateBeer(UUID beerId, BeerDTO beerDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.put(BASE_URL+GET_BEER_BY_ID_PATH, beerDto, beerId);
        return this.getBeerById(beerId);
    }

    @Override
    public BeerDTO createBeer(BeerDTO beerDTO) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<BeerDTO> response = restTemplate.postForEntity(BASE_URL+GET_BEER_PATH, beerDTO, BeerDTO.class);

        String responseBeerByIdUrl = response.getHeaders().get("Location-By-Id").get(0);
        return restTemplate.getForObject(BASE_URL + responseBeerByIdUrl, BeerDTO.class);
    }
}
