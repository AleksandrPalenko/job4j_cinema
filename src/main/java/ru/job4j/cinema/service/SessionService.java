package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Service
public class SessionService {

    private final Map<Integer, Ticket> tickets = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public SessionService() {
        tickets.put(1, new Ticket(1, new Session(), 1, 5, new User()));
        tickets.put(2, new Ticket(2, new Session(), 2, 4, new User()));
        tickets.put(3, new Ticket(3, new Session(), 5, 7, new User()));
        tickets.put(4, new Ticket(4, new Session(), 4, 12, new User()));
    }

    public Ticket findById(int id) {
        return tickets.get(id);
    }

    public void buy(Ticket ticket, int row, int cell) {
        if (row == 0 && cell == 0) {
            ticket.setId(id.incrementAndGet());
            tickets.putIfAbsent(ticket.getId(), ticket);
        }
    }

    public void update(Ticket ticket) {
        tickets.replace(ticket.getId(), ticket);
    }
}
