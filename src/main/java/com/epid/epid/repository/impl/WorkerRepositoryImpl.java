package com.epid.epid.repository.impl;


import com.epid.epid.domain.exception.ResourceMappingException;
import com.epid.epid.domain.worker.Status;
import com.epid.epid.domain.worker.Worker;
import com.epid.epid.repository.DataSourceConfig;
import com.epid.epid.repository.WorkerRepository;
import com.epid.epid.repository.mappers.WorkerRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class WorkerRepositoryImpl implements WorkerRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT w.id              as worker_id,
                   w.name           as worker_name,
                   w.surname     as worker_surname
            FROM worker w
            WHERE w.id = ?""";

    private final String FIND_NAME_SURNAME = """
            SELECT w.id              as worker_id,
                   w.name           as worker_name,
                   w.surname     as worker_surname
            FROM worker w
            WHERE w.name = ? AND w.surname = ?""";

    private final String DELETE = """
            DELETE FROM worker
            WHERE id = ?""";

    private final String CREATE = """
            INSERT INTO worker (name,surname,status)
            VALUES (?,?,?,?)""";

    private final String UPDATE = """
            UPDATE worker
            SET name = ?,
            surname = ?,
            status = ?
            WHERE id = ?""";

    private final String INSERT_USER_STATUS = """
          INSERT INTO worker_status (id,status)
            VALUES (?,?)""";

    
    @Override
    public Optional<Worker> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(WorkerRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while finding user by id.");
        }
    }
    @Override
    public Optional<Worker> findByNameSurname(String name, String surname) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_NAME_SURNAME);
            statement.setString(1, name);
            statement.setString(2, surname);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(WorkerRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while finding user by name,surname.");
        }
    }


    @Override
    public void update(Worker worker) {
 try {      Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, worker.getName());
      if(worker.getSurname() ==null){
statment.setNull(2, Types.VARCHAR);  
} else {
statment.setString(2, worker.getSurname());  
}
             statement.setString(3, worker.getStatus().name());
             statement.setLong(4, worker.getId());

                  statement.executeUpdate();
 } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while UDATAING WORKING.");
        }
    }

    @Override
    public void create(Worker worker) {

        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while CREATING WORKER.");
        }
    }


    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while DELETING WORKER.");
        }
    }

    @Override
    public void insertWorkerStatus(Long Id, Status status) 
      try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_STATUS);
            statement.setLong(1, id);
            statement.setString(2,status.name());
            statement.executeUpdate();

      } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while inserting user stats.");
        }
    }






}
