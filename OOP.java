import java.util.Scanner;

public class OOP {
    String name;
    String regNo;
    String studentNo;

    // Constructor
    public OOP(String name, String regNo, String studentNo) {
        this.name = name;
        this.regNo = regNo;
        this.studentNo = studentNo;
        // You can initialize any additional properties or perform setup here if needed
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student name:");
        String name = scanner.nextLine();
        System.out.print("Enter Registration number: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter Student number: Ajax Woocommerce Search");
        String studentNo = scanner.nextLine();
        OOP oop = new OOP(name, regNo, studentNo);
        
        System.out.println("Student name: " + oop.name);
        System.out.println("Registration number: " + oop.regNo);
        System.out.println("Student number: " + oop.studentNo);

        scanner.close();
    }
}
