package com.company.servise;

import com.company.dto.CardDTO;
import com.company.dto.CardFilterDTO;
import com.company.entity.BaseEntity;
import com.company.entity.CardEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.CardRepository;
import com.company.spec.CardSpecfication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public CardDTO create(CardDTO dto) {

        CardEntity cardEntity = new CardEntity();
        cardEntity.setNumber(dto.getNumber());
        cardEntity.setExcDate(dto.getExcDate());
        cardEntity.setProfileId(dto.getProfileId());
        cardEntity.setBalance(0l);
        cardEntity.setCreatedDate(LocalDateTime.now());
        try {
            cardRepository.save(cardEntity);
        } catch (RuntimeException e) {
            log.error("Error in adding card {},error {}", dto, e.getMessage());
        }
        dto.setBalance(cardEntity.getBalance());
        dto.setCreatedDate(cardEntity.getCreatedDate());
        dto.setId(cardEntity.getId());
        return dto;

    }

    public void log(CardDTO dto) {
        log.info("New Card Added {} ", dto);
        log.debug("New Card Added {} ", dto);
        log.error("New Card Added {} ", dto);
        log.warn("New Card Added {} ", dto);
    }

    public CardDTO updateCard(CardDTO dto, Integer carId) {
        Optional<CardEntity> entity = cardRepository.findById(carId);
        CardEntity card = get(carId);
        card.setNumber(dto.getNumber());
        card.setExcDate(dto.getExcDate());
        card.setProfileId(dto.getProfileId());
        card.setBalance(entity.get().getBalance());
        card.setCreatedDate(entity.get().getCreatedDate());
        try {
            cardRepository.save(card);
        } catch (RuntimeException e) {
            log.error("Error in updating card {},error {}", dto, e.getMessage());
        }
        log(dto);
        return dto;
    }

    public void log() {
        log.info("New Card Added {} ");
        log.debug("New Card Added {} ");
        log.error("New Card Added {} ");
        log.warn("New Card Added {} ");
    }

    public String deleteCard(Integer cardId) {
        CardEntity card = get(cardId);
        try {
            cardRepository.delete(card);
        } catch (RuntimeException e) {
            log.error("Error in deleting card {},error {}", e.getMessage());
        }
        return "Succesfully";
    }

//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
//    public void increaseBalance(String number, Long balance) {
//        test();
//        Optional<CardEntity> optional = cardRepository.findByNumber(number);
//        if (optional.isPresent()) {
//            CardEntity entity = optional.get();
//            entity.setBalance(entity.getBalance() + balance);
//            cardRepository.save(entity);
//            System.out.println(entity);
//        }
//    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update_balance(String number, Long balance) {
        if (number.isEmpty() || number == null || balance == null) {
            log.warn("Error in update_balance card {}, error {}");
        }
        try {
            cardRepository.updateBalance(number, balance);
        } catch (RuntimeException e) {
            log.error("Error in update_balance card {},error {}", e.getMessage());
        }
    }

//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
//    public void test() {
//        // ....
//    }


    public CardEntity get(String number) {
        return cardRepository.findByNumber(number).orElseThrow(() -> new RuntimeException("Car nor found"));
    }

    public CardEntity get(Integer cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Car nor found"));
    }

    public List<CardDTO> getAllCard() {
        List<CardDTO> list = new LinkedList<>();
        List<CardEntity> entities = null;
        try {
            entities = cardRepository.findAll();
        } catch (RuntimeException e) {
            log.error("Error in update_balance card {},error {}", e.getMessage());
        }
        Iterator<CardEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public CardDTO toDTO(CardEntity card) {
        CardDTO cardEntity = new CardDTO();
        cardEntity.setId(card.getId());
        cardEntity.setBalance(card.getBalance());
        cardEntity.setNumber(card.getNumber());
        cardEntity.setExcDate(card.getExcDate());
        cardEntity.setProfileId(card.getProfileId());
        cardEntity.setCreatedDate(card.getCreatedDate());
        return cardEntity;
    }

    public PageImpl<CardDTO> filterSpe(int page, int size, CardFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Specification<CardEntity> spec = Specification.where(CardSpecfication.isNotNull("id"));
        if (filterDTO.getProfileId() != null) {
            spec.and(CardSpecfication.id("profileId", filterDTO.getProfileId()));
        }

        if (filterDTO.getNumber() != null || !filterDTO.getNumber().isEmpty()) {
            spec.and(CardSpecfication.equal("number", filterDTO.getNumber()));
        }
        if (filterDTO.getExcDate() != null || !filterDTO.getExcDate().isEmpty()) {
            spec.and(CardSpecfication.equal("excDate", filterDTO.getNumber()));
        }
        if (filterDTO.getBalance() != null) {
            spec.and(CardSpecfication.balance("balance", filterDTO.getBalance()));
        }
        if (filterDTO.getToDate() != null && filterDTO.getFromDate() != null) {
            spec.and(CardSpecfication.date(filterDTO.getFromDate(), filterDTO.getToDate()));
        }


        Page<CardEntity> cardEntityPage = cardRepository.findAll(spec, pageable);
        log();
        List<CardDTO> dtoList = cardEntityPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println(cardEntityPage.getTotalElements());
        return new PageImpl<>(dtoList, pageable, cardEntityPage.getTotalElements());
    }
}

