package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link link;
    private final List<Specialisation> specialisations;

    public Organization(Link link, List<Specialisation> specialisations) {
        this.link = link;
        this.specialisations = specialisations;
    }

    public Organization(String name, String url, Specialisation... specialisations) {
        this(new Link(name, url), Arrays.asList(specialisations));
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link +
                ", specialisations=" + specialisations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        if (!Objects.equals(link, that.link)) return false;
        return Objects.equals(specialisations, that.specialisations);
    }

    @Override
    public int hashCode() {
        int result = link != null ? link.hashCode() : 0;
        result = 31 * result + (specialisations != null ? specialisations.hashCode() : 0);
        return result;
    }
}