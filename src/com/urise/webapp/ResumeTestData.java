package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Pieter Pen");
        resume = create(resume.getUuid(), resume.getFullName());

        System.out.println(resume);
        System.out.println(ContactType.PHONE.getTitle() + " " + resume.getContacts(ContactType.PHONE));
        System.out.println(ContactType.EMAIL.getTitle() + " " + resume.getContacts(ContactType.EMAIL));
        System.out.println(ContactType.SKYPE.getTitle() + " " + resume.getContacts(ContactType.SKYPE));
        System.out.println(ContactType.HOME_PAGE.getTitle() + " " + resume.getContacts(ContactType.HOME_PAGE));
        System.out.println(SectionType.OBJECTIVE.getTitle() + "\n" + resume.getSections(SectionType.OBJECTIVE));
        System.out.println(SectionType.PERSONAL.getTitle() + "\n" + resume.getSections(SectionType.PERSONAL));
        System.out.println(SectionType.ACHIEVEMENT.getTitle() + "\n" + resume.getSections(SectionType.ACHIEVEMENT));
        System.out.println(SectionType.QUALIFICATIONS.getTitle() + "\n" + resume.getSections(SectionType.QUALIFICATIONS));
        System.out.println(SectionType.EXPERIENCE.getTitle() + "\n" + resume.getSections(SectionType.EXPERIENCE));
        System.out.println(SectionType.EDUCATION.getTitle() + "\n" + resume.getSections(SectionType.EDUCATION));
    }

    public static Resume create(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        Map<SectionType, AbstractSection> sections = new HashMap<>();
        Map<ContactType, String> contacts = new HashMap<>();
        List<String> listAchievement = new ArrayList<>();
        List<String> listQualifications = new ArrayList<>();
        List<Organization> listExperience = new ArrayList<>();
        List<Organization> listEducation = new ArrayList<>();
        TextSection sectionObjective = new TextSection("\tВедущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        TextSection sectionPersonal = new TextSection("\tАналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.HOME_PAGE, "Home page");
        resume.setContacts(contacts);
        listAchievement.add("\tС 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.\n");
        listAchievement.add("\tРеализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.\n");
        listAchievement.add("\tНалаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");

        listQualifications.add("\tJEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\n");
        listQualifications.add("\tVersion control: Subversion, Git, Mercury, ClearCase, Perforce\n");
        listQualifications.add("\tDB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");

        listExperience.add(new Organization("Java Online Projects", "JavaOnlineProjects.com",
                new Specialisation(LocalDate.of(2013, 10, 1),
                        LocalDate.now(), "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок.")));

        listExperience.add(new Organization("Wrike", "wrike.com",
                new Specialisation(LocalDate.of(2013, 10, 1),
                        LocalDate.of(2016, 1, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API," +
                                " Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                                " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));

        listEducation.add(new Organization("Coursera", "coursera.com",
                new Specialisation(LocalDate.of(2013, 3, 1),
                        LocalDate.of(2013, 5, 1),
                        "Functional Programming Principles in Scala by Martin Odersky", "")));

        listEducation.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "link.com", new Specialisation(LocalDate.of(1993, 3, 1),
                LocalDate.of(1996, 9, 1),
                "Аспирантура (программист С, С++)", ""),
                new Specialisation(LocalDate.of(1987, 8, 1),
                        LocalDate.of(1993, 7, 1),
                        "Инженер (программист Fortran, C)", "")));

        sections.put(SectionType.OBJECTIVE, sectionObjective);
        sections.put(SectionType.PERSONAL, sectionPersonal);
        sections.put(SectionType.ACHIEVEMENT, new ListSection(listAchievement));
        sections.put(SectionType.QUALIFICATIONS, new ListSection(listQualifications));
        sections.put(SectionType.EXPERIENCE, new OrganizationSection(listExperience));
        sections.put(SectionType.EDUCATION, new OrganizationSection(listEducation));
        resume.setSections(sections);
        return resume;
    }
}