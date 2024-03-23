package org.itmo.backend.dto.movie;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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

    @Min(1900)
    @Max(2100)
    @NotNull
    private Integer year;

    @NotNull
    private Long director;

    @NotNull
    private LocalTime length;

    @Min(0)
    @Max(10)
    @NotNull
    private Integer rating;
}
