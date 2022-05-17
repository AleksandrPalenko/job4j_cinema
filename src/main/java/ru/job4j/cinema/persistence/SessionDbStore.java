package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SessionDbStore {

    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(SessionDbStore.class.getName());

    public SessionDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Session sessionMethod(ResultSet rs) throws SQLException {
        return new Session(
                rs.getInt("id"),
                rs.getString("name")
        );
    }

    public Session add(Session session) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO sessions(id, name) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, session.getId());
            ps.setString(2, session.getName());

            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    session.setId(id.getInt(1));
                }
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getMessage(), throwables);
        }
        return session;
    }

    public List<Session> findAll() {
        List<Session> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM sessions",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(
                            new Session(
                                    it.getInt("id"),
                                    it.getString("name")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return users;
    }

    public void update(Session session) {
        try (
                Connection cn = pool.getConnection();
                PreparedStatement ps = cn.prepareStatement("Update sessions SET name where id = '?'",
                        PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, session.getName());
            ps.setInt(2, session.getId());
            ps.execute();
        } catch (SQLException throwables) {
            LOG.error(throwables.getMessage(), throwables);
        }
    }

    public Session findByIdSession(int id) {
        Session sessionId = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement pr = cn.prepareStatement("Select * from sessions where id = '?'")
        ) {
            pr.setInt(1, id);
            try (ResultSet it = pr.executeQuery()) {
                while (it.next()) {
                    sessionId = sessionMethod(it);
                }
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getMessage(), throwables);
        }
        return sessionId;
    }
}
