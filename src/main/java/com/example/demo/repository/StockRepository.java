package com.example.demo.repository;

import com.example.demo.model.Stock;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
     Page<Stock> findAll( Pageable pageable);
}
