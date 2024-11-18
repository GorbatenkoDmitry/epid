package com.epid.epid.repository;

import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.domain.worker.Worker;

public interface VaccinationsRepository {
    Vaccinations getById(Long id);

    Vaccinations update (Vaccinations vaccinations);

    Vaccinations create (Vaccinations vaccinations);

    void delete (Long id);
}
