package com.epid.epid.service;

import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.domain.worker.Worker;
import java.util.Optional;

public interface VaccinationsService {

    Vaccinations getById(Long id);
    Vaccinations getByName(String Name);

    Vaccinations update (Vaccinations vaccinations);

    Vaccinations create (Vaccinations vaccinations,Long userId);

    void delete (Long id);
}
