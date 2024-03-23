package org.itmo.backend.service;

import lombok.RequiredArgsConstructor;
import org.itmo.backend.dto.director.DirectorApiDto;
import org.itmo.backend.dto.director.DirectorDto;
import org.itmo.backend.dto.director.DirectorList;
import org.itmo.backend.exception.RecordNotFoundException;
import org.itmo.backend.repository.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    public static final String DIRECTOR_NOT_FOUND_ERROR_MESSAGE = "Не найден режиссер по id=";

    public boolean existsById(Long directorId) {
        return directorRepository.existsById(directorId) > 0;
    }

    public DirectorList getAllDirectors() {
        List<DirectorDto> directorsList = directorRepository.findAll();
        return new DirectorList(directorsList);
    }

    public DirectorApiDto getDirectorById(Long directorId) {
        DirectorDto director = directorRepository.findById(directorId)
                .orElseThrow(() -> new RecordNotFoundException(DIRECTOR_NOT_FOUND_ERROR_MESSAGE + directorId));
        return new DirectorApiDto(director);
    }

    public DirectorApiDto createDirector(DirectorApiDto directorApiDto) {
        DirectorDto director = directorApiDto.getDirector();
        Long directorId = directorRepository.createDirector(director);
        director.setId(directorId);
        return new DirectorApiDto(director);
    }

    public DirectorApiDto updateDirector(DirectorApiDto directorApiDto) {
        DirectorDto director = directorApiDto.getDirector();
        if (directorRepository.updateDirector(director) == 0) {
            throw new RecordNotFoundException(DIRECTOR_NOT_FOUND_ERROR_MESSAGE + director.getId());
        }
        return new DirectorApiDto(director);
    }

    public void deleteById(Long directorId) {
        if (directorRepository.deleteById(directorId) == 0) {
            throw new RecordNotFoundException(DIRECTOR_NOT_FOUND_ERROR_MESSAGE + directorId);
        }
    }
}
