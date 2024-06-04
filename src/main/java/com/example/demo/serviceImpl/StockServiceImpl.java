package com.example.demo.serviceImpl;

import com.example.demo.dto.StockDto;
import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {
    private StockRepository stockRepository;

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

    public Page<StockDto> getAllStocksPaged(Pageable pageable) {
        List<Stock> stockList = stockRepository.findAll();
        List<StockDto> stockDtoList = new ArrayList<>();
        for (Stock stock : stockList){
            StockDto stockDto = convertToDto(stock);
            stockDtoList.add(stockDto);
        }
        return new PageImpl<>(stockDtoList);
    }

    @Override
    public StockDto getStock(Long id) {
       Stock stock = stockRepository.findById(id).orElseThrow(()-> new RuntimeException("stock with not available"));
        return convertToDto(stock);
    }

    @Override
    public String createStock(StockDto stockDto) {
        stockDto.setUpdateTime(LocalDate.now());
        stockRepository.save(convertFromStockDto(stockDto));
        return "stock created successfully";
    }

    @Override
    public String updateStock(Long id, StockDto stockDto) {
        Stock stock = stockRepository.findById(id).orElseThrow(()-> new RuntimeException("stock not found"));
        if(stockDto!= null) {
           if(stockDto.getAmount()!= null) {
               stock.setAmount(stockDto.getAmount());
           }
            if(stockDto.getName()!=null) {
                stock.setName(stockDto.getName());
            }
            stockDto.setUpdateTime(LocalDate.now());
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
