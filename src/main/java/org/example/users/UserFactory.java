package org.example.users;

import org.example.users.*;

import java.util.List;
import java.util.SortedSet;

public class UserFactory<T extends Comparable<T>> {

    public User<T> createUser(AccountType accountType, Information info, int experience,
                              SortedSet<T> favorites, SortedSet<T> contributions, String username,
                              List<String> notifications) {

        if (accountType == AccountType.Regular) {
            return new Regular<>(info, experience, favorites, username, notifications);
        } else if (accountType == AccountType.Contributor) {
            return new Contributor<>(info, experience, favorites, contributions, username, notifications);
        } else if (accountType == AccountType.Admin) {
            return new Admin<>(info, contributions, username, notifications);
        }
        return null;
    }
}
