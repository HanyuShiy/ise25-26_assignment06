package de.seuhd.campuscoffee.domain.impl;

import de.seuhd.campuscoffee.domain.model.User;
import de.seuhd.campuscoffee.domain.ports.UserDataService;
import de.seuhd.campuscoffee.domain.ports.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDataService userDataService;

    @Override
    public void clear() {
        log.debug("Clearing all users");
        userDataService.clear();
    }

    @Override
    public List<User> getAll() {
        log.debug("Fetching all users");
        return userDataService.getAll();
    }

    @Override
    public User getById(Long id) {
        log.debug("Fetching user by id: {}", id);
        return userDataService.getById(id);
    }

    @Override
    public User getByLoginName(String loginName) {
        log.debug("Fetching user by login name: {}", loginName);
        return userDataService.getByLoginName(loginName);
    }

    @Override
    public User upsert(User user) {
        log.debug("Upserting user: {}", user);
        return userDataService.upsert(user);
    }

    @Override
    public void delete(Long id) {
        log.debug("Deleting user by id: {}", id);
        userDataService.delete(id);
    }
}
