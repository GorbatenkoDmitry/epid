package com.epid.epid.service;

import com.epid.epid.domain.worker.Worker;

import java.util.Optional;

public interface WorkerService {


    Worker getById(Long id);

    Worker getByNameSurname(String name, String surname);

    Worker update (Worker worker);

    Worker create (Worker worker);

    void delete (Long id);
}
