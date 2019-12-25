package com.bitcoin.bitcoin.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bitcoin.bitcoin.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{
	
	Optional<Order> findByRandomToken(String token);

}
