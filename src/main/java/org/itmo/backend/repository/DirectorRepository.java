package org.itmo.backend.repository;

import lombok.RequiredArgsConstructor;
import org.itmo.backend.dto.director.DirectorDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DirectorRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public int existsById(Long directorId) {
        return jdbcTemplate.queryForObject("""
                        select count(1)
                        from movie.director
                        where id = :directorId
                        """,
                Map.of("directorId", directorId),
                Integer.class);
    }

    public List<DirectorDto> findAll() {
        return jdbcTemplate.query("""
                        select *
                        from movie.director
                        """,
                Map.of(),
                new BeanPropertyRowMapper<>(DirectorDto.class));
    }

    public Optional<DirectorDto> findById(Long directorId) {
        return jdbcTemplate.query("""
                                select *
                                from movie.director
                                where id = :directorId
                                """,
                        Map.of("directorId", directorId),
                        new BeanPropertyRowMapper<>(DirectorDto.class))
                .stream().findFirst();
    }

    public Long createDirector(DirectorDto director) {
        return jdbcTemplate.queryForObject("""
                        insert into movie.director(fio)
                        values (:fio) returning id
                        """,
                Map.of("fio", director.getFio()),
                Long.class);
    }

    public int updateDirector(DirectorDto director) {
        return jdbcTemplate.update("""
                        update movie.director set
                            fio = :fio
                        where id = :directorId
                        """,
                Map.of("directorId", director.getId(),
                        "fio", director.getFio()));
    }

    public int deleteById(Long directorId) {
        return jdbcTemplate.update("""
                        delete
                        from movie.director
                        where id = :directorId
                        """,
                Map.of("directorId", directorId));
    }
}
