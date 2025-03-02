package org.example.strategy;

public class AddProductionActor implements ExperienceStrategy{

    public AddProductionActor() {}
    public int calculateExperience() {
        return 15;
    }
}
