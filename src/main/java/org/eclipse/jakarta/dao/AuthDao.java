package org.eclipse.jakarta.dao;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.jakarta.model.User;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class AuthDao {

    private static final List<User> users = new ArrayList<>();

    public boolean isValidUser(String username, String password) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }

    public User getUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
