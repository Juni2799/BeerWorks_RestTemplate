package com.example.SpringRestTemplate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = "pageable") //used to ignore certain @JsonProperty which are not defined
public class BeerPageImpl<T> extends PageImpl<T> {
    //this is the constructor that will be used by Jackson during deserialization
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BeerPageImpl(@JsonProperty("content") List<T> content, @JsonProperty("number") int pageNumber,
                        @JsonProperty("size") int size, @JsonProperty("totalElements") Long totalElements) {
        super(content, PageRequest.of(pageNumber, size), totalElements);
    }

    public BeerPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerPageImpl(List<T> content) {
        super(content);
    }
}