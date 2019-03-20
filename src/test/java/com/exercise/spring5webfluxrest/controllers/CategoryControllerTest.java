package com.exercise.spring5webfluxrest.controllers;

import com.exercise.spring5webfluxrest.domain.Category;
import com.exercise.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;


    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient =
                WebTestClient.bindToController(categoryController).build();

    }

    @Test
    public void list(){
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat 1").build(),Category.builder().description("Cat 2").build()));
        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }
    @Test
    public void getById(){
        BDDMockito.given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(Category.builder().description("Cat").build()));
        webTestClient.get()
                .uri("/api/v1/categories/someid")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void testCreateCategory(){
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> catToSaveMono =
                Mono.just(Category.builder().description("Cat").build());
        webTestClient.post().uri("/api/v1/categories")
                .body(catToSaveMono,Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdate() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono =
                Mono.just(Category.builder().description("Some Cat").build());

        webTestClient.put().uri("/api/v1/categories/gfhfhfg")
                .body(catToUpdateMono,Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testPatch() {

        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono =
                Mono.just(Category.builder().description("Some Cat").build());

        webTestClient.patch().uri("/api/v1/categories/gfhfhfg")
                .body(catToUpdateMono,Category.class)
                .exchange()
                .expectStatus()
                .isOk();
        BDDMockito.verify(categoryRepository).save(any());
    }
}