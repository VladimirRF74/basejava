package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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

    public static class Specialisation implements Serializable {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Specialisation(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.NOW, title, description);
        }

        public Specialisation(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

        public Specialisation(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        @Override
        public String toString() {
            return "Specialisation{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Specialisation)) return false;

            Specialisation that = (Specialisation) o;

            if (!startDate.equals(that.startDate)) return false;
            if (!endDate.equals(that.endDate)) return false;
            if (!title.equals(that.title)) return false;
            return Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }
    }
}