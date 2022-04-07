package com.company.controller;

import com.company.dto.ProfileDTO;
import com.company.servise.ProfileService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/Profile")
public class ProfileController {
    @Autowired
    private ProfileService profileServise;

    @PostMapping("create")
    @ApiOperation(value = "create method", notes = "Bunda Profile create qilinadi", nickname = "NickName")
    public ResponseEntity<?> create(@Valid @RequestBody ProfileDTO dto) {
        profileServise.log(dto);
        ProfileDTO response = profileServise.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllProfile")
    @ApiOperation(value = "Get method", notes = "Bunda Barcha Profilelar olinadi", nickname = "NickName")
    public ResponseEntity<?> getAllProfile() {
        profileServise.log();
        List<ProfileDTO> getAllProfile = profileServise.getAllProfile();
        return ResponseEntity.ok(getAllProfile);
    }

    @PutMapping("/update/{profileId}")
    @ApiOperation(value = "update method", notes = "Bunda Profile update qilinadi", nickname = "NickName")
    public ResponseEntity<?> update(@PathVariable("profileId") Integer profileId, @Valid @RequestBody ProfileDTO dto) {
        profileServise.log(dto);
        ProfileDTO profileDTO = profileServise.update(dto, profileId);
        return ResponseEntity.ok(profileDTO);
    }

    @DeleteMapping("deleteProfile/{profileId}")
    @ApiOperation(value = "delete method", notes = "Bunda Profile delete qilinadi", nickname = "NickName")
    public ResponseEntity<?> delete(@PathVariable("profileId") Integer profileId) {
        profileServise.log();
        String response = profileServise.delete(profileId);
        return ResponseEntity.ok(response);
    }

}
