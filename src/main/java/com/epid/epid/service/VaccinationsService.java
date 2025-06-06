package com.epid.epid.service;

import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.domain.worker.Worker;
import java.util.Optional;

public interface VaccinationsService {

    Vaccinations getById(Long id);
    Vaccinations getByWorkerId(Long worker_id);

    Vaccinations update (Vaccinations vaccinations);

    Vaccinations create (Vaccinations vaccinations,Long userId);

    void delete (Long id);
}
