package com.epid.epid.web.dto.Worker;

import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.domain.worker.Status;
import com.epid.epid.web.dto.validation.OnCreate;
import com.epid.epid.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;
@Data

public class WorkerDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Name must be not", groups = {OnCreate.class,OnUpdate.class})
    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String name;

    @NotNull(message = "Name must be not", groups = {OnCreate.class,OnUpdate.class})
    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String surname;

    private Status status;

}
