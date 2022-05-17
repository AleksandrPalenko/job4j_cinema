package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.persistence.SessionDbStore;
import ru.job4j.cinema.persistence.TicketDbStore;

import java.util.*;

@Service
@ThreadSafe
public class SessionService {

    private final SessionDbStore sessionStore;
    private final TicketDbStore ticketStore;

    private final int countOfLines = 10;
    private final int countOfCells = 20;

    public SessionService(SessionDbStore sessionStore, TicketDbStore ticketStore) {
        this.sessionStore = sessionStore;
        this.ticketStore = ticketStore;
    }

    public Optional<Ticket> buyTicket(Ticket ticket) {
        return ticketStore.add(ticket);
    }

    public Optional<Ticket> add(Ticket ticket) {
        return ticketStore.add(ticket);
    }

    public List<Integer> approveLine(Session sessionId) {
        Collection<Ticket> tickets = ticketStore.findBySessionId(sessionId);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= getFindLines().size(); i++) {
            list.add(i);
        }
        for (Ticket ticket : tickets) {
            if (approveCells(sessionId, ticket.getLine()).isEmpty()) {
                list.remove(Integer.valueOf(ticket.getLine()));
            }
        }
        return list;
    }

    public List<Integer> approveCells(Session sessionId, int row) {
        Collection<Ticket> tickets = ticketStore.findBySessionId(sessionId);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= getFindCells().size(); i++) {
            list.add(i);
        }
        for (Ticket ticket : tickets) {
            if (ticket.getLine() == row) {
                list.remove(Integer.valueOf(ticket.getCell()));
            }
        }
        return list;
    }


    public List<Session> findAll() {
        return sessionStore.findAll();
    }

    public Session findById(int id) {
        return sessionStore.findByIdSession(id);
    }

    public List<Integer> getFindLines() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < countOfLines; i++) {
            list.add(i);
        }
        return list;
    }

    public List<Integer> getFindCells() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < countOfCells; i++) {
            list.add(i);
        }
        return list;
    }

}
