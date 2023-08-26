/**
 * This class models a key value pair to be used in our implementation of a HashtableMap.
 */
public class KeyValuePair<KeyType, ValueType> {
    protected KeyType key;
    protected ValueType val;
    /**
     * This method constructs a key value pair.
     * @param key the key
     * @param val the value
     */
    public KeyValuePair(KeyType key, ValueType val) {
        this.key = key;
        this.val = val;
    }
    /**
     * Returns the key.
     * @return
     */
    public KeyType getKey() {
        return key;
    }
    /**
     * Returns the value
     * @return
     */
    public ValueType getVal() {
        return val;
    }
}
