package ru.job4j.cinema.persistence;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Repository
public class TicketDbStore {

    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(TicketDbStore.class.getName());

    public TicketDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Ticket ticketMethod(ResultSet rs) throws SQLException {
        return new Ticket(
                rs.getInt("id"),
                new Session(
                        rs.getInt("id"),
                        rs.getString("name")
                ),
                rs.getInt("line"),
                rs.getInt("cell"),
                new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("userName"),
                        rs.getString("phone"))
        );
    }

    public List<Ticket> findAll() {
        List<Ticket> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM ticket")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    list.add(ticketMethod(it));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getMessage(), throwables);
        }

        return list;
    }

    public Optional<Ticket> add(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO ticket(session_id, line, cell, user_id) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getId());
            ps.setInt(2, ticket.getId());
            ps.setInt(3, ticket.getId());
            ps.setInt(4, ticket.getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getMessage(), throwables);
        }
        return Optional.of(ticket);
    }

    public List<Ticket> findBySessionId(Session sessionId) {
        List<Ticket> listOfSessionId = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from ticket where session_id = '?'")
        ) {
            ps.setInt(1, sessionId.getId());
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    listOfSessionId.add(ticketMethod(it));
                }
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getMessage(), throwables);
        }
        return listOfSessionId;
    }

}
