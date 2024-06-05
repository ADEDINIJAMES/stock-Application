package com.example.demo.serviceImpl;

import com.example.demo.dto.StockDto;
import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl (StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }
    @Override
    public List<StockDto> getAllStock() {
        List<Stock> stockList = stockRepository.findAll();
        List<StockDto> stockDtoList = new ArrayList<>();
        for (Stock stock : stockList){
            StockDto stockDto = convertToDto(stock);
            stockDtoList.add(stockDto);
        }
        return stockDtoList;
    }

    public Page<StockDto> getAllStocksPaged(int pageNo, int pageSize, String direction, String sortBy ) {

        Sort sort = direction.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
        Page<Stock> stockPaged = stockRepository.findAll(pageable);
        List<StockDto> stockDtoList = new ArrayList<>();
        for (Stock stock : stockPaged){
            StockDto stockDto = convertToDto(stock);
            stockDtoList.add(stockDto);
        }
        return new PageImpl<>(stockDtoList,pageable,stockDtoList.size());
    }

    @Override
    public StockDto getStock(Long id) {
       Stock stock = stockRepository.findById(id).orElseThrow(()-> new RuntimeException("stock with not available"));
        return convertToDto(stock);
    }

    @Override
    public String createStock(StockDto stockDto) {
//        stockDto.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        LocalDateTime localDateTime = LocalDateTime.now();
        stockDto.setUpdateTime(Timestamp.valueOf(localDateTime));
        Stock stock = convertFromStockDto(stockDto);
        stock.setCreateTime(LocalDateTime.now());
        stockRepository.save(stock);
        return "stock created successfully";
    }

    @Override
    public String updateStock(Long id, StockDto stockDto) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Stock stock = stockRepository.findById(id).orElseThrow(()-> new RuntimeException("stock not found"));
        if(stockDto!= null) {
           if(stockDto.getAmount()!= null) {
               stock.setAmount(stockDto.getAmount());
           }
            if(stockDto.getName()!=null) {
                stock.setName(stockDto.getName());
            }
            stock.setUpdateTime(timestamp);
            stockRepository.save(stock);
            return "stock updated successfully";
        }
        return "stock up to date";
    }

    private StockDto convertToDto (Stock stock){
        return StockDto.builder()
                .name(stock.getName())
                .createTime(stock.getCreateTime())
                .updateTime(stock.getUpdateTime())
                .amount(stock.getAmount())
                .build();
    }

    private Stock convertFromStockDto (StockDto stockDto){
//        return Stock.builder()
//                .name(stockDto.getName())
//                .amount(stockDto.getAmount())
//                .build();
        Stock stock = new Stock();
        stock.setName(stockDto.getName());
        stock.setAmount(stockDto.getAmount());
        stock.setUpdateTime(stockDto.getUpdateTime());
        return stock;
    }
}
