package com.epid.epid.repository;

import com.epid.epid.domain.vaccinations.Vaccinations;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;
@Mapper

public interface VaccinationsRepository {
    Optional<Vaccinations> findById (Long id);
    Optional<Vaccinations> findByWorker_Id (Long worker_id);

    void update (Vaccinations vaccinations);

    void create (Vaccinations vaccinations);

    void delete (Long id);
}
