package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.actors.Actor;
import org.example.actors.Performance;
import org.example.productions.Movie;
import org.example.productions.Production;
import org.example.productions.Series;
import org.example.requests.Request;
import org.example.requests.RequestType;
import org.example.requests.RequestsHolder;
import org.example.users.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ParseInput<T extends Comparable<T>> {
    private List<User<T>> users;
    private List<Actor> actors;
    private List<Request> requests;
    private List<Production> productions;

    public ParseInput() {
        users = new ArrayList<>();
        actors = new ArrayList<>();
        requests = new ArrayList<>();
        productions = new ArrayList<>();
    }

    public List<Request> requestsList() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode obj = objectMapper.readTree(new FileReader("src/main/resources/input/requests.json"));

            for (JsonNode curr_line : obj) {

                Request request = objectMapper.readValue(curr_line.toString(), Request.class);
                requests.add(request);

            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return requests;
    }

    public List<Production> productionsList() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode obj = objectMapper.readTree(new FileReader("src/main/resources/input/production.json", StandardCharsets.UTF_8));
            for (JsonNode curr_line : obj) {
                String type = curr_line.get("type").asText();
                if (type.equals("Movie")) {
                    Movie p = objectMapper.readValue(curr_line.toString(), Movie.class);
                    productions.add(p);
                } else {
                    Series series = objectMapper.readValue(curr_line.toString(), Series.class);
                    productions.add(series);
                }
            }
            return productions;
        } catch (IOException e) {
            System.out.println("not working");
            throw new RuntimeException(e);
        }
    }

    public List<Actor> actorsList() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/input/actors.json", StandardCharsets.UTF_8));
            JSONArray arr = (JSONArray) obj;
            for (Object current : arr) {
                JSONObject actor = (JSONObject) current;
                String name = (String) actor.get("name");
                String biography = (String) actor.get("biography");
                JSONArray perf = (JSONArray) actor.get("performances");
                List<Performance> performances = new ArrayList<>();
                for (Object perf_iterator : perf) {
                    JSONObject curr_perf = (JSONObject) perf_iterator;
                    String title = curr_perf.get("title").toString();
                    String type = curr_perf.get("type").toString();
                    performances.add(new Performance(title, type));
                }
                Actor a = new Actor(name, biography, performances);
                actors.add(a);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return actors;
    }

    public List<User<T>> usersList() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode obj = objectMapper.readTree(new FileReader("src/main/resources/input/accounts.json"));
            for (JsonNode curr : obj) {
                String username = curr.get("username").asText();
                Long experience_obj = curr.get("experience").asLong();
                int experience;

                experience = experience_obj.intValue();


                String user_t = curr.get("userType").asText();
                AccountType userType = AccountType.valueOf(user_t);
                JsonNode information_json = curr.get("information");
                Information info = objectMapper.readValue(information_json.toString(), Information.class);

                JsonNode notifications_arr = curr.get("notifications");
                List<String> notifications = new ArrayList<>();
                if (notifications_arr != null) {
                    for (JsonNode notif : notifications_arr) {
                        String notification = notif.asText();
                        notifications.add(notification);
                    }
                }

                SortedSet<T> favorites = new TreeSet<>();
                if (userType != AccountType.Admin) {
                    JsonNode fav_prod = curr.get("favoriteProductions");
                    JsonNode fav_act = curr.get("favoriteActors");

                    buildSortedSet(favorites, fav_prod, 1);
                    buildSortedSet(favorites, fav_act, 0);
                }
                SortedSet<T> contributions = new TreeSet<>();
                if (userType != AccountType.Regular) {
                    JsonNode cont_prod = curr.get("productionsContribution");
                    JsonNode cont_act = curr.get("actorsContribution");

                    buildSortedSet(contributions, cont_prod, 1);
                    buildSortedSet(contributions, cont_act, 0);
                }
                UserFactory<T> factory = new UserFactory<>();
                User<T> new_user = factory.createUser(userType, info, experience, favorites, contributions, username,
                        notifications);
                users.add(new_user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void parse_requests() {
        List<Request> requests = IMDB.getInstance().getRequests();
        List<User<T>> users = IMDB.getInstance().getUsers();
        for (Request r : requests) {
            if (r.getType() == RequestType.DELETE_ACCOUNT || r.getType() == RequestType.OTHERS) {
                r.to = "ADMIN";
                for (User<T> user : users) {
                    if (user.getUserType() == AccountType.Admin) {
                        r.addObserver(user);
                    }
                }
                RequestsHolder.add_request(r);
                r.notifyObservers("A request has been created and added to the admins' common" +
                        " request list.");
            } else {
                for (User<T> user : users) {
                    if (r.to.equals(user.getUsername()) && user.getUserType() != AccountType.Regular) {
                        ((Staff<T>) user).getRequests().add(r);
                        r.addObserver(user);
                        break;
                    }
                }
                r.notifyObservers("A request has been created and added to your request list.");
            }
        }
        IMDB.getInstance().setRequests(requests);
        IMDB.getInstance().setUsers(users);
    }
    public static Actor get_actor_byname(String name) {
        List<Actor> actors =  IMDB.getInstance().getActors();
        for (Actor a : actors) {
            if (a.name.equals(name)) {
                return a;
            }
        }
        return null;
    }

    public static Production get_production_byname(String name) {
        List<Production> productions1 = IMDB.getInstance().getProductions();
        for (Production a : productions1) {
            if (a.title.equals(name)) {
                return a;
            }
        }
        return null;
    }

    public static Request get_request_byname(String name, String to) {
        List<Request> requestList = IMDB.getInstance().getRequests();
        for (Request a : requestList) {
            if (a.username.equals(name) && a.to.equals(to)) {
                return a;
            }
        }
        return null;
    }

    public static User get_user_byname(String name) {
        List<User> userList = IMDB.getInstance().getUsers();
        for (User a : userList) {
            if (a.getUsername().equals(name)) {
                return a;
            }
        }
        return null;
    }
    public void parse_ratings() {
        List<Production> productions = IMDB.getInstance().getProductions();
        List<User<T>> users = IMDB.getInstance().getUsers();

        for (User<T> u : users) {
            if (u.getUserType() != AccountType.Regular) {
                for (T contributions : ((Staff<T>) u).getContributions()) {
                    if (contributions instanceof Production) {
                        for (Rating r : ((Production) contributions).ratings) {
                            r.addObserver(u);
                        }
                    }
                }
            }
            for (T favourites : u.getFavorites()) {
                if (favourites instanceof Production) {
                    for (Rating r : ((Production) favourites).ratings) {
                        r.addObserver(u);
                    }
                }
            }
        }
        for (Production production : productions) {
            for (Rating r : production.ratings) {

            }
        }

    }

    private void buildSortedSet(SortedSet<T> favorites, JsonNode elem, int check_type) {
        if (elem != null) {
            if (check_type == 1) {
                for (JsonNode prod : elem) {
                    String prod_name = prod.asText();
                    for (Production p : productions) {
                        if (p.title.equals(prod_name)) {
                            favorites.add((T) p);
                            break;
                        }
                    }
                }
            } else {
                for (JsonNode actor : elem) {
                    String actor_name = actor.asText();
                    for (Actor a : actors) {
                        if (a.name.equals(actor_name)) {
                            favorites.add((T) a);
                            break;
                        }
                    }
                }
            }
        }
    }
}
