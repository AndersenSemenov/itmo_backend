package org.itmo.backend.repository;

import lombok.RequiredArgsConstructor;
import org.itmo.backend.dto.movie.MovieDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovieRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<MovieDto> findAll() {
        return jdbcTemplate.query("""
                        select *
                        from movie.movie
                        """,
                Map.of(),
                new BeanPropertyRowMapper<>(MovieDto.class));
    }

    public Optional<MovieDto> findById(Long movieId) {
        return jdbcTemplate.query("""
                                select *
                                from movie.movie
                                where id = :movieId
                                """,
                        Map.of("movieId", movieId),
                        new BeanPropertyRowMapper<>(MovieDto.class))
                .stream().findFirst();
    }

    public Long createMovie(MovieDto movie) {
        return jdbcTemplate.queryForObject("""
                        insert into movie.movie(title, year, director, length, rating)
                        values (:title, :year, :director, :length, :rating) returning id
                        """,
                Map.of("title", movie.getTitle(),
                        "year", movie.getYear(),
                        "director", movie.getDirector(),
                        "length", movie.getLength(),
                        "rating", movie.getRating()),
                Long.class);
    }

    public int updateMovie(MovieDto movie) {
        return jdbcTemplate.update("""
                        update movie.movie set
                            title = :title,
                            year = :year,
                            director = :director,
                            length = :length,
                            rating = :rating
                        where id = :movieId
                        """,
                Map.of("movieId", movie.getId(),
                        "title", movie.getTitle(),
                        "year", movie.getYear(),
                        "director", movie.getDirector(),
                        "length", Time.valueOf(movie.getLength()),
                        "rating", movie.getRating()));
    }

    public int deleteById(Long movieId) {
        return jdbcTemplate.update("""
                        delete
                        from movie.movie
                        where id = :movieId
                        """,
                Map.of("movieId", movieId));
    }
}
