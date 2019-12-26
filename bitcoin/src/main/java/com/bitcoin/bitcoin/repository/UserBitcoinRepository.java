package com.bitcoin.bitcoin.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bitcoin.bitcoin.model.Merchant;

public interface UserBitcoinRepository extends CrudRepository<Merchant, Long> {

	Optional<Merchant> findByUsername(String username);
}
