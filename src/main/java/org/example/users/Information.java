package org.example.users;

import java.util.Date;

public class Information {
    private Credentials credentials;
    private String name;
    private String country;
    private int age;
    private String gender;

    private Date birthDate;

    public Information() {}

    private Information(Information.InformationBuilder builder) {
        this.credentials = builder.credentials;
        this.name = builder.name;
        this.country = builder.country;
        this.age = builder.age;
        this.gender = builder.gender;
        this.birthDate = builder.birthDate;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public static class InformationBuilder {
        private Credentials credentials;
        private String name;
        private String country;
        private int age;
        private String gender;

        private Date birthDate;

        public InformationBuilder credentials(Credentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public InformationBuilder name(String name) {
            this.name = name;
            return this;
        }

        public InformationBuilder country(String country) {
            this.country = country;
            return this;
        }

        public InformationBuilder age(int age) {
            this.age = age;
            return this;
        }

        public InformationBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public InformationBuilder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Information build() {
            return new Information(this);
        }
    }
}
