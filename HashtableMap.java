import java.util.NoSuchElementException;
@SuppressWarnings("unchecked")

/**
 * This data structure represents a HashtableMap, where we can store key and value pairs. 
 * There are several operations that we can do with this, like inserting and removing key value pairs, accessing data, and checking if data exists
 * The array in which key value pairs are stored is resized dynamically.
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>{
    private int capacity;
    private int size;
    final KeyValuePair<Integer, Integer> del = new KeyValuePair<Integer,Integer>(-1, -1);
    final double defaultLF = 0.7;
    protected KeyValuePair[] arr;


    /**
     * Constructs a HashtableMap object with a given capacity.
     * @param capacity how many items the HashtableMap can store.
     */
    public HashtableMap(int capacity) {
        this.capacity = capacity;
        size = 0;
        arr = (KeyValuePair<KeyType, ValueType>[]) new KeyValuePair[capacity];
    }
    /**
     * Constructs a HashtableMap object with a default capacity of 8.
     */
    public HashtableMap() {
        this.capacity = 8;
        size = 0;
        arr = (KeyValuePair<KeyType, ValueType>[]) new KeyValuePair[capacity];
    }
    /**
     * This method generates a hash index for a given key
     * @param key the key to generate a hash index for
     * @return the hash index
     */
    private int hash(KeyType key) {
        return Math.abs(key.hashCode()) % capacity;
    }
    /**
     * Returns the current size of the HashtableMap.
     */
    public int getSize() {
        return size;
    }
    /**
     * Returns the capacity of this HashtableMap.
     */
    public int getCapacity() {
        return capacity;
    }
    /**
     * Returns the current load factor.
     * @return the current load factor as a double.
     */
    private double getCurrentLF() {
        return (double) size / (double) capacity;
    }
    /**
     * Uses linear probing to find the next open spot in the hash array
     * @param start the hash index to start looking from
     * @return the hash index of the next open spot
     */
    private int findNextOpen(int start) {
        boolean found = false;
        while (!found) {
            if (start >= capacity) start -= capacity;
            if (arr[start] == null || arr[start] == del) {
                found = true;
                return start;
            }
            else {
                start++;
            }
        }
        return -1;
    }
    /**
     * Uses linear probing to find an item that had a duplicate hash index and was subsequently moved by linear probing.
     * @param start the index to start looking from
     * @param item the key value pair we are looking for
     * @return the hash index where the key value pair is stored, returns -1 if not found
     */
    private int findNext(int start, KeyValuePair<KeyType, ValueType> item) {
        boolean found = false;
        while (!found) {
            if (start >= capacity) start -= capacity;
            if (arr[start] == item) {
                found = true;
                return start;
            }
            else if (arr[start] == null) {
                found = true;
                return -1;
            }
            else {
                start++;
            }
        }
        return -1;
    }
    /**
     * Uses linear probing to find a key that was moved to an open spot due to a key value pair occupying it's original hash index.
     * @param start the index to start looking from
     * @param key the key to look for
     * @return the hash index where the key is found
     */
    private int findNextByKey(int start, KeyType key) {
        boolean found = false;
        while (!found) {
            if (start >= capacity) start -= capacity;
            if (arr[start] == null) {
                found = true;
                return -1;
            }
            else if (arr[start].key.equals(key)) {
                found = true;
                return start;
            }
            else {
                start++;
            }
        }
        return -1;
    }
    @Override
    /**
     * Inserts a key value pair into the HashtableMap. Resizes the hash array if necessary.
     */
    public void put(KeyType key, ValueType value) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Cannot put a null key");
        }
        if (containsKey(key)) {
            throw new IllegalArgumentException("Cannot put desired key, key already exists");
        }
        int indexToPut = hash(key);
        KeyValuePair<KeyType, ValueType> obj = new KeyValuePair<KeyType,ValueType>(key, value);
        if (arr[indexToPut] != null || arr[indexToPut] != del) {
            indexToPut = findNextOpen(indexToPut);
        }
        arr[indexToPut] = obj;
        size++;
        if (getCurrentLF() >= defaultLF) {
            growArray();
        }
    }
    @Override
    /**
     * Checks if a given key is already in the HashtableMap
     */
    public boolean containsKey(KeyType key) {
        if (arr[hash(key)] == null) {
            return false;
        }
        if (arr[hash(key)].getKey().equals(key)) {
            return true;
        }
        else {
            int idx = findNextByKey(hash(key), key);
            if (idx == -1) {
                return false;
            }
            else {
                return true;
            }
        }
    }
    @Override
    /**
     * Returns the value associated with a given key.
     * @throws NoSuchElementException if the key does not exist in this HashtableMap.
     */
    public ValueType get(KeyType key) throws NoSuchElementException {
        if (!containsKey(key)) {
            throw new NoSuchElementException("Key does not exist");
        }
        int index = hash(key);
        if (arr[index].key != key) {
            index = findNextByKey(index, key);
        }
        return (ValueType) arr[index].getVal();
    }
    @Override
    /**
     * Removes a key value pair from the HashtableMap by its key. Returns the value associated with the removed key value pair.
     * @throws NoSuchElementException if the given key is not found in the hash array.
     */
    public ValueType remove(KeyType key) throws NoSuchElementException {
        if (!containsKey(key)) {
            throw new NoSuchElementException("Key does not exist");
        }
        int index = hash(key);
        if (arr[index].key != key) {
            index = findNextByKey(index, key);
        }
        ValueType data = (ValueType) arr[index].val;
        arr[index] = del;
        size--;
        return data;
    }
    @Override
    /**
     * Clears the HashtableMap.
     */
    public void clear() {
        size = 0;
        arr = (KeyValuePair<KeyType, ValueType>[]) new KeyValuePair[capacity];
    }
    /**
     * Helper method to dynamically resize and rehash the hash array if need be.
     */
    private void growArray() {
        KeyValuePair<KeyType, ValueType>[] newArr = (KeyValuePair<KeyType, ValueType>[]) new KeyValuePair[capacity*2]; // create new array
        this.capacity *= 2; // double capacity

        size = 0; // set size to 0
        for (int i = 0; i < arr.length; i++) {  // iterate through old hash array, and rehash items
            if (arr[i] != null && arr[i] != del) {
                newArr[hash((KeyType) arr[i].key)] = arr[i];
                size++;
            }
        }
        arr = newArr; // replace array
    }

}
