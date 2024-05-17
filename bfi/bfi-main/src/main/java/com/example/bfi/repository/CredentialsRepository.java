package com.example.bfi.repository;

import com.example.bfi.domain.entity.Credentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Credentials entity.
 *
 */
@SuppressWarnings("unused")
@Repository
public interface CredentialsRepository extends MongoRepository<Credentials, String>{}
