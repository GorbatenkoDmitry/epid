package com.epid.epid.service.impl;

import com.epid.epid.domain.exception.ResourceNotFoundException;
import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.repository.UserRepository;
import com.epid.epid.repository.VaccinationsRepository;
import com.epid.epid.service.VaccinationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class VaccinationsServiceImpl implements VaccinationsService {

    private final VaccinationsRepository vaccinationsRepository;

    @Override
    @Transactional(readOnly = true)
 //   @Cachable(value = "VaccinationsService::getById", key = "#id")
            public Vaccinations getById(Long id)  {
        return vaccinationsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }
    @Override
    @Transactional(readOnly = true)
  //  @Cachable(value = "VaccinationsService::getByWorkerId", key = "#worker_id")
    public Vaccinations getByWorkerId(Long worker_id)  {
        return vaccinationsRepository.findByWorker_Id(worker_id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional
    public Vaccinations update(Vaccinations vaccinations) {
        return null;
    }

    @Override
    @Transactional
    public Vaccinations create(Vaccinations vaccinations, Long userId) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }
}
