package com.epid.epid.repository.impl;

import com.epid.epid.domain.worker.Worker;
import com.epid.epid.repository.WorkerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WorkerRepositoryImpl implements WorkerRepository {
    @Override
    public Optional<Worker> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Worker> findByNameSurname(String name, String surname) {
        return Optional.empty();
    }

    @Override
    public void update(Worker worker) {

    }

    @Override
    public void create(Worker worker) {

    }

    @Override
    public void delete(Long id) {

    }
}
