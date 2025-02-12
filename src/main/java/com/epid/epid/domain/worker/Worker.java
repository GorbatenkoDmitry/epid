package com.epid.epid.domain.worker;

import com.epid.epid.domain.vaccinations.Vaccinations;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Worker {
    private Long id;
    private String name;
    private String surname;
    private Status status;
    private List<Vaccinations> vaccinations;

}
