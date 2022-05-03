package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Session> findAll(Session session) {
        List<Session> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM session")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    list.add(sessionMethod(it));
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
