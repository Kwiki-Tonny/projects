/*
 * ======================================================================
 * ARRAYS vs ARRAYS OF OBJECTS IN JAVA - EDUCATIONAL GUIDE FOR BEGINNERS
 * ======================================================================
 * 
 * KEY DIFFERENCES:
 * 
 * 1. PRIMITIVE ARRAYS (int[], double[], char[], etc.)
 *    - Store primitive data types directly in memory
 *    - Fixed size (cannot grow or shrink)
 *    - Memory efficient for simple data
 *    - Example: int[] numbers = {1, 2, 3, 4, 5};
 * 
 * 2. OBJECT ARRAYS (String[], Student[], Object[], etc.)
 *    - Store references (memory addresses) to objects
 *    - Still fixed size, but you can create new objects
 *    - Store complex data with multiple properties
 *    - Example: String[] names = {"Alice", "Bob", "Charlie"};
 * 
 * 3. DYNAMIC ARRAYS (ArrayList)
 *    - Like arrays but can grow/shrink dynamically
 *    - More flexible but slightly slower than arrays
 *    - Better for collections that change size frequently
 * 
 * REMEMBER: All non-primitive types (String, objects, etc.) are stored 
 * as references to objects, even when declared as arrays.
 * ======================================================================
 */

import java.util.Arrays;
import java.util.ArrayList;

public class Array {
    
    // ========== EXAMPLE 1: ARRAY OF OBJECTS (String Array) ==========
    public static void demonstrateStringArray() {
        System.out.println("\n--- EXAMPLE 1: ARRAY OF OBJECTS (String Array) ---");
        
        // Fixed-size STRING ARRAY with 5 slots
        // Initially all values are null (no object assigned yet)
        String[] friendsArray = new String[5];
        System.out.println("Empty string array: " + Arrays.toString(friendsArray));
        // Output: [null, null, null, null, null]
        
        // STRING ARRAY initialized with actual String objects
        // Size is fixed at 5 - cannot add more without creating a new array
        String[] friendsArray2 = {"Alice", "Bob", "Charlie", "David", "Eve"};
        System.out.println("Initialized string array: " + Arrays.toString(friendsArray2));
        // Output: [Alice, Bob, Charlie, David, Eve]
        
        // Note: String is a REFERENCE TYPE (object), so this is an 
        // ARRAY OF OBJECTS - it stores references to String objects
    }
    
    // ========== EXAMPLE 2: DYNAMIC ARRAY OF OBJECTS (ArrayList) ==========
    public static void demonstrateArrayList() {
        System.out.println("\n--- EXAMPLE 2: DYNAMIC ARRAY OF OBJECTS (ArrayList) ---");
        
        // ArrayList is a flexible, resizable array of objects
        // You can add/remove elements dynamically
        ArrayList<String> friendsList = new ArrayList<>();
        friendsList.add("Alice");
        friendsList.add("John");
        friendsList.add("Charlie");
        friendsList.add("David");
        friendsList.add("Eve");
        System.out.println("ArrayList after adding 5 names: " + friendsList);
        // Output: [Alice, John, Charlie, David, Eve]
        
        // ArrayList can grow - add more elements beyond initial capacity
        friendsList.add("Frank"); // This works! Array expands automatically
        System.out.println("ArrayList after adding 6th name: " + friendsList);
        // Output: [Alice, John, Charlie, David, Eve, Frank]
    }
    
    // ========== EXAMPLE 3: CONVENIENT WAY TO INITIALIZE ArrayList ==========
    public static void demonstrateArrayListInitialization() {
        System.out.println("\n--- EXAMPLE 3: CONVENIENT ArrayList INITIALIZATION ---");
        
        // Create ArrayList and initialize with values in one line
        // Arrays.asList() converts a fixed array into a List
        ArrayList<String> friendsList2 = new ArrayList<>(
            Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve")
        );
        System.out.println("ArrayList initialized with Arrays.asList(): " + friendsList2);
        // Output: [Alice, Bob, Charlie, David, Eve]
    }
    
    // ========== EXAMPLE 4: ARRAY OF CUSTOM OBJECTS ==========
    public static void demonstrateCustomObjectArray() {
        System.out.println("\n--- EXAMPLE 4: ARRAY OF CUSTOM OBJECTS (Student Array) ---");
        
        // Create an array that can hold 3 Student objects
        // Initially all references point to null (no Student created yet)
        Student[] students = new Student[3];
        System.out.println("Empty Student array: " + Arrays.toString(students));
        // Output: [null, null, null]
        
        // Create actual Student objects and assign them to array positions
        students[0] = new Student("Alice", 85);
        students[1] = new Student("Bob", 92);
        students[2] = new Student("Charlie", 78);
        
        System.out.println("Student array with objects:");
        for (Student student : students) {
            System.out.println("  " + student);
        }
        // Output:
        //   Alice - Score: 85
        //   Bob - Score: 92
        //   Charlie - Score: 78
    }
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("ARRAYS vs ARRAYS OF OBJECTS DEMONSTRATION");
        System.out.println("========================================");
        
        // Run all demonstrations
        demonstrateStringArray();
        demonstrateArrayList();
        demonstrateArrayListInitialization();
        demonstrateCustomObjectArray();
        
        System.out.println("\n========================================");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("========================================");
        System.out.println("1. Arrays have FIXED SIZE (declare with size: new String[5])");
        System.out.println("2. ArrayLists are DYNAMIC (grow as needed)");
        System.out.println("3. Both String[] and ArrayList<String> are ARRAYS OF OBJECTS");
        System.out.println("   (String is a reference type, not primitive)");
        System.out.println("4. Custom objects can also be stored in arrays");
        System.out.println("   (e.g., Student[] students = new Student[3])");
    }
}

/**
 * Simple Student class to demonstrate arrays of custom objects
 */
class Student {
    private String name;
    private int score;
    
    // Constructor - initializes a Student object
    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }
    
    // toString() method - defines how to display this object
    @Override
    public String toString() {
        return name + " - Score: " + score;
    }
}