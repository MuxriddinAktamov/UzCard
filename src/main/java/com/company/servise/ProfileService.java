package com.company.servise;

import com.company.dto.CardDTO;
import com.company.dto.CardFilterDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.ProfileFilterDTO;
import com.company.entity.CardEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.ProfileRepository;
import com.company.spec.CardSpecfication;
import com.company.spec.ProfileSpecfication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setMiddleName(dto.getMiddleName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setCreatedDate(LocalDateTime.now());

        try {
            profileRepository.save(entity);
        } catch (RuntimeException e) {
            log.error("Error in Adding profile {},error {}", dto, e.getMessage());
        }
        dto.setCreatedDate(LocalDateTime.now());
        dto.setId(entity.getId());
        return dto;
    }

    public ProfileEntity get(Integer id) {
        return this.profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile Not found"));
    }

    public List<ProfileDTO> getAllProfile() {
        List<ProfileDTO> list = new LinkedList<>();
        List<ProfileEntity> entities = null;
        try {
            entities = profileRepository.findAll();
        } catch (RuntimeException e) {
            log.error("Error in AllProfile profile {},error {}", e.getMessage());
        }
        Iterator<ProfileEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public void log(ProfileDTO dto) {
        log.info("New Profile action {} ", dto);
        log.debug("New Profile action {} ", dto);
        log.error("New Profile action {} ", dto);
        log.warn("New Profile action {} ", dto);
    }

    public void log() {
        log.info("New Profile action {} ");
        log.debug("New Profile action {} ");
        log.error("New Profile action {} ");
        log.warn("New Profile action {} ");
    }

    public ProfileDTO update(ProfileDTO dto, Integer id) {
        ProfileEntity profile = get(id);
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setMiddleName(dto.getMiddleName());
        profile.setBirthDate(dto.getBirthDate());
        try {
            profileRepository.save(profile);
        } catch (RuntimeException e) {
            log.error("Error in updating profile {},error {}", dto, e.getMessage());
        }
        dto.setCreatedDate(profile.getCreatedDate());
        dto.setId(profile.getId());
        return dto;
    }

    public String delete(Integer id) {
        ProfileEntity profile = get(id);
        try {
            profileRepository.delete(profile);
        } catch (RuntimeException e) {
            log.error("Error in updating profile {},error {}", e.getMessage());
        }
        return "Succesfully";
    }

    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setMiddleName(entity.getMiddleName());
        dto.setBirthDate(entity.getBirthDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<ProfileDTO> filterSpe(int page, int size, ProfileFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Specification<ProfileEntity> spec = Specification.where(ProfileSpecfication.isNotNull("id"));
        if (filterDTO.getName() != null || !filterDTO.getSurname().isEmpty()) {
            spec.and(ProfileSpecfication.equal("name", filterDTO.getName()));
        }
        if (filterDTO.getSurname() != null || !filterDTO.getSurname().isEmpty()) {
            spec.and(ProfileSpecfication.equal("surname", filterDTO.getSurname()));
        }
        if (filterDTO.getMiddleName() != null || !filterDTO.getMiddleName().isEmpty()) {
            spec.and(ProfileSpecfication.equal("middleName", filterDTO.getMiddleName()));
        }
        if (filterDTO.getBirthDate() != null) {
            spec.and(ProfileSpecfication.balance("birthDate", filterDTO.getBirthDate()));
        }
        if (filterDTO.getToDate() != null && filterDTO.getFromDate() != null) {
            spec.and(ProfileSpecfication.date(filterDTO.getFromDate(), filterDTO.getToDate()));
        }


        Page<ProfileEntity> profileEntityPage = profileRepository.findAll(spec, pageable);
        List<ProfileDTO> dtoList = profileEntityPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println(profileEntityPage.getTotalElements());
        return new PageImpl<>(dtoList, pageable, profileEntityPage.getTotalElements());
    }
}
