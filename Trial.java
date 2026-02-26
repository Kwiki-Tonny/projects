/*
 A small academic example demonstrating getters and setters (encapsulation)
 and basic validation in Java. This single-file program contains a `Person`
 class with private fields and public accessor/mutator methods, and a
 `main` method that shows how to use them from a console application.
*/

public class Trial {
	public static void main(String[] args) {
		System.out.println("-- Getters & Setters Demo --");

		// Create and show a person
		Person p = new Person("Alice", 30);
		System.out.println("Initial: " + p);

		// Update via setters
		p.setAge(31);
		p.setName("Alice Smith");
		System.out.println("After update: " + p);

		// Demonstrate validation in setter
		try {
			p.setAge(-5);
		} catch (IllegalArgumentException ex) {
			System.out.println("Validation caught: " + ex.getMessage());
		}

		// Read a person from console input
		java.util.Scanner sc = new java.util.Scanner(System.in);
		System.out.print("Enter name: ");
		String name = sc.nextLine();
		System.out.print("Enter age: ");
		int age = 0;
		try {
			age = Integer.parseInt(sc.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("Invalid number; age set to 0");
		}

		Person user = new Person();
		user.setName(name);
		try {
			user.setAge(age);
		} catch (IllegalArgumentException ex) {
			System.out.println("Invalid age provided: " + ex.getMessage() + " -> age set to 0");
			user.setAge(0);
		}

		System.out.println("You created: " + user);
		sc.close();
	}
}

class Person {
	private String name;
	private int age;

	public Person() {
		this("", 0);
	}

	public Person(String name, int age) {
		setName(name);
		setAge(age);
	}

	// Getter for name
	public String getName() {
		return name;
	}

	// Setter for name (basic null-trimming guard)
	public void setName(String name) {
		this.name = (name == null) ? "" : name.trim();
	}

	// Getter for age
	public int getAge() {
		return age;
	}

	// Setter for age with validation
	public void setAge(int age) {
		if (age < 0) {
			throw new IllegalArgumentException("Age cannot be negative");
		}
		this.age = age;
	}

	@Override
	public String toString() {
		return String.format("Person{name='%s', age=%d}", name, age);
	}
}
