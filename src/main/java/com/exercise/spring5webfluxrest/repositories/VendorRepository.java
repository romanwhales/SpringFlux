package com.exercise.spring5webfluxrest.repositories;

import com.exercise.spring5webfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor,String> {
}
