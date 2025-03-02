package org.example;

import org.example.actors.Actor;
import org.example.productions.Production;
import org.example.requests.Request;
import org.example.swing.LoginInterface;
import org.example.users.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.util.*;
import java.util.List;

public class IMDB<T extends Comparable<T>>{
    private static final IMDB instance = new IMDB<>();
    private List<User<T>> users;
    private List<Actor> actors;
    private List<Request> requests;

    private List<Production> productions;
    private IMDB(){ }
    public static IMDB getInstance() {
        return instance;
    }
    public List<Actor> getActors() {
        return actors;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public List<User<T>> getUsers() {
        return users;
    }

    public void setUsers(List<User<T>> users) {
        this.users = users;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public void setProductions(List<Production> productions) {
        this.productions = productions;
    }


    public void run() throws ParseException {
        ParseInput<T> parseInput = new ParseInput<>();
        actors = parseInput.actorsList();
        requests = parseInput.requestsList();
        productions = parseInput.productionsList();
        users = parseInput.usersList();

        parseInput.parse_requests();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose interface:\n1)Terminal\n2)Swing");
        System.out.println("Write operation number.");
        int chosen_interface = 0;
        String chosen_interface_str = scanner.nextLine();
        try {
            chosen_interface = Integer.parseInt(chosen_interface_str);
        }
        catch (NumberFormatException exception) {
            System.out.println("The command should be a number");
        }
        if (chosen_interface == 1){
            Terminal<T> t = new Terminal<>();
            t.login();
        }
        else if (chosen_interface == 2){
            Map<String, ImageIcon> images = createDictionary();
            LoginInterface loginInterface = new LoginInterface(images);
        }
    }

    private Map<String, ImageIcon> createDictionary() {
        String directoryPath = "src/main/java/org/example/images_actprod";
        Map<String, ImageIcon> images = new HashMap<>();
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        fileName = fileName.substring(0, fileName.lastIndexOf("."));
                        ImageIcon image = new ImageIcon("src/main/java/org/example/images_actprod/" + fileName + ".jpg");

                        image.setImage(image.getImage().getScaledInstance(300, 400, Image.SCALE_DEFAULT));


                        images.put(fileName, image);
                    }
                }
            }
        }
        ImageIcon lotr = new ImageIcon(
                "src/main/java/org/example/images/The Lord of the Rings The Return of the King.jpg");
        lotr.setImage(lotr.getImage().getScaledInstance(300, 400, Image.SCALE_DEFAULT));
        images.put("The Lord of the Rings: The Return of the King", lotr);

        ImageIcon madmax = new ImageIcon("src/main/java/org/example/images/Mad Max Fury Road.jpg");
        madmax.setImage(madmax.getImage().getScaledInstance(300, 400, Image.SCALE_DEFAULT));
        images.put("Mad Max: Fury Road", madmax);

        return images;
    }

    public static void main(String[] args) throws ParseException {
        IMDB.getInstance().run();
    }
}
