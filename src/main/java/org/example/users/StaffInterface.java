package org.example.users;

public interface StaffInterface<T> {
    public void addProductionSystem(T p);
    public void addActorSystem(T a);
    public void removeProductionSystem(String name);
    public void removeActorSystem(String name);
    public void updateProduction(T p);
    public void updateActor(T a);
    public void resolve_requests();
}
