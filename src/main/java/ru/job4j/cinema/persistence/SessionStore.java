package ru.job4j.cinema.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class SessionStore {

    private final AtomicInteger id = new AtomicInteger(4);
    private final Map<Integer, Session> sessions = new ConcurrentHashMap<>();

    public SessionStore() {
        sessions.put(1, new Session(1, "Rocky V, 1990"));
        sessions.put(2, new Session(2, "Philadelphia, 1993"));
        sessions.put(3, new Session(3, "The big Lebowski, 1998"));
    }

    public List<Session> findAll() {
        return new ArrayList<>(sessions.values());
    }

    public Session findById(int id) {
        return sessions.get(id);
    }

}
