package com.epid.epid.web.controller;

import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.service.VaccinationsService;
import com.epid.epid.web.dto.vaccinations.VaccinationsDto;
import com.epid.epid.web.dto.validation.OnCreate;
import com.epid.epid.web.mappers.VaccinationsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/worker/vaccinations")
@RequiredArgsConstructor
@Validated
        @Tag(name = 'Контроллер для работы с данными вакцинации', description = 'Vac API')
public class VaccinationsController {
    private final VaccinationsService vaccinationsService;
    private final VaccinationsMapper vaccinationsMapper;

            @Operation(summary = "Get вакцинацию by id")
    @GetMapping("/{id}")
    public VaccinationsDto getVaccinationsById(@PathVariable Long id) {
        Vaccinations vaccinations = vaccinationsService.getById(id);
        return vaccinationsMapper.toDto(vaccinations);
    }
          
        
@Operation(summary = "Get вакцинацию by worker id")
    @GetMapping("/{worker_id}")
    public VaccinationsDto getVaccinationsByWorkerId(@PathVariable Long worker_id) {
        Vaccinations vaccinations = vaccinationsService.getByWorkerId(worker_id);
        return vaccinationsMapper.toDto(vaccinations);
    }
       
    @Operation(summary = "Create вакцинацию")
    @PostMapping("/{id}/vaccinations/create")

    public VaccinationsDto createVaccinations(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody VaccinationsDto dto
    ) {
        Vaccinations vaccinations = vaccinationsMapper.toEntity(dto);
        Vaccinations createdVaccinations = vaccinationsService.create(vaccinations, id);
        return vaccinationsMapper.toDto(createdVaccinations);
    }

@Operation(summary = "Delete вакцинацию")
    @DeleteMapping("/{id}")
    public void VaccinationsDeleteById(@PathVariable Long id) {
        vaccinationsService.delete(id);
    }
}
