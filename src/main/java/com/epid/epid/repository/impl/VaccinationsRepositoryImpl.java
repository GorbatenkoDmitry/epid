package com.epid.epid.repository.impl;

import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.repository.VaccinationsRepository;

public class VaccinationsRepositoryImpl implements VaccinationsRepository {
    
  private final String FIND_BY_ID """
SELECT v.id as Vaccinations_id,
    v.Vaccinations as Vaccinations_ADSM,
    v.Vaccinations as Vaccinations_HepatitisB,
    v.Vaccinations as Vaccinations_Measles,
   v.Vaccinations as Vaccinations_Rubella,
    FROM Vaccinations v
    LEFT JOIN users_id vi on v.id = vi.Vaccinations_id
    WHERE v.id = ?
    """;
   private final String UPDATE = """
            UPDATE Vaccinations
            SET ADSM = ?,
            HepatitisB = ?,
            Measles = ?
            Rubella = ?
            WHERE id = ?""";

private final String CREATE = """
            INSERT INTO Vaccinations (ADSM,HepatitisB,Measles,Rubella)
            VALUES (?,?,?,?)""";


  private final String DELETE = """
            DELETE FROM Vaccinations
            WHERE id = ?""";

    @Override
    public Vaccinations getById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(Vaccinations.RowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while finding vaccinations by id.");
        }
    }
    }

    @Override
    public Vaccinations update(Vaccinations vaccinations) {
        try {      Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, vacccinations.getADSM());
             statement.setString(2, vaccinations.getHepatitisB());
             statement.setString(3, vaccinations.getMeasles());
             statement.setString(4, vaccinations.getRubella());
             statement.setLong(5, vaccinations.getId());

                  statement.executeUpdate();
 } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while UDATAING vaccinations.");
        }
    
    

    @Override
    public Vaccinations create(Vaccinations vaccinations) {
               try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while CREATING vaccinations.");
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
            throw new ResourceMappingException("Error while DELETING vaccinations.");
        }
    }
}
