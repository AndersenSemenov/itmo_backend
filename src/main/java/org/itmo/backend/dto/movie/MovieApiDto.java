package org.itmo.backend.dto.movie;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieApiDto {
    @NotNull
    private @Valid MovieDto movie;
}
