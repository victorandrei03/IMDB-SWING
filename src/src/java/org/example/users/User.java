package org.example.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.*;
import org.example.actors.Actor;
import org.example.observer.Observer;
import org.example.productions.Genre;
import org.example.productions.Production;
import org.example.requests.Request;

import java.util.*;

@JsonIgnoreProperties({ "productionsContribution", "actorsContribution", "favoriteProductions", "favoriteActors" })
public abstract class User<T extends Comparable<T>> implements Observer {
    Information information;
    AccountType userType;
    int experience;
    SortedSet<T> favorites;
    List<String> notifications;
    String username;

    public User() {
        notifications = new ArrayList<>();
    }

    public User(Information information, AccountType userType, int experience, SortedSet<T> favorites, String username,
                List<String> notifications) {
        this.information = information;
        this.userType = userType;
        this.experience = experience;
        this.favorites = favorites;
        this.username = username;
        this.notifications = notifications;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserType(AccountType userType) {
        this.userType = userType;
    }

    public AccountType getUserType() {
        return userType;
    }

    public int getExperience() {
        return experience;
    }

    public SortedSet<T> getFavorites() {
        return favorites;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setFavorites(SortedSet<T> favorites) {
        this.favorites = favorites;
    }

    public Information getInformation() {return information;}

    public void setInformation(Information information) {this.information = information;}

    public List<String> getNotifications() {return notifications;}

    public void setNotifications(List<String> user_requests) {this.notifications = user_requests;}

    public void add_element(int obj_type, String name){
        int found = 0;
        List<Production> productions = IMDB.getInstance().getProductions();
        List<Actor> actors = IMDB.getInstance().getActors();
        if (obj_type == 2) {
            for (Production p : productions) {
                if (p.title.equals(name)) {
                    System.out.println("Production added to your favorite list");
                    if (favorites == null) {
                        favorites = new TreeSet<>();
                    }
                    favorites.add((T) p);
                    found = 1;
                    break;
                }
            }
            if (found == 0) {
                System.out.println("Production is not in the system");
            }
        }
        else if (obj_type == 1) {
            for (Actor a : actors){
                if (a.name.equals(name)){
                    System.out.println("Actor added to your favorite list");
                    if (favorites == null){
                        favorites = new TreeSet<>();
                    }
                    favorites.add((T) a);
                    found = 1;
                    break;
                }
            }
            if (found == 0){
                System.out.println("Actor is not in the system");
            }
        }
    }

    public void remove_element(String title){
        int found = 0;
        if (favorites != null) {
            for (T obj : favorites) {
                if (obj instanceof Actor) {
                    if (((Actor) obj).name.equals(title)) {
                        System.out.println("The operation was carried out successfully");
                        favorites.remove(obj);
                        found = 1;
                        break;
                    }
                } else if (obj instanceof Production) {
                    if (((Production) obj).title.equals(title)) {
                        System.out.println("The operation was carried out successfully");
                        favorites.remove(obj);
                        found = 1;
                        break;
                    }
                }
            }
        }
        if (found == 0){
            System.out.println("Operation failed.");
        }
    }
    public void view_actors() {
        List<Actor> actors = IMDB.getInstance().getActors();
        for (Actor a : actors) {
            System.out.println(a);
        }
    }

    public void view_notifications() {
        if (this.notifications.isEmpty()) {
            System.out.println("You have no notifications");
            return;
        }
        int i = 1;
        for (String notification : this.notifications) {
            System.out.println(i + ") " + notification);
            i++;
        }
    }

    public List<Request> get_created_request_by_user() {
        List<Request> requestCreated = new ArrayList<>();
        List<User<T>> users = IMDB.getInstance().getUsers();
        for (User<T> u : users) {
            if (u.userType != AccountType.Regular) {
                for (Request r : ((Staff<T>)u).requests) {
                    if (r.username.equals(this.username)) {
                        requestCreated.add(r);
                    }
                }
            }
        }
        return requestCreated;
    }

    public void display_common_commands(int register_complet) {
        if (register_complet == 0) {
            System.out.println("Welcome back user " + this.getUsername() + "!");
            System.out.println("Username: " + this.getUsername());
            if (this.userType == AccountType.Admin) {
                System.out.println("User experience: -");
            } else {
                System.out.println("User experience: " + this.experience);
            }
        }
        System.out.println("Choose action:");
        System.out.println("        1) View productions details");
        System.out.println("        2) View actors details");
        System.out.println("        3) View/Clear notifications");
        System.out.println("        4) Search for actor/movie/series");
        System.out.println("        5) Add/Delete actor/movie/series to/from favorites");
    }

    public void clear_notifications() {
        notifications.clear();
    }

    public void update(String notification) {
        notifications.add(notification);
    }


    public abstract void logout();
}
