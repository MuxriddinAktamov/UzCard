package com.company.servise;

import com.company.dto.ProfileDTO;
import com.company.dto.ProfileFilterDTO;
import com.company.dto.TransactionDTO;
import com.company.dto.TransactionFilterDTO;
import com.company.entity.CardEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.TransactionEntity;
import com.company.repository.CardRepository;
import com.company.repository.TransactionRepository;
import com.company.spec.ProfileSpecfication;
import com.company.spec.TransactionSpecfication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private CardRepository cardRepository;

    public TransactionDTO makeTransaction(TransactionDTO dto) {
        CardEntity fromCard = null;
        CardEntity toCard = null;
        try {
            fromCard = cardService.get(dto.getFromCardId()); // 1213
            toCard = cardService.get(dto.getToCardId());
        } catch (RuntimeException e) {
            log.error("Error in transaction  {},error", e.getMessage());
        }
        return doTransaction(fromCard, toCard, dto.getAmount());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionDTO doTransaction(CardEntity fromCard, CardEntity toCard, Long amount) {
        Long balance = null;
        try {
            balance = cardRepository.getCardBalance(fromCard.getNumber());

        } catch (RuntimeException e) {
            log.error("Error in transaction  {},error", e.getMessage());
        }

        if (balance == null || balance < amount) {
            log.error("Balance Yetmaydi {}");
            throw new RuntimeException("Not enough balance.");
        }

        try {
            cardService.update_balance(fromCard.getNumber(), amount * -1);
            cardService.update_balance(toCard.getNumber(), amount);
        } catch (RuntimeException e) {


        }


        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setFromCard(fromCard);
        transactionEntity.setToCard(toCard);
        transactionEntity.setAmount(amount);
        transactionEntity.setCreatedDate(LocalDateTime.now());
        try {
            transactionRepository.save(transactionEntity);
        } catch (RuntimeException e) {
            log.error("Error in Adding profile {},error {}", e.getMessage());
        }
        return toDTO(transactionEntity);
    }

    @Transactional(propagation = Propagation.NEVER)
    public boolean get() {
        transactionRepository.findAll();
        return true;
    }

    public void log(TransactionDTO dto) {
        log.info("New Transaction action {} ", dto);
        log.debug("New Transaction action {} ", dto);
        log.error("New Transaction action {} ", dto);
        log.warn("New Transaction action {} ", dto);
    }

    public void log() {
        log.info("New Transaction action {} ");
        log.debug("New Transaction action {} ");
        log.error("New Transaction action {} ");
        log.warn("New Transaction action {} ");
    }

    public List<TransactionDTO> getAllTransaction() {
        List<TransactionDTO> list = new LinkedList<>();
        List<TransactionEntity> entities = null;
        try {
            entities = transactionRepository.findAll();
        } catch (RuntimeException e) {
            log.error("Error in AllTransaction  {},error {}", e.getMessage());
        }
        Iterator<TransactionEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public List<TransactionDTO> getTransactionByProfileId(Integer id) {
        List<TransactionDTO> list = new LinkedList<>();
        List<TransactionEntity> entities = null;
        try {
            String number = cardRepository.findByProfileId(id);
            entities = transactionRepository.findByFromCardAndToCard(number);
        } catch (RuntimeException e) {
            log.error("Error in getTransactionByProfileId  {},error {}", e.getMessage());
        }
        Iterator<TransactionEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public List<TransactionDTO> getTransactionCreditByProfileId(Integer id) {
        List<TransactionDTO> list = new LinkedList<>();

        List<TransactionEntity> entities = null;
        try {
            String number = cardRepository.findByProfileId(id);
            entities = transactionRepository.findByFromCardOrderByCreatedDate(number);
        } catch (RuntimeException e) {
            log.error("Error in getTransactionCreditByProfileId  {},error {}", e.getMessage());
        }
        Iterator<TransactionEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public List<TransactionDTO> getTransactionDebitByProfileId(Integer id) {
        List<TransactionDTO> list = new LinkedList<>();
        List<TransactionEntity> entities = null;
        try {
            String number = cardRepository.findByProfileId(id);
            entities = transactionRepository.findByToCardOrderByCreatedDate(number);
        } catch (RuntimeException e) {
            log.error("Error in getTransactionDebitByProfileId  {},error {}", e.getMessage());
        }
        Iterator<TransactionEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public List<TransactionDTO> getTransactionByCardId(Integer id) {
        List<TransactionDTO> list = new LinkedList<>();
        List<TransactionEntity> entities = null;
        try {
            CardEntity card = cardService.get(id);
            entities = transactionRepository.findByFromCardAndToCard(card.getNumber());
        } catch (RuntimeException e) {
            log.error("Error in getTransactionByCardId  {},error {}", e.getMessage());
        }
        Iterator<TransactionEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public List<TransactionDTO> getTransactionDebitByCardId(Integer id) {
        List<TransactionDTO> list = new LinkedList<>();

        List<TransactionEntity> entities = null;
        try {
            CardEntity card = cardService.get(id);
            entities = transactionRepository.findByToCardOrderByCreatedDate(card.getNumber());
        } catch (RuntimeException e) {
            log.error("Error in getTransactionDebitByCardId  {},error {}", e.getMessage());
        }
        Iterator<TransactionEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public List<TransactionDTO> getTransactionCreditByCardId(Integer id) {
        List<TransactionDTO> list = new LinkedList<>();
        List<TransactionEntity> entities = null;
        try {
            CardEntity card = cardService.get(id);
            entities = transactionRepository.findByFromCardOrderByCreatedDate(card.getNumber());
        } catch (RuntimeException e) {
            log.error("Error in getTransactionCreditByCardId  {},error {}", e.getMessage());
        }
        Iterator<TransactionEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public TransactionDTO toDTO(TransactionEntity entity) {
        TransactionDTO dto = new TransactionDTO();
//        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setFromCardId(entity.getFromCard().getId());
        dto.setToCardId(entity.getToCard().getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<TransactionDTO> filterSpe(int page, int size, TransactionFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Specification<TransactionEntity> spec = Specification.where(TransactionSpecfication.isNotNull("id"));
        if (filterDTO.getFromCardId() != null) {
            spec.and(TransactionSpecfication.id("fromCardId", filterDTO.getFromCardId()));
        }
        if (filterDTO.getToCardId() != null) {
            spec.and(TransactionSpecfication.id("toCardId", filterDTO.getToCardId()));
        }
        if (filterDTO.getAmount() != null) {
            spec.and(TransactionSpecfication.balance("amount", filterDTO.getAmount()));
        }
        if (filterDTO.getToDate() != null && filterDTO.getFromDate() != null) {
            spec.and(TransactionSpecfication.date(filterDTO.getFromDate(), filterDTO.getToDate()));
        }


        Page<TransactionEntity> transEntityPage = transactionRepository.findAll(spec, pageable);
        List<TransactionDTO> dtoList = transEntityPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println(transEntityPage.getTotalElements());
        return new PageImpl<>(dtoList, pageable, transEntityPage.getTotalElements());
    }
}
