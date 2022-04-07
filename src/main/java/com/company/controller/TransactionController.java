package com.company.controller;

import com.company.dto.TransactionDTO;
import com.company.servise.TransactionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/Transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("create")
    @ApiOperation(value = "create method", notes = "Bunda Transaction create qilinadi", nickname = "NickName")
    public ResponseEntity<?> create(@RequestBody TransactionDTO dto) {
        transactionService.log(dto);
        TransactionDTO response = transactionService.makeTransaction(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllTransaction")
    @ApiOperation(value = "Get method", notes = "Bunda Barcha Transactionlar olinadi", nickname = "NickName")
    public ResponseEntity<?> getAllTransaction() {
        transactionService.log();
        List<TransactionDTO> getAllTransaction = transactionService.getAllTransaction();
        return ResponseEntity.ok(getAllTransaction);
    }

    @GetMapping("/getAllTransactionByProfileId/{ProfileId}")
    @ApiOperation(value = "Get method", notes = "Bunda Barcha Transactionlar olinadi ProfileId orqali", nickname = "NickName")
    public ResponseEntity<?> getAllTransactionByProfileId(@PathVariable("ProfileId") Integer profileId) {
        transactionService.log();
        List<TransactionDTO> getAllTransaction = transactionService.getTransactionByProfileId(profileId);
        return ResponseEntity.ok(getAllTransaction);
    }

    @GetMapping("/getAllTransactionCreditByProfileId/{ProfileId}")
    @ApiOperation(value = "Get method", notes = "Bunda Barcha TransactionCredit lar olinadi ProfileId orqali", nickname = "NickName")
    public ResponseEntity<?> getAllTransactionCreditByProfileId(@PathVariable("ProfileId") Integer profileId) {
        transactionService.log();
        List<TransactionDTO> getAllTransactionCredit = transactionService.getTransactionCreditByProfileId(profileId);
        return ResponseEntity.ok(getAllTransactionCredit);
    }

    @GetMapping("/getTransactionDebitByProfileId/{ProfileId}")
    @ApiOperation(value = "Get method", notes = "Bunda Barcha TransactionDebit lar olinadi ProfileId orqali", nickname = "NickName")
    public ResponseEntity<?> getTransactionDebitByProfileId(@PathVariable("ProfileId") Integer profileId) {
        transactionService.log();
        List<TransactionDTO> getAllTransactionCredit = transactionService.getTransactionDebitByProfileId(profileId);
        return ResponseEntity.ok(getAllTransactionCredit);
    }

    @GetMapping("/getTransactionByCardId/{cardId}")
    @ApiOperation(value = "Get method", notes = "Bunda Barcha Transaction lar olinadi cardId orqali", nickname = "NickName")
    public ResponseEntity<?> getTransactionByCardId(@PathVariable("cardId") Integer cardId) {
        transactionService.log();
        List<TransactionDTO> getAllTransactionCredit = transactionService.getTransactionByCardId(cardId);
        return ResponseEntity.ok(getAllTransactionCredit);
    }

    @GetMapping("/getTransactionDebitByCardId/{cardId}")
    @ApiOperation(value = "Get method", notes = "Bunda Barcha TransactionDebit lar olinadi CardId orqali", nickname = "NickName")
    public ResponseEntity<?> getTransactionDebitByCardId(@PathVariable("cardId") Integer cardId) {
        transactionService.log();
        List<TransactionDTO> getAllTransactionCredit = transactionService.getTransactionDebitByCardId(cardId);
        return ResponseEntity.ok(getAllTransactionCredit);
    }

    @GetMapping("/getTransactionCreditByCardId/{cardId}")
    @ApiOperation(value = "Get method", notes = "Bunda Barcha TransactionCredit lar olinadi cardId orqali", nickname = "NickName")
    public ResponseEntity<?> getTransactionCreditByCardId(@PathVariable("cardId") Integer cardId) {
        transactionService.log();
        List<TransactionDTO> getAllTransactionCredit = transactionService.getTransactionCreditByCardId(cardId);
        return ResponseEntity.ok(getAllTransactionCredit);
    }


}
