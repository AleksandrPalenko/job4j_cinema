package ru.job4j.cinema.model;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Serializable {

    private int id;
    private Session session_id;
    private int line;
    private int cell;
    private User user_id;

    public Ticket() {
    }

    public Ticket(int id, Session session_id, int line, int cell, User user_id) {
        this.id = id;
        this.session_id = session_id;
        this.line = line;
        this.cell = cell;
        this.user_id = user_id;
    }

    public Ticket(int id, Session session_id, int line, int cell) {
        this.id = id;
        this.session_id = session_id;
        this.line = line;
        this.cell = cell;
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

    public int getLine() {
        return line;
    }

    public void setLina(int row) {
        this.line = row;
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
        return id == ticket.id && line == ticket.line && cell == ticket.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, line, cell);
    }

    @Override
    public String toString() {
        return "Ticket{"
                + "id=" + id
                + ", session_id=" + session_id
                + ", row=" + line
                + ", cell=" + cell
                + ", user_id=" + user_id
                + '}';
    }

}
