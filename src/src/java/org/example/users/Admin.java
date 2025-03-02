package org.example.users;

import org.example.*;
import org.example.productions.Production;
import org.example.requests.Request;

import java.util.*;

public class Admin<T extends Comparable<T>> extends Staff<T> {
    public Admin() {
    }

    public Admin(Information info, SortedSet<T> contributions, String username, List<String> notifications) {
        super(info, AccountType.Admin, 0, new TreeSet<>(), contributions, username, notifications);
    }

    @Override
    public void setRequests(List<Request> requests) {
        super.setRequests(requests);
    }

    @Override
    public void setContributions(SortedSet<T> contributions) {
        super.setContributions(contributions);
    }

    @Override
    public SortedSet<T> getContributions() {
        return super.getContributions();
    }

    @Override
    public List<Request> getRequests() {
        return super.getRequests();
    }

    @Override
    public void setFavorites(SortedSet<T> favorites) {
        super.setFavorites(favorites);
    }

    @Override
    public SortedSet<T> getFavorites() {
        return super.getFavorites();
    }

    @Override
    public Information getInformation() {
        return super.getInformation();
    }


    public void add_user(Information information, String username, AccountType type) {
        List<User> users = IMDB.getInstance().getUsers();
        UserFactory<T> factory = new UserFactory<>();
        User<T> new_user = factory.createUser(type, information, 0, new TreeSet<>(),
                new TreeSet<>(), username, new ArrayList<>());
        users.add(new_user);
        IMDB.getInstance().setUsers(users);
        System.out.println("User added successfully");
    }

    public void remove_user(String username) {
        if (this.username.equals(username)) {
            return;
        }
        List<User> users = IMDB.getInstance().getUsers();
        List<Request> requests = IMDB.getInstance().getRequests();
        List<Production> productions = IMDB.getInstance().getProductions();

        for (User user : users) {
            if (user.username.equals(username)) {
                if (user.getUserType() == AccountType.Contributor) {
                    Contributor<T> c = (Contributor<T>) user;
                    for (T prod_act : c.contributions) {
                        for (User u : users) {
                            if (u.getUserType() == AccountType.Admin) {
                                Admin<T> a = (Admin<T>) u;
                                a.contributions.add(prod_act);
                            }
                        }
                    }
                }
                users.remove(user);
                break;
            }
        }
        int initialsize = requests.size();
        for (Request r : requests) {
            if (r.username.equals(username)) {
                requests.remove(r);
                if (requests.size() == initialsize - 1) {
                    break;
                }
            }
        }
        for (Production p : productions) {
            double averageRating = 0;
            for (Rating r : p.ratings) {
                averageRating += r.getRating();
                if (r.getUsername().equals(username)) {
                    p.ratings.remove(r);
                    averageRating -= r.getRating();
                    break;
                }
            }
            if (!p.ratings.isEmpty()) {
                averageRating /= p.ratings.size();
                p.setAverageRating(averageRating);
            } else {
                p.setAverageRating(averageRating);
            }
        }
    }

    @Override
    public void logout() {
    }

    @Override
    public void resolve_requests() {
    }
}
