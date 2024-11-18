package com.epid.epid.service.impl;

import com.epid.epid.domain.worker.Worker;
import com.epid.epid.service.WorkerService;
import org.springframework.stereotype.Service;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Override
    public Worker getById(Long id) {
        return null;
    }

    @Override
    public Worker getByNameSurname(String name, String surname) {
        return null;
    }

    @Override
    public Worker update(Worker worker) {
        return null;
    }

    @Override
    public Worker create(Worker worker) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
