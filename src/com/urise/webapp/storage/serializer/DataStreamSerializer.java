package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

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
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue().toString());
            }
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
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            TextSection ts = null;
            ListSection ls = null;
            OrganizationSection os = null;
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    ts = new TextSection(dis.readUTF());
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    ls = new ListSection(dis.readUTF());
                case EDUCATION:
                case EXPERIENCE:
                    os = new OrganizationSection();
            }
            resume.addSection(sectionType, ts);
            resume.addSection(sectionType, ls);
            resume.addSection(sectionType, os);
            System.out.println(resume);
            return resume;
        }
    }
}
