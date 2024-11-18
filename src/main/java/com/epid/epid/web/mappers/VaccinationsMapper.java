package com.epid.epid.web.mappers;

import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.web.dto.vaccinations.VaccinationsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface VaccinationsMapper {
    VaccinationsDto toDto(Vaccinations vaccinations);
 //   List<VaccinationsDto> toDto(List<Vaccinations> vaccinations);
    Vaccinations toEntity(VaccinationsDto dto);
}
