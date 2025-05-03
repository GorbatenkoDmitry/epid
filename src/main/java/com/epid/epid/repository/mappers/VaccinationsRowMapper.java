package com.epid.epid.repository.mappers;

import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.domain.worker.Worker;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class VaccinationsRowMapper {

    @SneakyThrows
    public static Vaccinations mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Vaccinations vaccinations = new Vaccinations();
            vaccinations.setId(resultSet.getLong("worker_id"));
            vaccinations.setADSM(resultSet.getString("worker_name"));
            vaccinations.setHepatitisB(resultSet.getString("worker_surname"));
            vaccinations.setMeasles(resultSet.getString("worker_surname"));
            vaccinations.setRubella(resultSet.getString("worker_surname"));
            return vaccinations;
        }

        return null;

    }

}