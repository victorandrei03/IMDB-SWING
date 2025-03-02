package org.example.requests;

import org.example.requests.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestsHolder {
    public static List<Request> requests = new ArrayList<>();
    public static void add_request(Request r){
        requests.add(r);
    }
    public static void remove_req(Request r){
        requests.remove(r);
    }
}
