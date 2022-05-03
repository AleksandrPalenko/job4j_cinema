package ru.job4j.cinema.model;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Serializable {

    private int id;
    private Session session_id;
    private int row;
    private int cell;
    private User user_id;

    public Ticket(int id, Session session_id, int row, int cell, User user_id) {
        this.id = id;
        this.session_id = session_id;
        this.row = row;
        this.cell = cell;
        this.user_id = user_id;
    }

    public Ticket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Session getSession_id() {
        return session_id;
    }

    public void setSession_id(Session session_id) {
        this.session_id = session_id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id && row == ticket.row && cell == ticket.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row, cell);
    }

    @Override
    public String toString() {
        return "Ticket{"
                + "id=" + id
                + ", session_id=" + session_id
                + ", row=" + row
                + ", cell=" + cell
                + ", user_id=" + user_id
                + '}';
    }
}
