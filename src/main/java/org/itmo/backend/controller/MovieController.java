package org.itmo.backend.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.itmo.backend.dto.movie.MovieApiDto;
import org.itmo.backend.dto.movie.MovieDto;
import org.itmo.backend.dto.movie.MovieList;
import org.itmo.backend.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final Validator validator;

    @GetMapping
    public ResponseEntity<MovieList> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieApiDto> getMovieById(@PathVariable("id") Long movieId) {
        return ResponseEntity.ok(movieService.getMovieById(movieId));
    }

    @PostMapping
    public ResponseEntity<MovieApiDto> createMovie(@RequestBody MovieApiDto movieApiDto) {
        validateMovie(movieApiDto);
        return ResponseEntity.ok(movieService.createMovie(movieApiDto));
    }

    @PatchMapping
    public ResponseEntity<MovieApiDto> updateMovie(@RequestBody MovieApiDto movieApiDto) {
        validateMovie(movieApiDto);
        return ResponseEntity.ok(movieService.updateMovie(movieApiDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long movieId) {
        movieService.deleteById(movieId);
        return ResponseEntity.accepted().build();
    }


    private void validateMovie(MovieApiDto movieApiDto) {
        MovieDto movie = movieApiDto.getMovie();
        Set<ConstraintViolation<MovieDto>> constraints = validator.validate(movie);

        if (constraints.isEmpty()) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (var constraint : constraints) {
            builder.append("Поле ")
                    .append(constraint.getPropertyPath())
                    .append(" ")
                    .append(constraint.getMessage())
                    .append("; ");
        }

        throw new ValidationException(builder.toString());
    }
}
