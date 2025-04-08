package com.epid.epid.repository.impl;

import com.epid.epid.domain.user.Role;
import com.epid.epid.domain.user.User;
import com.epid.epid.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final String FIND_BY_ID """
SELECT u.id as user_id,
    u.name as user_name,
    u.username as user_username,
    u.password as user_password,
    ur.role as user_role,
    FROM users u
    LEFT JOIN users_role ur on u.id = ur.user_id
    WHERE u.id = ?
    """;
    
   private final String FIND_BY_USERNAME """
SELECT u.id as user_id,
    u.name as user_name,
    u.username as user_username,
    u.password as user_password,
    ur.role as user_role,
    FROM users u
    LEFT JOIN users_role ur on u.id = ur.user_id
    WHERE u.username = ?
    """;


  private final String DELETE = """
            DELETE FROM users
            WHERE id = ?""";

    private final String CREATE = """
            INSERT INTO users (name,username,password)
            VALUES (?,?,?)""";

    private final String UPDATE = """
            UPDATE users
            SET name = ?,
            username = ?,
            password = ?
            WHERE id = ?""";

    private final String INSERT_USER_STATUS = """
          INSERT INTO users_roles (users_id,role)
            VALUES (?,?)""";


    @Override
    public Optional<User> findById(Long id) {
  try {
    //Созадали подключение к БД
            Connection connection = dataSourceConfig.getConnection();
       // Выполнили действие с В БД с помощью prepareStatement (подготовка к запросу) и передали в него запро, а так же пожелания, что бы 
        //ResultSet можно перемещать (прокручивать) как вперед, так и назад. 
        //Ты также можешь перейти к позиции относительно текущей позиции или перейти к абсолютной позиции.
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
            ResultSet.TYPE_SCROLL_INSENSITIV,
          //  CONCUR_READ_ONLY означает, что ResultSet может быть только прочитан.                                         
            ResultSet.CONCUR_READ_ONLY);
        //Интерфейс PreparedStatement содержит методы для установки строк — setString(),
        //для установки чисел — setInt(), setLong(), setDouble(), для установки дат — setDate().
        // первый параметр, определяет номер параметра номер параметра.
        //второй Значение long, которое передается, у нас айди.
        statment.setLong(1,id);
        //( далее с поомщью ExecuteQuery  выполнили запросов,при этом Возвращает объект ResultSet, который содержит все полученные данные.  2
           // Возвращаемый ResultSet никогда не является нулевым, даже если нет записей, соответствующих запросу.  5
           //а так же executeQuery Выбрасывает исключение, если запрос не возвращает ResultSet. 
        try (ResultSet rs = statment.executeQuery()){
            //метод говорит, что не может быть нулл, иначе исключение.
            // и результат в мэппер передаем, который создает объект мэппер и сеттает данные из бд в этот объект
            return Optional.ofNullable(UserRowMapper.MapRow(rs));
        }
    } catch (SQLExeption throwables){
        throwables.printStackTrace();
    } 
    
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

    }

    @Override
    public void insertUserRole(Long id, Role role) {

    }

    @Override
    public void delete(Long id) {

    }
}
