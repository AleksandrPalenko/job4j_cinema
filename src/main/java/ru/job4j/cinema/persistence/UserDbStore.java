package ru.job4j.cinema.persistence;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ThreadSafe
@Repository
public class UserDbStore {

    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(UserDbStore.class.getName());

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(username, email, phone) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            result = Optional.of(user);
        } catch (SQLException throwables) {
            LOG.error(throwables.getMessage(), throwables);
        }
        return result;
    }

    public User findByEmail(String email) {
        User userEmail = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from users where email = '?'")
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    userEmail = new User(
                            it.getString("username"),
                            it.getString("email"),
                            it.getString("phone")
                    );
                }
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getMessage(), throwables);
        }
        return userEmail;
    }

    public User findByUserId(int id) {
        User userId = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement pr = cn.prepareStatement("Select * from users WHERE id = '?'")
        ) {
            pr.setInt(1, id);
            try (ResultSet it = pr.executeQuery()) {
                while (it.next()) {
                    userId = new User(
                            it.getString("username"),
                            it.getString("email"),
                            it.getString("phone")
                    );
                }
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getMessage(), throwables);
        }
        return userId;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    list.add(new User(
                            it.getString("username"),
                            it.getString("email"),
                            it.getString("phone")
                    ));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getMessage(), throwables);
        }

        return list;
    }

}
