package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer1 implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            for (SectionType st : sections.keySet()) {
                System.out.println(st);
            }
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                System.out.println(entry);
                if (SectionType.ACHIEVEMENT.equals(entry.getKey()) || SectionType.QUALIFICATIONS.equals(entry.getKey())) {
                    ListSection ls = (ListSection) entry.getValue();
                    dos.writeInt(ls.getItems().size());
                    dos.writeUTF(entry.getKey().name());
                    for (String s : ls.getItems()) {
                        dos.writeUTF(s);
                    }
                }
                if (SectionType.PERSONAL.equals(entry.getKey()) || SectionType.OBJECTIVE.equals(entry.getKey())) {
                    TextSection ts = (TextSection) entry.getValue();
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(ts.getContent());
                }

                if (SectionType.EDUCATION.equals(entry.getKey()) || SectionType.EXPERIENCE.equals(entry.getKey())) {
                    OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
                    List<Organization> organization = organizationSection.getOrganizations();
                    dos.writeInt(organization.size());
                    writeObject(dos, entry, organization);
                }
            }
        }
    }

    private void writeObject(DataOutputStream dos, Map.Entry<SectionType, AbstractSection> entry, List<Organization> organization) throws IOException {
        dos.writeUTF(entry.getKey().name());
        for (Organization value : organization) {
            dos.writeUTF(value.getLink().getName());
            dos.writeUTF(value.getLink().getUrl());
            writeSpecialisation(dos, value);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int size1 = dis.readInt();
            for (int i = 0; i < size1; i++) {
                SectionType st = SectionType.valueOf(dis.readUTF());
                resume.addSection(st, readSection(dis, st));
            }
            System.out.println(resume);
            return resume;
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonth().getValue());
        dos.writeInt(ld.getDayOfMonth());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }

    private void writeSpecialisation(DataOutputStream dos, Organization o) throws IOException {
        for (Organization.Specialisation s : o.getSpecialisations()) {
            writeLocalDate(dos, s.getStartDate());
            writeLocalDate(dos, s.getEndDate());
            dos.writeUTF(s.getTitle());
            dos.writeUTF(s.getDescription());
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis.readUTF()));
            /*case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(new Organization.Specialisation(
                                        readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()
                                ))
                        )));*/
            default:
                throw new IllegalStateException();
        }
    }

    private List<String> readList(DataInputStream dis, String s) throws IOException {
        int size = dis.readInt();
        List<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(s);
        }
        return list;
    }
}
