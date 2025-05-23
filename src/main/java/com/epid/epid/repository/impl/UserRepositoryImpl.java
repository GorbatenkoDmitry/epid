package com.epid.epid.repository.impl;

import com.epid.epid.domain.exception.ResourceMappingException;
import com.epid.epid.domain.user.Role;
import com.epid.epid.domain.user.User;
import com.epid.epid.repository.DataSourceConfig;
import com.epid.epid.repository.UserRepository;
import com.epid.epid.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;
//@Repository
@RequiredArgsConstructor

public class UserRepositoryImpl implements UserRepository {
    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
SELECT u.id as user_id,
    u.name as user_name,
    u.username as user_username,
    u.password as user_password,
    ur.roles as user_role
    FROM users u
    LEFT JOIN users_roles ur on u.id = ur.users_id
    WHERE u.id = ?
    """;
    
   private final String FIND_BY_USERNAME = """
SELECT u.id as user_id,
    u.name as user_name,
    u.username as user_username,
    u.password as user_password,
    ur.roles as user_role
    FROM users u
    LEFT JOIN users_roles ur on u.id = ur.users_id
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

    private final String INSERT_USER_ROLE = """
          INSERT INTO users_roles (users_id,roles)
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
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    //  CONCUR_READ_ONLY означает, что ResultSet может быть только прочитан.
                    ResultSet.CONCUR_READ_ONLY);
            //Интерфейс PreparedStatement содержит методы для установки строк — setString(),
            //для установки чисел — setInt(), setLong(), setDouble(), для установки дат — setDate().
            // первый параметр, определяет номер параметра номер параметра.
            //второй Значение long, которое передается, у нас айди.
            statement.setLong(1, id);
            //( далее с поомщью ExecuteQuery  выполнили запросов,при этом Возвращает объект ResultSet, который содержит все полученные данные.  2
            // Возвращаемый ResultSet никогда не является нулевым, даже если нет записей, соответствующих запросу.  5
            //а так же executeQuery Выбрасывает исключение, если запрос не возвращает ResultSet.
            try (ResultSet rs = statement.executeQuery()) {
                //метод говорит, что не может быть нулл, иначе исключение.
                // и результат в мэппер передаем, который создает объект мэппер и сеттает данные из бд в этот объект
                return Optional.ofNullable(UserRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while finding user by id.");
        }
    }
        @Override
        public Optional<User> findByUsername(String username) {
            try {
                Connection connection = dataSourceConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                statement.setString(1, username);
                try (ResultSet rs = statement.executeQuery()) {
                    return Optional.ofNullable(UserRowMapper.mapRow(rs));
                }
            } catch (SQLException throwables) {
                throw new ResourceMappingException("Exception while finding user by username.");
            }
        }

    @Override
    public void update(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while updating user.");
        }
    }

    @Override
    public void create(User user) {

        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE);
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while CREATING WORKER.");
        }
    }

    @Override
    public void insertUserRole(Long id, Role role) {
  try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, id);
            statement.setString(2,role.name());
            statement.executeUpdate();

      } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while inserting user stats.");
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
            throw new ResourceMappingException("Error while DELETING user.");
        }
    }
}
