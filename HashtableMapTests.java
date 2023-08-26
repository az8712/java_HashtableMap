// --== CS400 Project One File Header ==--
// Name: Zaid Ahmed
// CSL Username: zahmed
// Email: zahmed8@wisc.edu
// Lecture #: 004 MoWeFr 3:30 pm to 4:20 pm
// Notes to Grader: <any optional extra notes to your grader>

import java.util.NoSuchElementException;

/**
 * This class contains tester methods for the HashtableMap class
 */
public class HashtableMapTests {
    
    /**
     * This method checks that bad input when using the put method is caught and handled.
     * @return true if the errors are caught as expected, false otherwise.
     */
    public static boolean test1() {
        HashtableMap<Integer, Integer> map = new HashtableMap<Integer, Integer>(5);
        try {
            map.put(null, 5);
            return false;
        }
        catch (IllegalArgumentException e) { }
        catch (Exception e) {
            return false;
        }
        map.put(1, 1);
        try {
            map.put(1, 3);
        }
        catch (IllegalArgumentException e) { }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * Makes sure the put method adds key value pairs as expected
     * @return true if the results are as expected, false otherwise
     */
    public static boolean test2() {
        HashtableMap<Integer, Integer> map = new HashtableMap<Integer, Integer>(5);
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        return map.get(1).equals(1) && map.get(2).equals(2) && map.get(3).equals(3);
    }
    /**
     * Checks to see that removing an item from the map updates the size correctly 
     * @return true if the size is updated as expected, false otherwise.
     */
    public static boolean test3() {
        HashtableMap<Integer, Integer> map = new HashtableMap<Integer, Integer>(5);
        map.put(1, 1);
        if (map.getSize() != 1) {
            return false;
        }
        map.remove(1);
        if (map.getSize() != 0) {
            return false;
        }
        return true;
    }
    /**
     * Checks that you cannot remove an item from the hashtablemap that does not exist
     * @return
     */
    public static boolean test4() {
        HashtableMap<Integer, Integer> map = new HashtableMap<Integer, Integer>(5);
        try {
            map.remove(1);
            return false;
        }
        catch (NoSuchElementException e) { }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * Check to make sure containsKey returns true when the key exists, and returns false when it does not
     * @return true if the method works as expected, false otherwise
     */
    public static boolean test5() {
        HashtableMap<Integer, Integer> map = new HashtableMap<Integer, Integer>(5);
        if (map.containsKey(1)) {
            return false;
        }
        map.put(1, 1);
        if (!map.containsKey(1)) {
            return false;
        }
        return true;
    }

    public static void main(String[] arg) {
        System.out.println("All tests: " + (test1() && test2() && test3() && test4() && test5()));
    }
}
