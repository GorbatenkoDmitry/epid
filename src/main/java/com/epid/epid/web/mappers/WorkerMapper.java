package com.epid.epid.web.mappers;

import com.epid.epid.domain.worker.Worker;
import com.epid.epid.web.dto.Worker.WorkerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkerMapper {

    WorkerDto toDto(Worker worker);
    List<WorkerDto> toDto(List<Worker> worker);
    Worker toEntity(WorkerDto dto);

}
