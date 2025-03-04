package com.epid.epid.domain.vaccinations;

import com.epid.epid.domain.worker.Status;
import com.epid.epid.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class Vaccinations {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String ADSM;

    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String HepatitisB;

    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String Measles;

    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String Rubella;


}
