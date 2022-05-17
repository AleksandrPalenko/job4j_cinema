package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.persistence.TicketDbStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@ThreadSafe
public class TicketService {

    private final TicketDbStore store;

    private TicketService(TicketDbStore store) {
        this.store = store;
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(store.findAll());
    }

    public Optional<Ticket> add(Ticket ticket) {
        return store.add(ticket);
    }

}
