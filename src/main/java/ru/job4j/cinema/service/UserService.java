package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.persistence.UserDbStore;

import java.util.Optional;

@Service
@ThreadSafe
public class UserService {

    private final UserDbStore store;

    public UserService(UserDbStore store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public User findByEmail(String email) {
        return store.findByEmail(email);
    }

    public Optional<User> findUserByEmailAndPwd(String email, String phone) {
        User user = findByEmail(email);
        return phone.equals(user.getPhone())
                ? Optional.of(user) : Optional.empty();
    }

}
