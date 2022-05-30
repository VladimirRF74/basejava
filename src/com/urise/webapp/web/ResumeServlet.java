package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = new TextSection("");
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = new ListSection("");
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection orgSection = (OrganizationSection) section;
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(new Organization("", "", new Organization.Specialisation()));
                            if (orgSection != null) {
                                for (Organization org : orgSection.getOrganizations()) {
                                    List<Organization.Specialisation> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(new Organization.Specialisation());
                                    emptyFirstPositions.addAll(org.getSpecialisations());
                                    emptyFirstOrganizations.add(new Organization(org.getLink(), emptyFirstPositions));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrganizations);
                            break;
                    }
                    resume.addSection(type, section);
                }
                break;
            case "new":
                if (uuid == null) {
                    resume = new Resume();
                    for (ContactType type : ContactType.values()) {
                        resume.addContact(type, "");
                    }
                    showEmptyForm(resume);
                }
                break;
            default:
                throw new IllegalStateException("Action" + action + "is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")).forward(request, response);
    }

    private void showEmptyForm(Resume resume) {
        List<Organization> listOrg = new ArrayList<>();
        for (SectionType type : SectionType.values()) {
            if (resume.getSection(type) == null) {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(type, new TextSection(""));
                    case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(type, new ListSection(""));
                    case EDUCATION, EXPERIENCE -> {
                        listOrg.add(new Organization("", "", new Organization.Specialisation()));
                        resume.addSection(type, new OrganizationSection(listOrg));
                    }
                }
            }
        }
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
            doFill(request, resume);
            storage.update(resume);
        } else if (fullName.length() != 0) {
            resume = new Resume(fullName);
            doFill(request, resume);
            storage.save(resume);
        }
        response.sendRedirect("resume");
    }

    private void doFill(HttpServletRequest request, Resume resume) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (checkValidity(value)) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name().trim());
            String[] values = request.getParameterValues(type.name().trim());
            if ((value == null || value.isEmpty()) && values.length < 2) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(type, new TextSection(value));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        assert value != null;
                        resume.addSection(type, new ListSection(deleteEmptyLines(value)));
                    }
                    case EDUCATION, EXPERIENCE -> {
                        List<Organization> listOrg = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (checkValidity(name)) {
                                List<Organization.Specialisation> positions = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (checkValidity(titles[j])) {
                                        positions.add(new Organization.Specialisation(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]),
                                                titles[j], descriptions[j]));
                                    }
                                }
                                listOrg.add(new Organization(new Link(name, urls[i]), positions));
                            }
                        }
                        resume.addSection(type, new OrganizationSection(listOrg));
                    }
                }
            }
        }
    }

    private List<String> deleteEmptyLines(String value) {
        List<String> ls = new LinkedList<>();
        String[] values = value.split("\\n");
        for (String s : values) {
            if (checkValidity(s)) {
                ls.add(s);
            }
        }
        return ls;
    }

    private boolean checkValidity(String s) {
        return s != null && s.trim().length() != 0;
    }
}