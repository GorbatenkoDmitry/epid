package com.epid.epid.repository.impl;

import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.repository.VaccinationsRepository;

public class VaccinationsRepositoryImpl implements VaccinationsRepository {
    
  private final String FIND_BY_ID """
SELECT v.id as Vaccinations_id,
    v.Vaccinations as Vaccinations_ADSM,
    v.Vaccinations as Vaccinations_HepatitisB,
    v.Vaccinations as Vaccinations_Measles,
   v.Vaccinations as Vaccinations_Measles,
    FROM Vaccinations v
    LEFT JOIN users_id vi on v.id = vi.Vaccinations_id
    WHERE v.id = ?
    """;
   private final String UPDATE = """
            UPDATE Vaccinations
            SET ADSM = ?,
            HepatitisB = ?,
            Measles = ?
            WHERE id = ?""";


    @Override
    public Vaccinations getById(Long id) {
        return null;
    }

    @Override
    public Vaccinations update(Vaccinations vaccinations) {
        return null;
    }

    @Override
    public Vaccinations create(Vaccinations vaccinations) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
