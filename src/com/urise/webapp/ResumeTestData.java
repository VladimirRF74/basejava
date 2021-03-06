package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Pieter Pen");
        resume = create(resume.getFullName());

        System.out.println(resume);
        /*System.out.println(ContactType.PHONE.getTitle() + " " + resume.getContact(ContactType.PHONE));
        System.out.println(ContactType.EMAIL.getTitle() + " " + resume.getContact(ContactType.EMAIL));
        System.out.println(ContactType.SKYPE.getTitle() + " " + resume.getContact(ContactType.SKYPE));
        System.out.println(ContactType.HOME_PAGE.getTitle() + " " + resume.getContact(ContactType.HOME_PAGE));
        System.out.println(SectionType.OBJECTIVE.getTitle() + "\n" + resume.getSection(SectionType.OBJECTIVE));
        System.out.println(SectionType.PERSONAL.getTitle() + "\n" + resume.getSection(SectionType.PERSONAL));
        System.out.println(SectionType.ACHIEVEMENT.getTitle() + "\n" + resume.getSection(SectionType.ACHIEVEMENT));
        System.out.println(SectionType.QUALIFICATIONS.getTitle() + "\n" + resume.getSection(SectionType.QUALIFICATIONS));
        System.out.println(SectionType.EXPERIENCE.getTitle() + "\n" + resume.getSection(SectionType.EXPERIENCE));
        System.out.println(SectionType.EDUCATION.getTitle() + "\n" + resume.getSection(SectionType.EDUCATION));*/
    }

    public static Resume create(String fullName) {
        Resume resume = new Resume(fullName);
        Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

        Map<ContactType, String> contacts = new HashMap<>();
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.HOME_PAGE, "Home page");
        resume.setContacts(contacts);

        TextSection sectionObjective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        TextSection sectionPersonal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        List<String> listAchievement = new ArrayList<>();
        listAchievement.add("Text-1.");
        listAchievement.add("Text-2.");
        listAchievement.add("Text-3.");

        List<String> listQualifications = new ArrayList<>();
        listQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");

        List<Organization> listExperience = new ArrayList<>();
        listExperience.add(new Organization("Java Online Projects", "JavaOnlineProjects.com",
                new Organization.Specialisation(LocalDate.of(2013, 10, 1),
                        LocalDate.now(), "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок.")));

        listExperience.add(new Organization("Wrike", "wrike.com",
                new Organization.Specialisation(LocalDate.of(2013, 10, 1),
                        LocalDate.of(2016, 1, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API," +
                                " Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                                " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));

        List<Organization> listEducation = new ArrayList<>();
        listEducation.add(new Organization("Coursera", "coursera.com",
                new Organization.Specialisation(LocalDate.of(2013, 3, 1),
                        LocalDate.of(2013, 5, 1),
                        "Functional Programming Principles in Scala by Martin Odersky", "")));

        listEducation.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "link.com", new Organization.Specialisation(LocalDate.of(1993, 3, 1),
                LocalDate.of(1996, 9, 1),
                "Аспирантура (программист С, С++)", ""),
                new Organization.Specialisation(LocalDate.of(1987, 8, 1),
                        LocalDate.of(1993, 7, 1),
                        "Инженер (программист Fortran, C)", "")));

        sections.put(SectionType.PERSONAL, sectionPersonal);
        sections.put(SectionType.OBJECTIVE, sectionObjective);
        sections.put(SectionType.ACHIEVEMENT, new ListSection(listAchievement));
        sections.put(SectionType.QUALIFICATIONS, new ListSection(listQualifications));
        sections.put(SectionType.EXPERIENCE, new OrganizationSection(listExperience));
        sections.put(SectionType.EDUCATION, new OrganizationSection(listEducation));
        resume.setSections(sections);
        return resume;
    }
}