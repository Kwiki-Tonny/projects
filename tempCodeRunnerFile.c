#include <stdio.h>
#include <string.h>

// Global Variables
char userName[20] = "@usertestDetails";
char userPassword[15] = "paSSphrase_27";

// Function to Log In
void logIn() {
    int attempts = 0;
    char userNameInput[20];
    char userPasswordInput[15];

    while (attempts < 4) {
        printf("\nEnter your User-name: ");
        scanf("%19s", userNameInput);
        printf("Enter your Password: ");
        scanf("%14s", userPasswordInput);

        if (strcmp(userNameInput, userName) == 0 && strcmp(userPasswordInput, userPassword) == 0) {
            printf("\nLogin Successful!\n");
            printf("Access Granted.\n");
            return;  // Exit on success
        } else {
            attempts++;
            printf("Incorrect details. You have %d attempt(s) left.\n", 4 - attempts);
        }
    }

    printf("Maximum attempts reached. Try again later.\n");
}

// Main Function
int main() {
    int choice;

    do {
        printf("\n========== MENU ==========\n");
        printf("1. Log In\n");
        printf("2. Sign Up\n");
        printf("3. Exit\n");
        printf("Choose Your Action To Continue: ");

        if (scanf("%d", &choice) != 1) {
            printf("Invalid input. Please enter a number.\n");
            while (getchar() != '\n'); // clear input buffer
            continue;
        }

        switch (choice) {
            case 1:
                logIn();
                break;

            case 2:
                printf("Sign Up feature not implemented yet.\n");
                break;

            case 3:
                printf("Exiting program. Goodbye!\n");
                break;

            default:
                printf("Invalid choice. Please select from the menu options.\n");
                break;
        }

    } while (choice != 3);

    return 0;
}
