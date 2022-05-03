package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketDbStore {

    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(TicketDbStore.class.getName());

    public TicketDbStore(BasicDataSource pool) {
        this.pool = pool;
    }
}
