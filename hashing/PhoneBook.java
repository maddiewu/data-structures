import java.util.*;
import java.util.HashMap;

public class PhoneBook {
    LinkedList<Entry>[] book;
    Person firstPerson;
    PhoneNumber number;
    int size = 0;

    public PhoneBook() {
        book = new LinkedList[26];

    }

    public static class Entry<Person,PhoneNumber> {

        public int hash;

        public Entry() {
            hash = _key.hashCode();
        }

        /** The key used for lookup. */
        private Person _key;
        /** The associated value. */
        private PhoneNumber _value;
        private String phone;
        private String name;

        /** Create a new (KEY, VALUE) pair. */
        public Entry(Person key, PhoneNumber value) {
            _key = key;
            name = key.toString();
            _value = value;
            phone = value.toString();
        }

        /** Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return _key.equals(other._key);
        }

        /** Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry &&
                    _key.equals(((Entry) other)._key) &&
                    _value.equals(((Entry) other)._value));
        }
    }

    public PhoneBook(Person x, PhoneNumber y) {
        this.firstPerson = x;
        this.number = y;
    }
    /*
     * Adds a person with this name to the phone book and associates 
     * with the given PhoneNumber.
     */
    public void addEntry(Person personToAdd, PhoneNumber numberToAdd){
        if (containsKey(personToAdd)) {
            for (LinkedList<Entry> y : book) {
                if (y == null) {
                    y = new LinkedList<>();
                }
                for (Entry x: y) {
                    if (x._key.equals(personToAdd)) {
                        int z = (x._key.hashCode() & 0x7FFFFFFF) % book.length;
                        if (book[z] == null) {
                            book[z] = new LinkedList<>();
                        }
                        Entry replace = new Entry(personToAdd, numberToAdd);
                        book[z].add(replace);
                        x._value = numberToAdd;
                    }
                }
            }
        } else {
            Entry addEntry = new Entry(personToAdd, numberToAdd);
            int x = (addEntry._key.hashCode() & 0x7FFFFFFF) % book.length;
            if (book[x] == null) {
                book[x] = new LinkedList<>();
            }
            book[x].add(addEntry);
            size++;
        }
    }

    public boolean containsKey(Person key) {
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (this.book[i] != null) {
                    for (int j = 0; j < this.book[i].size(); j++) {
                        if (this.book[i].get(j)._key.equals(key)) {
                            if (book[i].get(j).hash == key.hashCode())
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /*
     * Access an entry in the phone book. 
     */
    public PhoneNumber getNumber(Person personToLookup){
    	if (book != null) {
            int index = 0;
            int linkedIndex = 0;
            for (LinkedList<Entry> x : book) {
                if (x != null) {
                    for (Entry y : x) {
                        if (y != null) {
                            if (personToLookup.equals(y._key)) {
                                if (personToLookup.toString().hashCode() == y.hash) {
                                    return (PhoneNumber) y._value;
                                }
                            }
                        }
                    }
                }
            }
        }
    	return null;
    }

}
