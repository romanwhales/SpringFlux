package com.exercise.spring5webfluxrest.controllers;

import com.exercise.spring5webfluxrest.domain.Vendor;
import com.exercise.spring5webfluxrest.repositories.CategoryRepository;
import com.exercise.spring5webfluxrest.repositories.VendorRepository;
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

public class VendorControllerTest {
    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;




    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);

        webTestClient =
                WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void list() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Vendor1").lastName("Lastvendor1").build(),Vendor.builder().firstName("Vendor2").lastName("LastVendor2").build()));
        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(Vendor.builder().firstName("Vendor1").lastName("Vendor2").build()));
        webTestClient.get()
                .uri("/api/v1/vendors/someid")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void testCreateVendor() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));
        Mono<Vendor> venToSaveMono = Mono.just(Vendor.builder().firstName(
                "Olawale").lastName("Adedeji").build());
        webTestClient.post().uri("/api/v1/vendors")
                .body(venToSaveMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdate() {
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdateMono =
                Mono.just(Vendor.builder().lastName("Sunmonu").firstName(
                        "Adedeji").build());

        webTestClient.put().uri("/api/v1/vendors/hheher")
                .body(vendorToUpdateMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}