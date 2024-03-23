package org.itmo.backend.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    @NotNull
    private Long id;

    @Length(max = 100)
    @NotBlank
    private String title;

    @Range(min = 1900, max = 2100)
    @NotNull
    private Integer year;

    @NotNull
    private Long director;

    @NotNull
    private LocalTime length;

    @NotNull
    @Range(max = 10)
    private Integer rating;
}
