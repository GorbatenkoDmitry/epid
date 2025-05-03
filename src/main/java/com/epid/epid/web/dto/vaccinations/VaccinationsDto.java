package com.epid.epid.web.dto.vaccinations;

import com.epid.epid.web.dto.validation.OnCreate;
import com.epid.epid.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data

public class VaccinationsDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long worker_id;


    @NotNull(message = "Name must be not", groups = {OnCreate.class,OnUpdate.class})
    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String ADSM;

    @NotNull(message = "Username must be not", groups = {OnCreate.class,OnUpdate.class})
    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String HepatitisB;

    @NotNull(message = "Username must be not", groups = {OnCreate.class,OnUpdate.class})
    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String Measles;

    @NotNull(message = "Username must be not", groups = {OnCreate.class,OnUpdate.class})
    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String Rubella;


}
