package org.example.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.*;
import org.example.actors.Actor;
import org.example.actors.Performance;
import org.example.productions.*;
import org.example.requests.Request;
import org.example.strategy.AddProductionActor;
import org.example.strategy.ResolveRequest;

import java.util.*;

@JsonIgnoreProperties({"productionsContribution", "actorsContribution", "favoriteProductions", "favoriteActors"})
public abstract class Staff<T extends Comparable<T>> extends User<T> implements StaffInterface<T> {
    List<Request> requests = new ArrayList<>();
    SortedSet<T> contributions;

    public Staff() {
    }

    public Staff(Information info, AccountType type, int experience, SortedSet<T> favorites, SortedSet<T> contributions,
                 String username, List<String> notifications) {
        super(info, type, experience, favorites, username, notifications);
        this.contributions = contributions;
    }

    public void setFavorites(SortedSet<T> favorites) {
        super.setFavorites(favorites);
    }

    @Override
    public SortedSet<T> getFavorites() {
        return super.getFavorites();
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public int getExperience() {
        return super.getExperience();
    }

    @Override
    public void setExperience(int experience) {
        super.setExperience(experience);
    }

    @Override
    public AccountType getUserType() {
        return super.getUserType();
    }

    @Override
    public void setUserType(AccountType userType) {
        super.setUserType(userType);
    }

    @Override
    public List<String> getNotifications() {
        return super.getNotifications();
    }

    @Override
    public void setInformation(Information information) {
        super.setInformation(information);
    }

    @Override
    public void setNotifications(List<String> user_requests) {
        super.setNotifications(user_requests);
    }

    public SortedSet<T> getContributions() {
        return contributions;
    }

    public void setContributions(SortedSet<T> contributions) {
        this.contributions = contributions;
    }

    @Override
    public Information getInformation() {
        return super.getInformation();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    public void addProductionSystem(T p) {
        if (p instanceof Production) {
            List<Production> productions = IMDB.getInstance().getProductions();
            if (productions.contains(p)) {
                System.out.println("This production is already in the system!");
                return;
            }
            productions.add((Production) p);
            contributions.add(p);
            System.out.println("This production was added successfully in the system!");
            if (this.userType == AccountType.Contributor) {
                ((Contributor<T>)this).setStrategy(new AddProductionActor());
                this.experience += ((Contributor<T>)this).executeStrategy();
            }
        }
    }

    public void addActorSystem(T a){
        if (a instanceof Actor) {
            List<Actor> actors = IMDB.getInstance().getActors();
            if (actors.contains(a)) {
                System.out.println("This actor is already in the system!");
                return;
            }
            actors.add((Actor) a);
            contributions.add(a);
            System.out.println("This actor was added successfully in the system!");
            if (this.userType == AccountType.Contributor) {
                ((Contributor<T>)this).setStrategy(new AddProductionActor());
                this.experience += ((Contributor<T>)this).executeStrategy();
            }
        }
    }
    public void removeProductionSystem(String name){
        List<Production> productions = IMDB.getInstance().getProductions();
        for (Production p : productions) {
            if (p.title.equals(name)) {
                if (contributions.contains(p)) {
                    contributions.remove(p);
                    productions.remove(p);
                    System.out.println("This production was successfully removed from the system!");
                }
                else {
                    System.out.println("This production cannot be removed because it was added by another user!");
                }
                return;
            }
        }
    }

    public void removeActorSystem(String name){
        List<Actor> actors = IMDB.getInstance().getActors();
        for (Actor a : actors) {
            if (a.name.equals(name)) {
                if (contributions.contains(a)) {
                    contributions.remove(a);
                    actors.remove(a);
                    System.out.println("This actor was successfully removed from the system!");
                }
                else {
                    System.out.println("This actor cannot be removed because it was added by another user!");
                }
                return;
            }
        }
    }

    public void resolve_requests(int op_num, Scanner s, List<Request> requestList) {
        List<Request> requests = IMDB.getInstance().getRequests();
        List<User<T>> users = IMDB.getInstance().getUsers();
        if (op_num == 1) {
            System.out.print("Choose the number of request you have solved: ");
        } else if (op_num == 2) {
            System.out.print("Choose the number of request you want to reject: ");
        }
        int num = 0;
        String num_str = s.nextLine();
        try {
            num = Integer.parseInt(num_str);
        }
        catch (NumberFormatException exception) {
            System.out.println("The command should be a number");
        }
        if (num > 0 && num <= requestList.size()) {
            Request r = requestList.get(num - 1);
            User<T> user_found = null;
            for (User<T> u : users) {
                if (u.username.equals(r.username)) {
                    user_found = u;
                    break;
                }
            }
            if (user_found != null) {
                r.clearObservers();
                r.addObserver(user_found);
                requests.remove(r);
                requestList.remove(r);
                if (op_num == 1) {
                    System.out.println("You have successfully resolved " +
                            "the request.");
                    r.notifyObservers("User " + this.username +
                            " has solved your request");
                    if (user_found.userType == AccountType.Contributor) {
                        ((Contributor<T>)user_found).setStrategy(new ResolveRequest());
                        user_found.experience += ((Contributor<T>)user_found).executeStrategy();
                    }
                    else if (user_found.userType == AccountType.Regular) {
                        ((Regular<T>)user_found).setStrategy(new ResolveRequest());
                        user_found.experience += ((Regular<T>)user_found).executeStrategy();
                    }
                } else if (op_num == 2) {
                    System.out.println("You have rejected the assigned" +
                            " request.");
                    r.notifyObservers("User " + this.username +
                            " has rejected your request");
                }
            } else {
                System.out.println("Operation failed");
            }
        } else {
            System.out.println("There is no request with such an index");
        }
        IMDB.getInstance().setRequests(requests);
    }

    public void updateProduction(T p) {
        Production production = null;
        if (p instanceof Production){
            production = (Production) p;
        }
        Scanner s = new Scanner(System.in);
        if (production != null) {
            System.out.println("The production you have chosen is a " + production.type);
            System.out.println("Choose what you want to modify at this production: ");
            System.out.println("        1) Title");
            System.out.println("        2) Directors");
            System.out.println("        3) Actors");
            System.out.println("        4) Genres");
            System.out.println("        5) Plot");
            System.out.println("        6) Release Year");
            if (production.type.equals("Movie")) {
                System.out.println("        7) Duration");
                System.out.print("Type operation number: ");
            } else if (production.type.equals("Series")) {
                System.out.println("        7) Seasons");
                System.out.print("Type operation number: ");
            }
            int operation = 0;
            String operation_str = s.nextLine();
            try {
                operation = Integer.parseInt(operation_str);
            }
            catch (NumberFormatException exception) {
                System.out.println("The command should be a number");
            }
            if (operation == 1) {
                System.out.print("Set new title: ");
                String new_title = s.nextLine();
                production.setTitle(new_title);
                System.out.println("Successful operation");
            } else if (operation == 2) {
                while (true) {
                    System.out.println("Choose operation: ");
                    System.out.println("        1) Add director");
                    System.out.println("        2) Remove director");
                    System.out.println("        3) Stop");
                    System.out.print("Type operation number: ");
                    int operation_dir = 0;
                    String operation_dir_str = s.nextLine();
                    try {
                        operation_dir = Integer.parseInt(operation_dir_str);
                    }
                    catch (NumberFormatException exception) {
                        System.out.println("The command should be a number");
                    }
                    if (operation_dir == 3) {
                        break;
                    }
                    int count_dir = 1;
                    for (String str : production.getDirectors()) {
                        System.out.println(count_dir + ") " + str);
                        count_dir++;
                    }
                    System.out.print("Write name of director: ");
                    String dir_name = s.nextLine();
                    if (operation_dir == 1) {
                        production.getDirectors().add(dir_name);
                        System.out.println("Successful operation");
                    } else if (operation_dir == 2) {
                        if (production.getDirectors().contains(dir_name)) {
                            production.getDirectors().remove(dir_name);
                            System.out.println("Successful operation");
                        } else {
                            System.out.println("Operation failed");
                        }
                    }
                }
            } else if (operation == 3) {
                while (true) {
                    System.out.println("Choose operation: ");
                    System.out.println("        1) Add actor");
                    System.out.println("        2) Remove actor");
                    System.out.println("        3) Stop");
                    System.out.print("Type operation number: ");
                    int operation_act = 0;
                    String operation_act_str = s.nextLine();
                    try {
                        operation_act = Integer.parseInt(operation_act_str);
                    }
                    catch (NumberFormatException exception) {
                        System.out.println("The command should be a number");
                    }
                    if (operation_act == 3) {
                        break;
                    }
                    int count_act = 1;
                    for (String str : production.getActors()) {
                        System.out.println(count_act + ") " + str);
                        count_act++;
                    }
                    System.out.print("Write name of actor: ");
                    String act_name = s.nextLine();
                    if (operation_act == 1) {
                        production.getActors().add(act_name);
                        System.out.println("Successful operation");
                    } else if (operation_act == 2) {
                        if (production.getActors().contains(act_name)) {
                            production.getActors().remove(act_name);
                            System.out.println("Successful operation");
                        } else {
                            System.out.println("Operation failed");
                        }
                    }
                }
            } else if (operation == 4) {
                while (true) {
                    System.out.println("Choose operation: ");
                    System.out.println("        1) Add genre");
                    System.out.println("        2) Remove genre");
                    System.out.println("        3) Stop");
                    System.out.print("Type operation number: ");
                    int operation_gen = 0;
                    String operation_gen_str = s.nextLine();
                    try {
                        operation_gen = Integer.parseInt(operation_gen_str);
                    }
                    catch (NumberFormatException exception) {
                        System.out.println("The command should be a number");
                    }
                    if (operation_gen == 3) {
                        break;
                    }
                    int count_genre = 1;
                    for (Genre g : production.getGenres()) {
                        System.out.println(count_genre + ") " + g.name());
                        count_genre++;
                    }
                    System.out.print("Write production genre: ");
                    String gen_name = s.nextLine();
                    int found = 0;
                    for (Genre genre : Genre.values()) {
                        if (genre.name().equals(gen_name)) {
                            found = 1;
                        }
                    }
                    if (operation_gen == 1 && found == 1) {
                        production.getGenres().add(Genre.valueOf(gen_name));
                        System.out.println("Successful operation");
                    } else if (operation_gen == 2 && found == 1) {
                        production.getGenres().remove(Genre.valueOf(gen_name));
                        System.out.println("Successful operation");
                    } else if (found == 0) {
                        System.out.println("This type of genre is not in this system ");
                    }
                }
            } else if (operation == 5) {
                System.out.println("Actual plot: " + production.getPlot());
                System.out.print("Set new plot: ");
                String new_plot = s.nextLine();
                production.setPlot(new_plot);
                System.out.println("Successful operation");
            } else if (operation == 6) {
                System.out.print("Set new release year: ");
                Long releaseYear = s.nextLong();
                s.nextLine();
                if (production instanceof Movie) {
                    ((Movie) production).setReleaseYear(releaseYear);
                } else if (production instanceof Series) {
                    ((Series) production).setReleaseYear(releaseYear);
                }
                System.out.println("Successful operation");
            } else if (operation == 7 && production.type.equals("Movie")) {
                System.out.print("Set new duration time: ");
                ((Movie) production).setDuration(s.nextLine());
                System.out.println("Successful operation");
            } else if (operation == 7 && production.type.equals("Series")) {
                while (true) {
                    System.out.println("Choose operation:");
                    System.out.println("        1) Add season");
                    System.out.println("        2) Remove season");
                    System.out.println("        3) Add episode in a season");
                    System.out.println("        4) Remove episode from a season");
                    System.out.println("        5) Stop");
                    System.out.print("Type operation number: ");
                    int operation_season = 0;
                    String operation_season_str = s.nextLine();
                    try {
                        operation_season = Integer.parseInt(operation_season_str);
                    }
                    catch (NumberFormatException exception) {
                        System.out.println("The command should be a number");
                    }
                    if (operation_season == 5) {
                        break;
                    }
                    Map<String, List<Episode>> seasons = ((Series) production).getSeasons();
                    if (operation_season == 1) {
                        int number_seasons = ((Series) production).getSeasons().size();
                        number_seasons++;
                        String season_name = "Season " + number_seasons;
                        seasons.put(season_name, new ArrayList<>());
                        ((Series) production).setNumSeasons(((Series) production).getNumSeasons() + 1);
                        ((Series) production).setSeasons(seasons);
                        System.out.println(season_name + " was added successfully");
                    } else if (operation_season == 2) {
                        if (((Series) production).getNumSeasons() > 0) {
                            int count_seasons = 1;
                            for (String str : seasons.keySet()) {
                                System.out.println(count_seasons + ") " + str);
                                count_seasons++;
                            }
                            System.out.print("Write the name of the season you want to " +
                                    "remove: ");
                            String removed_seas = s.nextLine();
                            if (seasons.containsKey(removed_seas)) {
                                System.out.println("Season removed");
                                seasons.remove(removed_seas);
                                ((Series) production).setNumSeasons(((Series) production).getNumSeasons() - 1);
                                ((Series) production).setSeasons(seasons);
                            } else {
                                System.out.println("There is no season with this name");
                            }
                        } else {
                            System.out.println("Operation failed");
                        }
                    } else if (operation_season == 3) {
                        if (((Series) production).getNumSeasons() > 0) {
                            int count_seasons = 1;
                            for (String str : seasons.keySet()) {
                                System.out.println(count_seasons + ") " + str);
                                count_seasons++;
                            }
                            System.out.print("Write the name of the season you want to " +
                                    "add an episode: ");
                            String season_name = s.nextLine();
                            if (seasons.containsKey(season_name)) {
                                System.out.print("Write the name of the episode: ");
                                String epName = s.nextLine();
                                System.out.print("Write the duration of the episode: ");
                                String duration = s.nextLine();
                                Episode ep = new Episode(epName, duration);
                                seasons.get(season_name).add(ep);
                                System.out.println("Episode added successfully");
                                ((Series) production).setSeasons(seasons);
                            } else {
                                System.out.println("There is no season with this name");
                            }
                        } else {
                            System.out.println("Operation failed");
                        }
                    } else if (operation_season == 4) {
                        if (((Series) production).getNumSeasons() > 0) {
                            int count_seasons = 1;
                            for (String str : seasons.keySet()) {
                                System.out.println(count_seasons + ") " + str);
                                count_seasons++;
                            }
                            System.out.print("Write the name of the season you want to " +
                                    "remove an episode: ");
                            String season_name = s.nextLine();
                            if (seasons.containsKey(season_name)) {
                                int count_ep = 1;
                                for (Episode ep : seasons.get(season_name)) {
                                    System.out.println(count_ep + ") " + ep);
                                    count_ep++;
                                }
                                if (count_ep == 1) {
                                    System.out.println("There are no episodes in this season");
                                }
                                System.out.print("Write the name of the episode you want to " +
                                        "remove");
                                String ep_name = s.nextLine();
                                int found = 0;
                                for (Episode ep : seasons.get(season_name)) {
                                    if (ep.getEpisodeName().equals(ep_name)) {
                                        found = 1;
                                        seasons.get(season_name).remove(ep);
                                        ((Series) production).setSeasons(seasons);
                                        break;
                                    }
                                }
                                if (found == 1) {
                                    System.out.println("Episode removed successfully");
                                } else {
                                    System.out.println("There is no episode with this name");
                                }
                            } else {
                                System.out.println("There is no season with this name");
                            }
                        } else {
                            System.out.println("Operation failed");
                        }
                    }
                }
            }


        }
    }

    public void updateActor(T a) {
        Actor actor = null;
        if (a instanceof Actor) {
            actor = (Actor) a;
        }
        Scanner s = new Scanner(System.in);
        if (actor != null) {
            System.out.println("Choose what you want to modify at this actor: ");
            System.out.println("        1) Name");
            System.out.println("        2) Performances");
            System.out.println("        3) Biography");
            System.out.print("Write the number of category: ");
            int operation = 0;
            String operation_str = s.nextLine();
            try {
                operation = Integer.parseInt(operation_str);
            }
            catch (NumberFormatException exception) {
                System.out.println("The command should be a number");
            }
            if (operation == 1) {
                System.out.println("Set new name: ");
                actor.name = s.nextLine();
                System.out.println("New name set");
            } else if (operation == 2) {
                while (true) {
                    System.out.println("Choose operation: ");
                    System.out.println("        1) Add performance");
                    System.out.println("        2) Remove performance");
                    System.out.println("        3) Stop");
                    System.out.print("Type the operation number: ");
                    int operation_perf = 0;
                    String operation_perf_str = s.nextLine();
                    try {
                        operation_perf = Integer.parseInt(operation_perf_str);
                    }
                    catch (NumberFormatException exception) {
                        System.out.println("The command should be a number");
                    }
                    if (operation_perf == 3) {
                        break;
                    }
                    int count_perf = 1;
                    for (Performance p : actor.performances) {
                        System.out.println(count_perf + ") " + p);
                        count_perf++;
                    }
                    System.out.print("Write the title of the performance: ");
                    String title = s.nextLine();
                    if (operation_perf == 1) {
                        System.out.println("Write the type(Movie/Series of the performance: ");
                        String type = s.nextLine();
                        Performance performance = new Performance(title, type);
                        actor.performances.add(performance);
                        System.out.println("Performance added successfully");
                    } else if (operation_perf == 2) {
                        int found = 0;
                        for (Performance performance : actor.performances) {
                            if (performance.title.equals(title)) {
                                found = 1;
                                actor.performances.remove(performance);
                                break;
                            }
                        }
                        if (found == 0) {
                            System.out.println("Performance not found");
                        }
                    }
                }
            } else if (operation == 3) {
                System.out.println("Actual biography: " + actor.biography);
                System.out.println("Set new biography: ");
                actor.biography = s.nextLine();
                System.out.println("New biography set");
            }
        }
    }


    public abstract void logout();

    public void display_common_terminal_staff() {
        System.out.println("        6) Solve a request");
        System.out.println("        7) Add/Delete actor/movie/series from system");
        System.out.println("        8) Update production details");
        System.out.println("        9) Update actor details");
    }
}
