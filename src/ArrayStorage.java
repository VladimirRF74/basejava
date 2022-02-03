/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i] = null;
        }
    }

    void save(Resume resume) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = resume;
                break;
            }
        }
    }

    Resume get(String uuid) {
        int i;
        for (i = 0; i < size(); i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        return new Resume();
    }

    void delete(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (uuid.equals(storage[i].toString())) {
                System.arraycopy(storage, i + 1, storage, i, size());
                storage[size()] = null;

            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResume = new Resume[size()];
        if (size() >= 0) System.arraycopy(storage, 0, allResume, 0, size());
        return allResume;
    }

    int size() {
        int counter = 0;
        for (Resume resume : storage) {
            if (resume != null) {
                counter++;
            } else break;
        }
        return counter;
    }
}
