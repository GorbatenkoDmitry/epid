package com.epid.epid.service.impl;

import com.epid.epid.domain.exception.ResourceNotFoundException;
import com.epid.epid.domain.user.Role;
import com.epid.epid.domain.worker.Status;
import com.epid.epid.domain.worker.Worker;
import com.epid.epid.repository.WorkerRepository;
import com.epid.epid.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.epid.epid.domain.worker.Status.EMPLOYED;

@Service
@RequiredArgsConstructor

public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;


    @Override
    @Transactional(readOnly = true)
    public Worker getById(final Long id) {
        return workerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

    }


    @Override
    @Transactional(readOnly = true)
    public Worker getByNameSurname(String name, String surname) {

        return workerRepository.findByNameSurname(name, surname)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

    }

    @Override
    @Transactional
        public Worker update(Worker worker) {
        if (worker.getStatus() == null) {
            worker.setStatus(Status.NONE);
        }
        workerRepository.update(worker);
        return worker;
    }

    @Override
    @Transactional
    public Worker create(Worker worker) {
        {
            if (workerRepository.findByNameSurname(worker.getName(), worker.getSurname()).isPresent()) {
                throw new IllegalStateException("Worker already exists.");
            }
            worker.setName(worker.getName());
            worker.setSurname(worker.getSurname());
            worker.setStatus(EMPLOYED);
            workerRepository.insertWorkerStatus(worker.getId(), EMPLOYED);
            workerRepository.create(worker);
             return worker;
        }

    }

    @Override
    @Transactional
    public void delete(Long id) {
        workerRepository.delete(id);
    }
}
