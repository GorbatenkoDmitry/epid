package com.epid.epid.service.impl;

import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.service.VaccinationsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class VaccinationsServiceImpl implements VaccinationsService {
    @Override
    public Vaccinations getById(Long id) {
        return null;
    }
    @Override
    @Transactional(readOnly = true)
    public Vaccinations getByName(String Name) {
        return null;

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
