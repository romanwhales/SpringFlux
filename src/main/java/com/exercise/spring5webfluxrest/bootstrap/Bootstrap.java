package com.exercise.spring5webfluxrest.bootstrap;

import com.exercise.spring5webfluxrest.domain.Category;
import com.exercise.spring5webfluxrest.domain.Vendor;
import com.exercise.spring5webfluxrest.repositories.CategoryRepository;
import com.exercise.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count().block() == 0){
            //load data
            System.out.println("### loading Data on Bootstrap ####");

            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();
            System.out.println("Loaded Categories "+categoryRepository.count().block());

            vendorRepository.save(Vendor.builder().firstName("Joe").lastName(
                    "Buck").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Micheal").lastName("Weson").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Jessie").lastName("Waters").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Bill").lastName("Nershi").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Jimmy").lastName("Buffet").build()).block();

            System.out.println("Loaded Vendors "+vendorRepository.count().block());

        }

    }
}
