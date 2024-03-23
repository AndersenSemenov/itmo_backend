package org.itmo.backend.service;

import lombok.RequiredArgsConstructor;
import org.itmo.backend.dto.movie.MovieApiDto;
import org.itmo.backend.dto.movie.MovieDto;
import org.itmo.backend.dto.movie.MovieList;
import org.itmo.backend.exception.RecordNotFoundException;
import org.itmo.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.itmo.backend.service.DirectorService.DIRECTOR_NOT_FOUND_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final DirectorService directorService;
    private static final String MOVIE_NOT_FOUND_ERROR_MESSAGE = "Не найден фильм по id=";

    public MovieList getAllMovies() {
        List<MovieDto> moviesList = movieRepository.findAll();
        return new MovieList(moviesList);
    }

    public MovieApiDto getMovieById(Long movieId) {
        MovieDto movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RecordNotFoundException(MOVIE_NOT_FOUND_ERROR_MESSAGE + movieId));
        return new MovieApiDto(movie);
    }

    public MovieApiDto createMovie(MovieApiDto movieApiDto) {
        MovieDto movie = movieApiDto.getMovie();
        if (!directorService.existsById(movie.getDirector())) {
            throw new RecordNotFoundException(DIRECTOR_NOT_FOUND_ERROR_MESSAGE + movie.getId());
        }

        Long movieId = movieRepository.createMovie(movie);
        movie.setId(movieId);
        return new MovieApiDto(movie);
    }

    public MovieApiDto updateMovie(MovieApiDto movieApiDto) {
        MovieDto movie = movieApiDto.getMovie();
        if (!directorService.existsById(movie.getDirector())) {
            throw new RecordNotFoundException(DIRECTOR_NOT_FOUND_ERROR_MESSAGE + movie.getId());
        }

        if (movieRepository.updateMovie(movie) == 0) {
            throw new RecordNotFoundException(MOVIE_NOT_FOUND_ERROR_MESSAGE + movie.getId());
        }
        return new MovieApiDto(movie);
    }

    public void deleteById(Long movieId) {
        if (movieRepository.deleteById(movieId) == 0) {
            throw new RecordNotFoundException(MOVIE_NOT_FOUND_ERROR_MESSAGE + movieId);
        }
    }
}
