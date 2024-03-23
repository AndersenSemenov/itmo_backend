package org.itmo.backend.dto.director;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDto {
    @NotNull
    private Long id;

    @Length(max = 100)
    @NotBlank
    private String fio;
}
