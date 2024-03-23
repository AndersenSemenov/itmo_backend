package org.itmo.backend.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.itmo.backend.dto.director.DirectorDto;
import org.itmo.backend.dto.director.DirectorList;
import org.itmo.backend.dto.director.DirectorApiDto;
import org.itmo.backend.service.DirectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/directors")
public class DirectorController {

    private final DirectorService directorService;
    private final Validator validator;

    @GetMapping
    public ResponseEntity<DirectorList> getAllDirectors() {
        return ResponseEntity.ok(directorService.getAllDirectors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorApiDto> getDirectorById(@PathVariable("id") Long directorId) {
        return ResponseEntity.ok(directorService.getDirectorById(directorId));
    }

    @PostMapping
    public ResponseEntity<DirectorApiDto> createDirector(@RequestBody DirectorApiDto directorApiDto) {
        validateDirector(directorApiDto);
        return ResponseEntity.ok(directorService.createDirector(directorApiDto));
    }

    @PatchMapping
    public ResponseEntity<DirectorApiDto> updateDirector(@RequestBody DirectorApiDto directorApiDto) {
        validateDirector(directorApiDto);
        return ResponseEntity.ok(directorService.updateDirector(directorApiDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long directorId) {
        directorService.deleteById(directorId);
        return ResponseEntity.accepted().build();
    }


    private void validateDirector(DirectorApiDto directorApiDto) {
        DirectorDto director = directorApiDto.getDirector();
        Set<ConstraintViolation<DirectorDto>> constraints = validator.validate(director);

        if (constraints.isEmpty()) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (var constraint : constraints) {
            builder.append("Поле ")
                    .append(constraint.getPropertyPath())
                    .append(" ")
                    .append(constraint.getMessage())
                    .append(" ;");
        }

        throw new ValidationException(builder.toString());
    }
}
