package org.example.strategy;

public class ResolveRequest implements ExperienceStrategy {
    public ResolveRequest() { }

    public int calculateExperience() {
        return 10;
    }
}
