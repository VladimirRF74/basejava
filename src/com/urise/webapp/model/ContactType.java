package com.urise.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    SKYPE("Скайп"),
    EMAIL("Почта"),
    GITHUB("Профиль Гитхаб"),
    HOME_PAGE("Домашняя страница:");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}