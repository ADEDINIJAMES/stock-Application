package com.example.demo.controller;

import com.example.demo.dto.StockDto;
import com.example.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api")
public class StockController {
    private final StockService stockService;

    @Autowired
    public StockController (StockService stockService){
        this.stockService = stockService;
    }
@GetMapping("/stock/{id}")
    public ResponseEntity<StockDto> getStock(@PathVariable Long id){
        return ResponseEntity.ok(stockService.getStock(id));
}

@PostMapping ("/stock")
    public  ResponseEntity<String> createStock (@RequestBody StockDto stockDto){
        return ResponseEntity.ok(stockService.createStock(stockDto));
}
@PutMapping ("/stock/{id}")
    public ResponseEntity<String> updateStock (@PathVariable Long id, @RequestBody StockDto stockDto){
        return ResponseEntity.ok(stockService.updateStock(id,stockDto));
}

@GetMapping("/stocks")
    public ResponseEntity<Page<StockDto>> getAll (Pageable pageable){
return ResponseEntity.ok(stockService.getAllStocksPaged(pageable));
}
@GetMapping("/stocks-paged")
    public ResponseEntity<List<StockDto>> getListOfStock(){
        return ResponseEntity.ok(stockService.getAllStock());
}
}
