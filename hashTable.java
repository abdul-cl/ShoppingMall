class hashTable {

    private int size;
    private DSALinkedList[] table;

    public hashTable(int size) {
        this.size = size;
        table = new DSALinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new DSALinkedList();
        }
    }

    public void put(String key, DSALinkedList value) {
        int index = hashFunction(key);
        table[index].insertLast(new Entry(key, value));
    }

    public DSALinkedList get(String key) {
        int index = hashFunction(key);
        DSALinkedList list = table[index];
        for (Object obj : list) {
            Entry entry = (Entry) obj;
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean containsKey(String key) {
        int index = hashFunction(key);
        DSALinkedList list = table[index];
        for (Object obj : list) {
            Entry entry = (Entry) obj;
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public void remove(String key) {
        int index = hashFunction(key);
        DSALinkedList list = table[index];
        for (Object obj : list) {
            Entry entry = (Entry) obj;
            if (entry.key.equals(key)) {
                list.remove(obj);
                return;
            }
        }
    }

    private int hashFunction(String key) {
        return Math.abs(key.hashCode() % size);
    }

    private static class Entry {
        String key;
        DSALinkedList value;

        public Entry(String key, DSALinkedList value) {
            this.key = key;
            this.value = value;
        }
    }
}
