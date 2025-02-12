package com.epid.epid.repository;

import com.epid.epid.domain.worker.Status;
import com.epid.epid.domain.worker.Worker;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkerRepository {

    Optional<Worker> findById(Long id);

    Optional<Worker> findByNameSurname(String name, String surname);

    void update (Worker worker);

    void create (Worker worker);

    void delete (Long id);

    void insertWorkerStatus(@Param("workerId") Long workerId, @Param("status") Status status);



}
