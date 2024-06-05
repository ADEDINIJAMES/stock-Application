package com.example.demo.service;

import com.example.demo.dto.StockDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockService {
    List<StockDto> getAllStock ();
    StockDto getStock (Long id);
    String createStock (StockDto stockDto);
    String updateStock (Long id, StockDto stockDto);
    Page<StockDto> getAllStocksPaged(int pageNo, int pageSize, String direction, String sortBy );
}
