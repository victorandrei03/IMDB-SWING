package org.example;

import org.example.actors.Actor;
import org.example.actors.Performance;
import org.example.observer.Observer;
import org.example.productions.*;
import org.example.requests.Request;
import org.example.requests.RequestsHolder;
import org.example.strategy.AddReview;
import org.example.users.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Terminal<T extends Comparable<T>> {

    public Terminal() {
    }

    public void login() throws ParseException {
        List<User<T>> users = IMDB.getInstance().getUsers();
        List<Production> productions = IMDB.getInstance().getProductions();
        List<Actor> actors = IMDB.getInstance().getActors();
        List<Request> requests = IMDB.getInstance().getRequests();

        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("1) Login");
            System.out.println("2) Exit");
            System.out.print("Choose operation: ");
            int login_attempt = 0;
            String login_attempt_str = s.nextLine();
            try {
                login_attempt = Integer.parseInt(login_attempt_str);
            }
            catch (NumberFormatException exception) {
                System.out.println("The command should be a number");
            }

            if (login_attempt == 2) {
                return;
            }
            System.out.print("      email: ");
            String email = s.nextLine();

            System.out.print("      password: ");
            String password = s.nextLine();


            int found = 0;
            for (User<T> user : users) {
                if (user.getInformation().getCredentials().getEmail().equals(email)) {
                    if (user.getInformation().getCredentials().getPassword().equals(password)) {
                        found = 1;
                        int register_complet = 0;
                        while (true) {
                            user.display_common_commands(register_complet);
                            register_complet++;
                            if (user.getUserType() == AccountType.Regular) {

                                System.out.println("        6) Create/Remove request");
                                System.out.println("        7) Add/Remove rating to/from a production");
                                System.out.println("        8) Logout");
                            } else {
                                ((Staff<T>) user).display_common_terminal_staff();
                            }
                            if (user.getUserType() == AccountType.Admin) {
                                System.out.println("        10) Add/Delete user");
                                System.out.println("        11) Logout");
                            } else if (user.getUserType() == AccountType.Contributor) {
                                System.out.println("        10) Create/Remove request");
                                System.out.println("        11) Logout");
                            }
                            System.out.print("Type operation number: ");

                            int command = 0;
                            String d = s.nextLine();
                            try {
                                command = Integer.parseInt(d);
                            }
                            catch (NumberFormatException exception) {
                                System.out.println("The command should be a number");
                            }

                            if (command == 8 && user.getUserType() == AccountType.Regular) {
                                break;
                            } else if (command == 11) {
                                break;
                            }

                            if (command == 1) {
                                System.out.println("Choose how you want to display the information's of productions: ");
                                System.out.println("        1) By name");
                                System.out.println("        2) By gender");
                                System.out.println("        3) By number of ratings");
                                System.out.print("Type operation number: ");
                                int operation = 0;
                                String operation_str = s.nextLine();
                                try {
                                    operation = Integer.parseInt(operation_str);
                                }
                                catch (NumberFormatException exception) {
                                    System.out.println("The command should be a number");
                                }
                                if (operation == 1){
                                    for (Production p : productions){
                                        System.out.print(p);
                                    }
                                }
                                else if (operation == 2){
                                    System.out.print("Choose genre: ");
                                    String g = s.nextLine();
                                    int operation_done = 0;
                                    for (Genre genre : Genre.values()){
                                        if (genre.name().equals(g)) {
                                            operation_done = 1;
                                            for (Production p : productions){
                                                for (Genre genre_prod : p.getGenres()){
                                                    if (genre_prod.equals(genre)){
                                                        System.out.println(p);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (operation_done == 0){
                                        System.out.println("This type of genre is not in this system");
                                    }
                                }
                                else if (operation == 3) {
                                    System.out.print("Choose how many ratings should have the productions you want to see: ");
                                    int rating_number = 0;
                                    String rating_number_str = s.nextLine();
                                    try {
                                        rating_number = Integer.parseInt(rating_number_str);
                                    }
                                    catch (NumberFormatException exception) {
                                        System.out.println("The command should be a number");
                                    }
                                    int operation_done = 0;
                                    for (Production p : productions){
                                        if (p.ratings.size() == rating_number){
                                            operation_done = 1;
                                            System.out.print(p);
                                        }
                                    }
                                    if (operation_done == 0){
                                        System.out.println("There is no production with this number of ratings");
                                    }
                                }
                            } else if (command == 2) {
                                user.view_actors();
                            } else if (command == 3) {
                                System.out.println("        1) View notifications");
                                System.out.println("        2) Clear notifications");
                                System.out.print("Type operation number: ");
                                int operation = 0;
                                String operation_str = s.nextLine();
                                try {
                                    operation = Integer.parseInt(operation_str);
                                }
                                catch (NumberFormatException exception) {
                                    System.out.println("The command should be a number");
                                }
                                if (operation == 1) {
                                    user.view_notifications();
                                } else if (operation == 2) {
                                    user.clear_notifications();
                                }
                            } else if (command == 4) {
                                System.out.println("What are you searching for?");
                                System.out.println("        1) Actor");
                                System.out.println("        2) Movie");
                                System.out.println("        3) Series");
                                System.out.print("Type category number: ");
                                int search = 0;
                                String search_str = s.nextLine();
                                try {
                                    search = Integer.parseInt(search_str);
                                }
                                catch (NumberFormatException exception) {
                                    System.out.println("The command should be a number");
                                }
                                if (search == 1) {
                                    System.out.print("Type name of actor: ");
                                    System.out.println();
                                    String actor_name = s.nextLine();
                                    int found_a = 0;
                                    for (Actor a : actors) {
                                        if (a.name.equals(actor_name)) {
                                            System.out.println("Successful search");
                                            System.out.print(a);
                                            found_a = 1;
                                            break;
                                        }
                                    }
                                    if (found_a == 0) {
                                        System.out.println("Actor not in system");
                                    }
                                } else if (search == 2 || search == 3) {
                                    if (search == 2) {
                                        System.out.print("Type name of movie: ");
                                    } else {
                                        System.out.print("Type name of series: ");
                                    }
                                    String prod_title = s.nextLine();
                                    int found_p = 0;
                                    for (Production p : productions) {
                                        if (p.title.equals(prod_title)) {
                                            System.out.println("Successful search");
                                            System.out.println("Choose operation:");
                                            System.out.print(p);
                                            found_p = 1;
                                            break;
                                        }
                                    }
                                    if (found_p == 0 && search == 2) {
                                        System.out.println("Movie not in system");
                                    } else if (found_p == 0) {
                                        System.out.println("Series not in system");
                                    }
                                }
                            } else if (command == 5) {
                                System.out.println("Choose operation:");
                                System.out.println("        1) Add");
                                System.out.println("        2) Remove");
                                System.out.print("Type operation number: ");
                                int operation = 0;
                                String operation_str = s.nextLine();
                                try {
                                    operation = Integer.parseInt(operation_str);
                                }
                                catch (NumberFormatException exception) {
                                    System.out.println("The command should be a number");
                                }

                                System.out.println("What are you searching for?");
                                System.out.println("        1) Actor");
                                System.out.println("        2) Production");
                                System.out.print("Type category number: ");
                                int obj_type = 0;
                                String obj_type_str = s.nextLine();
                                try {
                                    obj_type = Integer.parseInt(obj_type_str);
                                }
                                catch (NumberFormatException exception) {
                                    System.out.println("The command should be a number");
                                }

                                if (obj_type == 1) {
                                    int i = 1;
                                    if (user.getFavorites() != null) {
                                        for (T act : user.getFavorites()) {
                                            if (act instanceof Actor) {
                                                System.out.println(i + ") " + ((Actor) act).name);
                                                i++;
                                            }
                                        }
                                    }
                                    System.out.println("This is your current list of favorite actors.\n");
                                }
                                else if (obj_type == 2) {
                                    int i = 1;
                                    for (T prod : user.getFavorites()) {
                                        if (prod instanceof Production) {
                                            System.out.println(i + ") " + ((Production)prod).title);
                                            i++;
                                        }
                                    }
                                    System.out.println("This is your current list of favorite productions.\n");
                                }
                                if (operation == 1 && obj_type == 2){
                                    System.out.print("Type the title of the production you want to add in your" +
                                            " favorite list: ");
                                    String title = s.nextLine();
                                    user.add_element(obj_type, title);
                                }
                                else if (operation == 1 && obj_type == 1){
                                    System.out.print("Type the name of the actor you want to add in your" +
                                            " favorite list: ");
                                    String title = s.nextLine();
                                    user.add_element(obj_type, title);
                                }
                                else if (operation == 2 && obj_type == 1) {
                                    System.out.print("What do you want to remove from your favorite list? Type name: ");
                                    String title = s.nextLine();
                                    user.remove_element(title);
                                }
                                else if (operation == 2 && obj_type == 2){
                                    System.out.print("What do you want to remove from your favorite list? Type name: ");
                                    String title = s.nextLine();
                                    user.remove_element(title);
                                }

                            } else if (command == 6 && user.getUserType() != AccountType.Regular) {
                                //Resolve requests
                                while (true) {
                                    System.out.println("Choose operation");
                                    System.out.println("        1) Solve a request");
                                    System.out.println("        2) Display requests");
                                    System.out.println("        3) Exit menu of requests");
                                    System.out.print("Type operation number: ");
                                    int operation = 0;
                                    String operation_str = s.nextLine();
                                    try {
                                        operation = Integer.parseInt(operation_str);
                                    }
                                    catch (NumberFormatException exception) {
                                        System.out.println("The command should be a number");
                                    }
                                    if (operation == 3) {
                                        break;
                                    }
                                    if (operation == 1) {
                                        while (true) {
                                            int list_index = -1;
                                            if (user.getUserType() == AccountType.Admin) {
                                                System.out.println("Choose list:");
                                                System.out.println("        1) The common request list for all admins");
                                                System.out.println("        2) Your list of requests");
                                                System.out.println("        3) Back to the menu of requests");
                                                System.out.print("Type operation number: ");
                                                String list_index_str = s.nextLine();
                                                try {
                                                    list_index = Integer.parseInt(list_index_str);
                                                }
                                                catch (NumberFormatException exception) {
                                                    System.out.println("The command should be a number");
                                                }
                                                if (list_index == 3) {
                                                    break;
                                                }
                                            }
                                            System.out.println("Choose operation");
                                            System.out.println("        1) Mark solved request");
                                            System.out.println("        2) Reject request");
                                            System.out.println("        3) Back to the menu of requests");
                                            System.out.print("Type operation number: ");
                                            int op_num = 0;
                                            String op_num_str = s.nextLine();
                                            try {
                                                op_num = Integer.parseInt(op_num_str);
                                            }
                                            catch (NumberFormatException exception) {
                                                System.out.println("The command should be a number");
                                            }
                                            if (op_num == 3) {
                                                break;
                                            }
                                            if (op_num == 1) {
                                                System.out.println("        1) Yes");
                                                System.out.println("        2) Not yet");
                                                System.out.print("Has the request assigned to you been resolved? " +
                                                        "Write operation number: ");
                                                int resolved = 0;
                                                String resolved_str = s.nextLine();
                                                try {
                                                    resolved = Integer.parseInt(resolved_str);
                                                }
                                                catch (NumberFormatException exception) {
                                                    System.out.println("The command should be a number");
                                                }
                                                if (resolved == 2) {
                                                    break;
                                                }
                                            }
                                            if (list_index == 2 || user.getUserType() == AccountType.Contributor) {
                                                ((Staff<T>) user).resolve_requests(op_num, s,
                                                        ((Staff<T>) user).getRequests());

                                            } else if (list_index == 1) {
                                                ((Staff<T>) user).resolve_requests(op_num, s,
                                                        RequestsHolder.requests);
                                            }
                                        }
                                    } else if (operation == 2) {
                                        int list_index = -1;
                                        if (user.getUserType() == AccountType.Admin) {
                                            System.out.println("Choose list:");
                                            System.out.println("        1) The common request list for all admins");
                                            System.out.println("        2) Your list of requests");
                                            System.out.print("Type operation number: ");
                                            list_index = 0;
                                            String list_index_str = s.nextLine();
                                            try {
                                                list_index = Integer.parseInt(list_index_str);
                                            }
                                            catch (NumberFormatException exception) {
                                                System.out.println("The command should be a number");
                                            }
                                        }
                                        if (list_index == 2 || user.getUserType() == AccountType.Contributor) {
                                            int i = 1;
                                            for (Request r : ((Staff<T>) user).getRequests()) {
                                                System.out.println(i + ")");
                                                System.out.println(r);
                                                i++;
                                            }
                                        } else if (list_index == 1) {
                                            int i = 1;
                                            for (Request r : RequestsHolder.requests) {
                                                System.out.println(i + ")");
                                                System.out.println(r);
                                                i++;
                                            }
                                        }
                                    }
                                }
                            } else if (command == 7 && user.getUserType() != AccountType.Regular) {
                                System.out.println("Choose operation:");
                                System.out.println("        1) Add");
                                System.out.println("        2) Remove");
                                System.out.print("Type operation number: ");
                                int operation = 0;
                                String operation_str = s.nextLine();
                                try {
                                    operation = Integer.parseInt(operation_str);
                                }
                                catch (NumberFormatException exception) {
                                    System.out.println("The command should be a number");
                                }

                                System.out.println("Choose category:");
                                System.out.println("        1) Actor");
                                System.out.println("        2) Movie");
                                System.out.println("        3) Series");
                                System.out.print("Type category number: ");
                                int item = 0;
                                String item_str = s.nextLine();
                                try {
                                    item = Integer.parseInt(item_str);
                                }
                                catch (NumberFormatException exception) {
                                    System.out.println("The command should be a number");
                                }
                                String type = "";
                                if (item == 2){
                                    type = "Movie";
                                }
                                else if (item == 3){
                                    type = "Series";
                                }
                                if (operation == 1){
                                    if (item == 1) {
                                        System.out.print("Type the name of the actor: ");
                                        String name = s.nextLine();
                                        System.out.print("Type the number of performances this actor has: ");
                                        int performances_number = 0;
                                        String perforrmances_number_str = s.nextLine();
                                        try {
                                            performances_number = Integer.parseInt(perforrmances_number_str);
                                        }
                                        catch (NumberFormatException exception) {
                                            System.out.println("The command should be a number");
                                        }
                                        List<Performance> performances = new ArrayList<>();
                                        if (performances_number > 0) {
                                            for (int i = 0; i < performances_number; i++) {
                                                System.out.println("Write the title of this performance: ");
                                                String title_p = s.nextLine();
                                                System.out.println("Write the type(movie/series) of this performance: ");
                                                String type_p = s.nextLine();
                                                Performance p = new Performance(title_p, type_p);
                                                performances.add(p);
                                            }
                                        }
                                        System.out.println("Write his biography: ");
                                        String biography = s.nextLine();
                                        Actor actor = new Actor(name, biography, performances);
                                        ((Staff<T>)user).addActorSystem((T) actor);
                                    }
                                    else if (item == 2 || item == 3){
                                        System.out.print("Write the title of the production you want to add: ");
                                        String title = s.nextLine();
                                        System.out.print("Write the number of directors the production has: ");
                                        int directors_number = 0;
                                        String directors_number_str = s.nextLine();
                                        try {
                                            directors_number = Integer.parseInt(directors_number_str);
                                        }
                                        catch (NumberFormatException exception) {
                                            System.out.println("The command should be a number");
                                        }
                                        List<String> directors = new ArrayList<>();
                                        if (directors_number > 0) {
                                            for (int i = 0; i < directors_number; i++) {
                                                System.out.print("Write the director's name: ");
                                                String name = s.nextLine();
                                                directors.add(name);
                                            }
                                        }
                                        System.out.print("Write the number of actors the production has: ");
                                        int actors_number = 0;
                                        String actors_number_str = s.nextLine();
                                        try {
                                            actors_number = Integer.parseInt(actors_number_str);
                                        }
                                        catch (NumberFormatException exception) {
                                            System.out.println("The command should be a number");
                                        }
                                        List<String> actors_list = new ArrayList<>();
                                        if (actors_number > 0) {
                                            for (int i = 0; i < actors_number; i++) {
                                                System.out.print("Write the actor's name: ");
                                                String name = s.nextLine();
                                                actors_list.add(name);
                                            }
                                        }

                                        System.out.print("Write the number of genres the production has: ");
                                        int genres_number = 0;
                                        String genres_number_str = s.nextLine();
                                        try {
                                            genres_number = Integer.parseInt(genres_number_str);
                                        }
                                        catch (NumberFormatException exception) {
                                            System.out.println("The command should be a number");
                                        }
                                        List<Genre> genres_list = new ArrayList<>();
                                        if (genres_number > 0) {
                                            for (int i = 0; i < genres_number; i++) {
                                                System.out.print("Write the production's genre: ");
                                                String genre = s.nextLine();
                                                int found_g = 0;
                                                for (Genre g : Genre.values()) {
                                                    if (g.name().equals(genre)) {
                                                        genres_list.add(g);
                                                        found_g = 1;
                                                    }
                                                }
                                                if (found_g == 0){
                                                    System.out.println("This genre is not included in the" +
                                                            " system!");
                                                }
                                            }
                                        }
                                        System.out.print("Describe what happens in this production: ");
                                        String plot = s.nextLine();
                                        if (item == 2){
                                            System.out.print("Write the release year of the movie: ");
                                            Long releaseYear = s.nextLong();
                                            s.nextLine();
                                            System.out.print("Write the duration of the movie in minutes: ");
                                            String duration = s.nextLine();
                                            Movie m = new Movie(releaseYear, duration, title, genres_list, directors,
                                                    actors_list, new ArrayList<>(), plot, null, type);
                                            ((Staff<T>)user).addProductionSystem((T) m);
                                        }
                                        else {
                                            System.out.print("Write the release year of the series: ");
                                            Long releaseYear = s.nextLong();
                                            s.nextLine();
                                            System.out.print("Write the number of the seasons the series has: ");
                                            int numSeasons = 0;
                                            String numSeasons_str = s.nextLine();
                                            try {
                                                numSeasons = Integer.parseInt(numSeasons_str);
                                            }
                                            catch (NumberFormatException exception) {
                                                System.out.println("The command should be a number");
                                            }

                                            Map<String, List<Episode>> seasons = new TreeMap<>();
                                            for (int i = 0; i < numSeasons; i++){
                                                System.out.println("Season " + (i + 1));
                                                String season_name = "Season " + (i + 1);
                                                List<Episode> episodes = new ArrayList<>();
                                                while (true){
                                                    System.out.println("Choose operation: ");
                                                    System.out.println("        1) Add episode");
                                                    System.out.println("        2) Stop adding episodes");
                                                    System.out.print("Type operation number: ");
                                                    int operation_episode = 0;
                                                    String operation_episode_str = s.nextLine();
                                                    try {
                                                        operation_episode = Integer.parseInt(operation_episode_str);
                                                    }
                                                    catch (NumberFormatException exception) {
                                                        System.out.println("The command should be a number");
                                                    }
                                                    if (operation_episode == 2) {
                                                        break;
                                                    }
                                                    System.out.print("Write the name of the episode: ");
                                                    String ep_name = s.nextLine();
                                                    System.out.print("Write the duration of the episode in minutes: ");
                                                    String duration = s.nextLine();
                                                    Episode episode = new Episode(ep_name, duration);
                                                    System.out.println("Episode added successfully");
                                                    episodes.add(episode);
                                                }
                                                seasons.put(season_name, episodes);
                                            }
                                            Series series = new Series(numSeasons, releaseYear, seasons, title, genres_list,
                                                    directors, actors_list, new ArrayList<>(), plot, null, type);
                                            ((Staff<T>)user).addProductionSystem((T) series);
                                        }

                                    }
                                }
                                if (operation == 2) {
                                    if (item == 1) {
                                        System.out.println("The actors added to the system by you");
                                        int i = 0;
                                        for (T actor : ((Staff<T>)user).getContributions()) {
                                            if (actor instanceof Actor) {
                                                i++;
                                                System.out.println(i + ") " + ((Actor)actor).name);
                                            }
                                        }
                                        System.out.print("Write the name of the actor you want to remove: ");
                                        String name = s.nextLine();
                                        ((Staff<T>) user).removeActorSystem(name);
                                    } else if (item == 2 || item == 3) {
                                        System.out.println("The productions added to the system by you");
                                        int i = 0;
                                        for (T prod : ((Staff<T>)user).getContributions()) {
                                            if (prod instanceof Production) {
                                                i++;
                                                System.out.println(i + ") " + ((Production)prod).title);
                                            }
                                        }
                                        System.out.print("Write the title of the production you want to remove: ");
                                        String title = s.nextLine();
                                        ((Staff<T>) user).removeProductionSystem(title);
                                    }
                                }
                            } else if (command == 8) {
                                System.out.println("Choose the production you want to update: ");
                                int count = 1;
                                for (T p : ((Staff<T>) user).getContributions()) {
                                    if (p instanceof Production) {
                                        System.out.println(count + ") " + ((Production) p).title);
                                        count++;
                                    }
                                }
                                System.out.print("Write the production number you want to update: ");
                                int film_number = 0;
                                String film_number_str = s.nextLine();
                                try {
                                    film_number = Integer.parseInt(film_number_str);
                                }
                                catch (NumberFormatException exception) {
                                    System.out.println("The command should be a number");
                                }

                                count = 1;
                                Production production = null;
                                for (T p : ((Staff<T>) user).getContributions()) {
                                    if (p instanceof Production) {
                                        if (count == film_number) {
                                            production = (Production) p;
                                            break;
                                        }
                                        count++;
                                    }
                                }
                                ((Staff<T>) user).updateProduction((T) production);
                            } else if (command == 9) {
                                System.out.println("Choose the actor you want to update: ");
                                int count = 1;
                                for (T a : ((Staff<T>) user).getContributions()) {
                                    if (a instanceof Actor) {
                                        System.out.println(count + ") " + ((Actor) a).name);
                                        count++;
                                    }
                                }
                                System.out.print("Write the name of the actor you want to update: ");
                                String actor_name = s.nextLine();

                                Actor actor = null;
                                for (T a : ((Staff<T>) user).getContributions()) {
                                    if (a instanceof Actor) {
                                        if (((Actor) a).name.equals(actor_name)) {
                                            actor = (Actor) a;
                                            break;
                                        }
                                    }
                                }
                                ((Staff<T>) user).updateActor((T) actor);
                            } else if (command == 10 && user.getUserType() == AccountType.Admin) {
                                System.out.println("Choose operation");
                                System.out.println("        1) Add");
                                System.out.println("        2) Remove");
                                System.out.print("Type operation number: ");
                                int operation = 0;
                                String operation_str = s.nextLine();
                                try {
                                    operation = Integer.parseInt(operation_str);
                                }
                                catch (NumberFormatException exception) {
                                    System.out.println("The command should be a number");
                                }

                                if (operation == 1) {
                                    System.out.print("Set your email: ");
                                    String email_user = s.nextLine();
                                    System.out.print("Set your password: ");
                                    String pass = s.nextLine();
                                    System.out.print("Username: ");
                                    String username = s.nextLine();
                                    System.out.print("Name: ");
                                    String name = s.nextLine();
                                    System.out.print("Country: ");
                                    String coun = s.nextLine();
                                    System.out.print("Age: ");
                                    int age = 0;
                                    String age_str = s.nextLine();
                                    try {
                                        age = Integer.parseInt(age_str);
                                    }
                                    catch (NumberFormatException exception) {
                                        System.out.println("The command should be a number");
                                    }
                                    System.out.print("Gender: ");
                                    String gen = s.nextLine();
                                    System.out.print("Birthdate (format yyyy-MM-dd): ");
                                    String birth = s.nextLine();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date birthDate = dateFormat.parse(birth);
                                    Credentials c = new Credentials(email_user, pass);
                                    System.out.print("AccountType: ");
                                    String acctype = s.nextLine();
                                    AccountType accountType = AccountType.valueOf(acctype);


                                    Information info = new Information.InformationBuilder()
                                            .credentials(c)
                                            .name(name)
                                            .country(coun)
                                            .age(age)
                                            .gender(gen)
                                            .birthDate(birthDate)
                                            .build();
                                    Admin<T> admin = (Admin) user;
                                    admin.add_user(info, username, accountType);
                                } else if (operation == 2) {
                                    Admin<T> admin = (Admin) user;
                                    int count = 1;
                                    for (User<T> u : users) {
                                        System.out.println("    " + count + ") " + u.getUsername());
                                        count++;
                                    }
                                    System.out.println("Write the username of the user you want to remove: ");
                                    String username = s.nextLine();

                                    admin.remove_user(username);
                                    if (user.getUsername().equals(username)) {
                                        System.out.println("You can't remove yourself from system!");
                                        return;
                                    }
                                    System.out.println("User removed from system");
                                }
                            } else if ((command == 10 && user.getUserType() == AccountType.Contributor) ||
                                    (command == 6 && user.getUserType() == AccountType.Regular)) {
                                System.out.println("Choose operation:");
                                System.out.println("        1) Create request");
                                System.out.println("        2) Remove request");
                                System.out.print("Write the number of the operation: ");
                                int operation = 0;
                                String operation_str = s.nextLine();
                                try {
                                    operation = Integer.parseInt(operation_str);
                                }
                                catch (NumberFormatException exception) {
                                    System.out.println("The operation should be a number");
                                }
                                if (operation == 1) {
                                    while (true) {
                                        System.out.println("Choose the type of the request you want to create:");
                                        System.out.println("        1) MOVIE_ISSUE");
                                        System.out.println("        2) ACTOR_ISSUE");
                                        System.out.println("        3) DELETE_ACCOUNT");
                                        System.out.println("        4) OTHERS");
                                        System.out.println("        5) Leave the menu of requests");
                                        System.out.print("Write the number representing the type of request you have " +
                                                "chosen: ");
                                        int request_type = 0;
                                        String request_Type_str = s.nextLine();
                                        try {
                                            request_type = Integer.parseInt(request_Type_str);
                                        }
                                        catch (NumberFormatException exception) {
                                            System.out.println("The command should be a number");
                                        }
                                        if (request_type == 5) {
                                            break;
                                        }
                                        if (user.getUserType() == AccountType.Contributor) {
                                            ((Contributor<T>) user).createRequest(request_type);
                                        } else if (user.getUserType() == AccountType.Regular) {
                                            ((Regular<T>) user).createRequest(request_type);
                                        }
                                    }
                                }
                                if (operation == 2) {
                                    int count = 1;
                                    for (Request r : requests) {
                                        if (r.username.equals(user.getUsername())) {
                                            System.out.println(count + ")");
                                            System.out.println(r);
                                            count++;
                                        }
                                    }
                                    if (count == 1) {
                                        System.out.println("You have no requests!");
                                    } else {
                                        System.out.print("Write the username to whom the request is addressed: ");
                                        String user_to = s.nextLine();
                                        int req_found = 0;
                                        for (Request request : requests) {
                                            if (request.to.equals(user_to) && request.username.equals(user.getUsername())) {
                                                req_found = 1;
                                                if (user.getUserType() == AccountType.Contributor) {
                                                    ((Contributor<T>) user).removeRequest(request);
                                                } else if (user.getUserType() == AccountType.Regular) {
                                                    ((Regular<T>) user).removeRequest(request);
                                                }
                                                break;
                                            }
                                        }
                                        if (req_found == 0) {
                                            System.out.println("You have not created any requests for this user.");
                                        }
                                    }
                                }
                            } else if (command == 7 && user.getUserType() == AccountType.Regular) {
                                while (true) {
                                    System.out.println("Choose operation");
                                    System.out.println("        1) Add rating");
                                    System.out.println("        2) Remove rating");
                                    System.out.println("        3) Exit rating menu");
                                    System.out.print("Type the number of operation: ");
                                    int operation = 0;
                                    String operation_str = s.nextLine();
                                    try {
                                        operation = Integer.parseInt(operation_str);
                                    }
                                    catch (NumberFormatException exception) {
                                        System.out.println("The command should be a number");
                                    }
                                    if (operation == 3) {
                                        break;
                                    }
                                    if (operation == 1) {
                                        int i = 1;
                                        for (Production p : productions) {
                                            System.out.println(i + ") " + p.title);
                                            i++;
                                        }
                                        System.out.print("Write the number associated with the desired production on " +
                                                "which you want to perform operations: ");
                                        int prod_ind = 0;
                                        String prod_ind_str = s.nextLine();
                                        try {
                                            prod_ind = Integer.parseInt(prod_ind_str);
                                        }
                                        catch (NumberFormatException exception) {
                                            System.out.println("The command should be a number");
                                        }
                                        if (prod_ind <= i && prod_ind > 0) {
                                            Production p = productions.get(prod_ind - 1);
                                            int already_rated = 0;
                                            for (Rating r : p.ratings) {
                                                if (r.username.equals(user.getUsername())) {
                                                    System.out.println("You already have rated this production, if" +
                                                            "you want to change the given rating, remove the existing" +
                                                            "one");
                                                    already_rated = 1;
                                                }
                                            }
                                            if (already_rated == 0) {
                                                System.out.print("What rating do you give to this production? Choose a" +
                                                        " whole number: ");
                                                int rating = 0;
                                                String rating_str = s.nextLine();
                                                try {
                                                    rating = Integer.parseInt(rating_str);
                                                }
                                                catch (NumberFormatException exception) {
                                                    System.out.println("The command should be a number");
                                                }
                                                System.out.print("Write a comment about this production: ");
                                                String comment = s.nextLine();
                                                Rating r = new Rating(user.getUsername(), rating, comment);
                                                Rating r2 = new Rating(user.getUsername(), rating, comment);
                                                for (User<T> u : users) {
                                                    if (u.getUserType() != AccountType.Regular) {
                                                        for (T prod : ((Staff<T>) u).getContributions()) {
                                                            if (prod instanceof Production) {
                                                                if (prod.equals(p)) {
                                                                    r2.addObserver(u);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                r2.notifyObservers("The production \"" + p.title + "\"" +
                                                        " that you added has received a review from the user " + "\"" +
                                                        user.getUsername() + "\" -> " + rating);
                                                for (User<T> u : users) {
                                                    if (u.getFavorites() != null) {
                                                        if (!u.getFavorites().isEmpty()) {
                                                            for (T prod : u.getFavorites()) {
                                                                if (prod instanceof Production) {
                                                                    if (prod.equals(p)) {
                                                                        r.addObserver(u);
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                r.notifyObservers("The production \"" + p.title + "\"" +
                                                        " from your list of favorites has received a review from the" +
                                                        " user " + "\"" + user.getUsername() + "\" -> " + rating);
                                                for (Observer o : r2.observers) {
                                                    r.addObserver(o);
                                                }
                                                ((Regular<T>)user).setStrategy(new AddReview());
                                                user.setExperience(((Regular<T>)user).executeStrategy() + user.getExperience());
                                                p.ratings.add(r);
                                                p.setAverageRating((p.getAverageRating() + rating) / p.ratings.size());
                                                System.out.println("Rating added successfully");
                                            }
                                        } else {
                                            System.out.println("Operation failed");
                                        }
                                    } else if (operation == 2) {
                                        int i = 1;
                                        for (Production p : productions) {
                                            for (Rating r : p.ratings) {
                                                if (r.username.equals(user.getUsername())) {
                                                    System.out.println(i + ") " + p.title);
                                                    i++;
                                                }
                                            }
                                        }
                                        if (i != 1) {
                                            System.out.println("These are the productions you have rated. ");
                                            System.out.print("Write the name corresponding to the production" +
                                                    " from which you want to delete the rating: ");
                                            String chosen = s.nextLine();
                                            int found_chosen = 0;
                                            for (Production p : productions) {
                                                if (p.title.equals(chosen)) {
                                                    found_chosen = 1;
                                                    for (Rating r : p.ratings) {
                                                        if (r.username.equals(user.getUsername())) {
                                                            double averageRating = 0;
                                                            for (Rating rating : p.ratings) {
                                                                averageRating += rating.rating;
                                                            }
                                                            averageRating -= r.rating;
                                                            averageRating /= (p.ratings.size() - 1);
                                                            p.setAverageRating(averageRating);
                                                            r.notifyObservers("The rating from the production " +
                                                                    p.title + " was deleted");
                                                            System.out.println("Rating removed with success");
                                                            p.ratings.remove(r);
                                                            break;
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                            if (found_chosen == 0) {
                                                System.out.println("Production not found.");
                                            }
                                        } else {
                                            System.out.println("You have not evaluated any production.");
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
            if (found == 0) {
                System.out.println("Wrong email or password! Try again!");
            }
        }

    }
}

