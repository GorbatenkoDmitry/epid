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
    private final VaccinationsService vaccinationsService;

    private final WorkerService workerService;
    private final VaccinationsMapper vaccinationsMapper;


    @PutMapping
    public WorkerDto update(@Validated(OnUpdate.class) @RequestBody WorkerDto dto){

        Worker worker = workerMapper.toEntity(dto);
        Worker updatedWorker = workerService.update(worker);
        return workerMapper.toDto(updatedWorker);
    }


    @GetMapping("/{id}")
    public WorkerDto getById(@PathVariable Long id){
        Worker worker = workerService.getById(id);
        return workerMapper.toDto(worker);
    }

   @DeleteMapping("/{id}")
        public void deleteById(@PathVariable Long id) {
            workerService.delete(id);
        }

    @GetMapping("/{id}/vaccinations")
    public VaccinationsDto getVaccinationsByWorkerId(@PathVariable Long id) {
         Vaccinations vaccinations = vaccinationsService.getById(id);
        return vaccinationsMapper.toDto(vaccinations);
    }

    @PostMapping("/{id}/vaccinations")

    public VaccinationsDto createVaccinations(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody VaccinationsDto dto
    ) {
        Vaccinations vaccinations = vaccinationsMapper.toEntity(dto);
        Vaccinations createdVaccinations = vaccinationsService.create(vaccinations, id);
        return vaccinationsMapper.toDto(createdVaccinations);
    }

}
