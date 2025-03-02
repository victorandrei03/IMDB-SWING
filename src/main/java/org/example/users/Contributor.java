package org.example.users;

import org.example.*;
import org.example.actors.Actor;
import org.example.productions.Production;
import org.example.requests.Request;
import org.example.requests.RequestType;
import org.example.requests.RequestsHolder;
import org.example.requests.RequestsManager;
import org.example.strategy.ExperienceStrategy;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;

public class Contributor<T extends Comparable<T>> extends Staff<T> implements RequestsManager {
    private ExperienceStrategy strategy;
    public Contributor(){}
    public Contributor(Information info, int experience, SortedSet<T> favorites, SortedSet<T> contributions,
                       String username, List<String> notifications){
        super(info, AccountType.Contributor, experience, favorites, contributions, username, notifications);
    }

    @Override
    public void setNotifications(List<String> user_requests) {
        super.setNotifications(user_requests);
    }

    @Override
    public Information getInformation() {
        return super.getInformation();
    }

    @Override
    public List<String> getNotifications() {
        return super.getNotifications();
    }

    @Override
    public AccountType getUserType() {
        return super.getUserType();
    }

    @Override
    public void setExperience(int experience) {
        super.setExperience(experience);
    }

    @Override
    public int getExperience() {
        return super.getExperience();
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
    public void setUserType(AccountType userType) {
        super.setUserType(userType);
    }

    @Override
    public List<Request> getRequests() {
        return super.getRequests();
    }

    @Override
    public SortedSet<T> getContributions() {
        return super.getContributions();
    }

    @Override
    public void setContributions(SortedSet<T> contributions) {
        super.setContributions(contributions);
    }

    @Override
    public void setRequests(List<Request> requests) {
        super.setRequests(requests);
    }


    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setInformation(Information information) {
        super.setInformation(information);
    }

    public void setStrategy(ExperienceStrategy strategy) {
        this.strategy = strategy;
    }
    public int executeStrategy() {
        return strategy.calculateExperience();
    }

    @Override
    public void resolve_requests() {

    }

    @Override
    public void createRequest(int operation) {
        Scanner s = new Scanner(System.in);
        List<User<T>> users = IMDB.getInstance().getUsers();
        List<Request> requests = IMDB.getInstance().getRequests();

        String user_to = "";
        if (operation == 1 || operation == 2) {
            System.out.print("Write the username to whom the request is addressed: ");
            user_to = s.nextLine();
        }
        User<T> user_found = null;
        for (User<T> u : users) {
            if (u.username.contains(user_to) && u.userType != AccountType.Regular) {
                user_found = u;
                break;
            }
        }
        if (user_found != null) {
            if (operation == 1) {
                System.out.print("Write the name of the production: ");
                String prod_name = s.nextLine();
                int found_prod = 0;
                for (T p : ((Staff<T>) user_found).contributions) {
                    if (p instanceof Production) {
                        if (((Production) p).title.equals(prod_name)) {
                            found_prod = 1;
                            System.out.print("Write the description of the issue you" +
                                    " are encountering: ");
                            String description = s.nextLine();
                            Request r = new Request(RequestType.MOVIE_ISSUE, new Date(),
                                    null, prod_name, description,
                                    this.username, user_to);
                            r.addObserver(user_found);
                            ((Staff<T>) user_found).requests.add(r);
                            r.notifyObservers("A request has been created and" +
                                    " added to your request list.");
                            requests.add(r);
                            IMDB.getInstance().setRequests(requests);
                            System.out.println("The request has been successfully created.");
                            break;
                        }
                    }
                }
                if (found_prod == 0) {
                    System.out.println("You have entered the title of the production" +
                            "incorrectly, or the user to whom you want to address" +
                            "the request cannot modify this production because it was" +
                            "not added by him.");
                }
            } else if (operation == 2) {
                System.out.print("Write the name of the actor: ");
                String actor_name = s.nextLine();
                int found_actor = 0;
                for (T a : ((Staff<T>) user_found).contributions) {
                    if (a instanceof Actor) {
                        if (((Actor) a).name.equals(actor_name)) {
                            found_actor = 1;
                            System.out.print("Write the description of the issue you" +
                                    " are encountering: ");
                            String description = s.nextLine();
                            Request r = new Request(RequestType.ACTOR_ISSUE, new Date(),
                                    actor_name, null, description,
                                    this.username, user_to);
                            r.addObserver(user_found);
                            ((Staff<T>) user_found).requests.add(r);
                            r.notifyObservers("A request has been created and" +
                                    " added to your request list.");
                            requests.add(r);
                            IMDB.getInstance().setRequests(requests);
                            System.out.println("The request has been successfully created.");
                            break;
                        }
                    }
                }
                if (found_actor == 0) {
                    System.out.println("You have entered the name of the actor" +
                            "incorrectly, or the user to whom you want to address " +
                            "the request cannot modify this actor because it was" +
                            "not added by him.");
                }
            } else if (operation == 3 || operation == 4) {
                System.out.print("Write the description of the issue you" +
                        " are encountering: ");
                String description = s.nextLine();
                Request r;
                if (operation == 3) {
                    r = new Request(RequestType.DELETE_ACCOUNT, new Date(),
                            null, null, description,
                            this.username, "ADMIN");
                } else {
                    r = new Request(RequestType.OTHERS, new Date(),
                            null, null, description,
                            this.username, "ADMIN");
                }
                for (User<T> u : users) {
                    if (u.userType == AccountType.Admin) {
                        r.addObserver(u);
                    }
                }
                r.notifyObservers("A request has been created and" +
                        " added to the common list of the admins.");
                RequestsHolder.add_request(r);
                requests.add(r);
                IMDB.getInstance().setRequests(requests);
                System.out.println("The request has been successfully created.");
            }
        } else {
            System.out.println("User not in system");
        }
    }

    @Override
    public void removeRequest(Request r) {
        List<Request> requests = IMDB.getInstance().getRequests();
        List<User<T>> users = IMDB.getInstance().getUsers();

        int found_user = 0;
        for (User<T> u : users) {
            if (u.username.equals(r.to) && u.userType != AccountType.Regular) {
                found_user = 1;
                if (r.getType() == RequestType.ACTOR_ISSUE ||
                        r.getType() == RequestType.MOVIE_ISSUE) {
                    ((Staff<T>) u).requests.remove(r);
                } else if (u.userType == AccountType.Admin) {
                    RequestsHolder.remove_req(r);
                }
                r.notifyObservers("The request from user " + this.username
                        + " has been deleted from the system.");
                requests.remove(r);
                IMDB.getInstance().setRequests(requests);
                System.out.println("Request deleted with success");
                break;
            }
        }
        if (found_user == 0) {
            System.out.println("User not found!");
        }
    }

    @Override
    public void logout() {

    }
}
