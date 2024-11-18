package com.epid.epid.repository;

import com.epid.epid.domain.user.User;
import com.epid.epid.domain.worker.Worker;

import java.util.Optional;

public interface WorkerRepository {

    Optional<Worker> findById(Long id);

    Optional<Worker> findByNameSurname(String name, String surname);

    void update (Worker worker);

    void create (Worker worker);

    void delete (Long id);

}
