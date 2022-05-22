package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume = null;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    if (resume.getSection(type) == null) {
                        switch (type) {
                            case OBJECTIVE, PERSONAL -> resume.addSection(type, new TextSection("No text"));
                            case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(type, new ListSection("No text"));
                        }
                    }
                }
                break;
            case "new":
                if (uuid == null) {
                    resume = new Resume();
                    for (ContactType type : ContactType.values()) {
                        resume.addContact(type, "No text");
                    }
                    for (SectionType type : SectionType.values()) {
                        switch (type) {
                            case OBJECTIVE, PERSONAL -> resume.addSection(type, new TextSection("No text"));
                            case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(type, new ListSection("No text"));
                            case EDUCATION, EXPERIENCE -> resume.addSection(type, new OrganizationSection(new ArrayList<>()));
                        }
                    }
                }
                break;
            default:
                throw new IllegalStateException("Action" + action + "is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName").trim();
        Resume resume;
        assert uuid != null;
        if (uuid.trim().length() != 0) {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
            doSaveUpdate(request, resume);
            storage.update(resume);
        } else {
            resume = new Resume(fullName);
            doSaveUpdate(request, resume);
            storage.save(resume);
        }
        response.sendRedirect("resume");
    }

    private void doSaveUpdate(HttpServletRequest request, Resume resume) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name().trim());
            if (value == null || value.length() == 0) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(type, new TextSection(value));
                    case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(type, new ListSection(value.split("\\n")));
                    case EDUCATION, EXPERIENCE -> resume.addSection(type, new OrganizationSection(new ArrayList<>()));
                }
            }
        }
    }
}