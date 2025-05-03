package com.epid.epid.repository.impl;

import com.epid.epid.domain.exception.ResourceMappingException;
import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.repository.DataSourceConfig;
import com.epid.epid.repository.VaccinationsRepository;
import com.epid.epid.repository.WorkerRepository;
import com.epid.epid.repository.mappers.VaccinationsRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
@RequiredArgsConstructor
//@Repository
public class VaccinationsRepositoryImpl implements VaccinationsRepository {
    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
         SELECT v.id            as vaccinations_id,
         v.worker_id            as vaccinations_worker_id,
          v.ADSM                as vaccinations_ADSM,
          v.HepatitisB          as vaccinations_HepatitisB,
          v.Measles             as vaccinations_Measles,
          v.Rubella             as vaccinations_Rubella
          FROM vaccinations v
          WHERE v.id = ? """;

    private final String FIND_BY_WORKER_ID = """
         SELECT v.id            as vaccinations_id,
         v.worker_id            as vaccinations_worker_id,
          v.ADSM                as vaccinations_ADSM,
          v.HepatitisB          as vaccinations_HepatitisB,
          v.Measles             as vaccinations_Measles,
          v.Rubella             as vaccinations_Rubella
          FROM vaccinations v
          WHERE v.worker_id = ? """;
   private final String UPDATE = """
            UPDATE Vaccinations
            SET 
            worker_id = ?,
            ADSM = ?,
            HepatitisB = ?,
            Measles = ?,
            Rubella = ?
            WHERE id = ?""";

private final String CREATE = """
            INSERT INTO Vaccinations (worker_id,ADSM,HepatitisB,Measles,Rubella)
            VALUES (?,?,?,?)""";


  private final String DELETE = """
            DELETE FROM Vaccinations
            WHERE id = ?""";

    @Override
    public Optional<Vaccinations> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(VaccinationsRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while finding user by id.");
        }
    }
    @Override
    public Optional<Vaccinations> findByWorker_Id(Long worker_id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_WORKER_ID);
            statement.setLong(1, worker_id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(VaccinationsRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while finding user by id.");
        }
    }
    @Override
    public void update(Vaccinations vaccinations) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setLong(1, vaccinations.getWorker_id());
            statement.setString(2, vaccinations.getADSM());
            statement.setString(3, vaccinations.getHepatitisB());
            statement.setString(4, vaccinations.getMeasles());
            statement.setString(5, vaccinations.getRubella());
            statement.setLong(6, vaccinations.getId());

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while UDATAING vaccinations.");
        }
    }

        @Override
        public void create (Vaccinations vaccinations){
            try {
                Connection connection = dataSourceConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE);
                statement.setLong(1, vaccinations.getId());
                statement.executeUpdate();
            } catch (SQLException throwables) {
                throw new ResourceMappingException("Error while CREATING vaccinations.");
            }

        }

        @Override
        public void delete (Long id){

            try {
                Connection connection = dataSourceConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE);
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException throwables) {
                throw new ResourceMappingException("Error while DELETING vaccinations.");
            }
        }






}
