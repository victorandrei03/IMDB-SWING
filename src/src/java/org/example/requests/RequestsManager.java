package org.example.requests;

import org.example.requests.Request;

public interface RequestsManager {
    public void createRequest(int operation);
    public void removeRequest(Request r);
}
