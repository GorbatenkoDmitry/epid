package com.epid.epid.repository.mappers;

import com.epid.epid.domain.worker.Worker;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class WorkerRowMapper {

    @SneakyThrows
    public static Worker mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Worker worker = new Worker();
            worker.setId(resultSet.getLong("worker_id"));
            worker.setName(resultSet.getString("worker_name"));
            worker.setSurname(resultSet.getString("worker_surname"));
            return worker;
        }

        return null;

    }

}