import java.util.Scanner;

public class ATMSim {
    private static double balance = 1000000; // Starting balance in UgShs

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean running = true;

        System.out.println("================================");
        System.out.println("   Welcome to ATM Simulator");
        System.out.println("================================");

        while (running) {
            displayMenu();
            System.out.print("Enter your choice (1-4): ");
            
            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        checkBalance();
                        break;
                    case 2:
                        deposit(scanner);
                        break;
                    case 3:
                        withdraw(scanner);
                        break;
                    case 4:
                        exit();
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter 1-4.\n");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.\n");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- ATM Menu ---");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Exit");
    }

    private static void checkBalance() {
        System.out.println("\n--- Current Balance ---");
        System.out.printf("Your balance: UgShs %.2f\n", balance);
    }

    private static void deposit(Scanner scanner) {
        System.out.println("\n--- Deposit Money ---");
        System.out.print("Enter amount to deposit (UgShs): ");
        
        try {
            double amount = scanner.nextDouble();

            if (amount <= 0) {
                System.out.println("Error: Amount must be greater than 0!");
                return;
            }

            balance += amount;
            System.out.printf("Successfully deposited: UgShs %.2f\n", amount);
            System.out.printf("New balance: UgShs %.2f\n", balance);
        } catch (Exception e) {
            System.out.println("Invalid amount! Please enter a valid number.");
            scanner.nextLine(); // Clear the invalid input
        }
    }

    private static void withdraw(Scanner scanner) {
        System.out.println("\n--- Withdraw Money ---");
        System.out.print("Enter amount to withdraw (UgShs): ");
        
        try {
            double amount = scanner.nextDouble();

            if (amount <= 0) {
                System.out.println("Error: Amount must be greater than 0!");
                return;
            }

            if (amount > balance) {
                System.out.printf("Error: Insufficient funds! Your balance: UgShs %.2f\n", balance);
                return;
            }

            balance -= amount;
            System.out.printf("Successfully withdrawn: UgShs %.2f\n", amount);
            System.out.printf("New balance: UgShs %.2f\n", balance);
        } catch (Exception e) {
            System.out.println("Invalid amount! Please enter a valid number.");
            scanner.nextLine(); // Clear the invalid input
        }
    }

    private static void exit() {
        System.out.println("\n================================");
        System.out.println("Thank you for using ATM!");
        System.out.printf("Final Balance: UgShs %.2f\n", balance);
        System.out.println("Goodbye!");
        System.out.println("================================");
    }
}
