package com.epid.epid.web.controller;

import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.domain.worker.Worker;
import com.epid.epid.service.VaccinationsService;
import com.epid.epid.service.WorkerService;
import com.epid.epid.web.dto.Worker.WorkerDto;
import com.epid.epid.web.dto.vaccinations.VaccinationsDto;
import com.epid.epid.web.dto.validation.OnCreate;
import com.epid.epid.web.dto.validation.OnUpdate;
import com.epid.epid.web.mappers.VaccinationsMapper;
import com.epid.epid.web.mappers.WorkerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/worker")
@RequiredArgsConstructor
@Validated
public class WorkerController {
    private final WorkerMapper workerMapper;

    private final WorkerService workerService;

    @GetMapping("/{id}")
    public WorkerDto getById(@PathVariable Long id){
        Worker worker = workerService.getById(id);
        return workerMapper.toDto(worker);
    }

    @GetMapping("/{name}/{surname}")
    public WorkerDto getByNameSurname(@PathVariable String name,@PathVariable String surname){
        Worker worker = workerService.getByNameSurname(name,surname);
        return workerMapper.toDto(worker);
    }

    @PutMapping
    public WorkerDto update(@Validated(OnUpdate.class) @RequestBody WorkerDto dto){

        Worker worker = workerMapper.toEntity(dto);
        Worker updatedWorker = workerService.update(worker);
        return workerMapper.toDto(updatedWorker);
    }

   @DeleteMapping("/{id}")
        public void deleteById(@PathVariable Long id) {
            workerService.delete(id);
        }

}
