package org.itmo.backend.service;

import lombok.RequiredArgsConstructor;
import org.itmo.backend.dto.movie.MovieDto;
import org.itmo.backend.dto.movie.MovieList;
import org.itmo.backend.dto.movie.MovieApiDto;
import org.itmo.backend.exception.RecordNotFoundException;
import org.itmo.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final DirectorService directorService;

    public MovieList getAllMovies() {
        List<MovieDto> moviesList = movieRepository.findAll();
        return new MovieList(moviesList);
    }

    public MovieApiDto getMovieById(Long movieId) {
        MovieDto movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RecordNotFoundException("Не найден фильм по id=" + movieId));
        return new MovieApiDto(movie);
    }

    public MovieApiDto createMovie(MovieApiDto movieApiDto) {
        MovieDto movie = movieApiDto.getMovie();
        if (!directorService.existsById(movie.getDirector())) {
            throw new RecordNotFoundException("Не найден режиссер по id=" + movie.getId());
        }

        Long movieId = movieRepository.createMovie(movie);
        movie.setId(movieId);
        return new MovieApiDto(movie);
    }

    public MovieApiDto updateMovie(MovieApiDto movieApiDto) {
        MovieDto movie = movieApiDto.getMovie();
        if (!directorService.existsById(movie.getDirector())) {
            throw new RecordNotFoundException("Не найден режиссер по id=" + movie.getId());
        }

        if (movieRepository.updateMovie(movie) == 0) {
            throw new RecordNotFoundException("Не найден фильм по id=" + movie.getId());
        }
        return new MovieApiDto(movie);
    }

    public void deleteById(Long movieId) {
        if (movieRepository.deleteById(movieId) == 0) {
            throw new RecordNotFoundException("Не найден фильм по id=" + movieId);
        }
    }
}
