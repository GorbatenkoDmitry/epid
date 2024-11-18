package com.epid.epid.web.dto.vaccinations;

import com.epid.epid.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;

public class VaccinationsDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)

    private Long id;

    private String ADSM;
    private String HepatitisB;
    private String Measles;
    private String Rubella;

}
