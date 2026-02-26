/*
 * =====================================================================================
 *
 * Author: Ssenkuba Tonny Kwikiriza
 * Filename:  airtel.c
 *
 * Description:  A C program that simulates how Airtel Money buying airtime works.
 * Purpose:  Academic demonstration of turning a TAD (Technical Analysis and Design) assignment into code.
 *
 * =====================================================================================
 */

#include <stdio.h>
#include <string.h>

// Global variables
double accountBalance = 10000.0;
double airtimeAccount = 2000.0;
char userPIN[5] = "2005";
char dial[6] = "*185#";

// Function to check account balance
void checkAccountBalance() {
    printf("Your current account balance is UGX %.2lf\n", accountBalance);
}

// Function to check airtime balance
void checkAirtimeBalance() {
    printf("Your current airtime balance is UGX %.2lf\n", airtimeAccount);
}

// Function to buy airtime
void buyAirtime(double amount) {
    if (amount <= 0) {
        printf("Invalid amount. Please enter a positive value.\n");
        return;
    }

    if (amount > accountBalance) {
        printf("You have insufficient funds to complete this transaction.\n");
        return;
    }

    char userPINInput[5];
    int attempts = 0;

    // Validate user PIN
    while (attempts < 3) {
        printf("Please enter your PIN to complete the transaction: ");
        scanf("%4s", userPINInput);

        if (strcmp(userPINInput, userPIN) == 0) {
            accountBalance -= amount;
            airtimeAccount += amount;
            printf("Transaction successful!\n");
            printf("Your new Airtime Balance: UGX %.2lf\n", airtimeAccount);
            printf("Your new Account Balance: UGX %.2lf\n", accountBalance);
            printf("Thank you for choosing Airtel Money Services.\n");
            return;
        } else {
            attempts++;
            printf("Incorrect PIN. You have %d attempt(s) left.\n", 3 - attempts);
        }
    }

    printf("Maximum attempts reached. Try again later.\n");
}

// Function to deposit money
void deposit(double amount) {
    if (amount <= 0) {
        printf("Invalid deposit amount.\n");
        return;
    }

    accountBalance += amount;
    printf("Deposit successful. Your new balance is UGX %.2lf\n", accountBalance);
}

// Function to withdraw money
void withdraw(double amount) {
    if (amount <= 0) {
        printf("Invalid withdrawal amount.\n");
        return;
    }

    if (amount <= accountBalance) {
        accountBalance -= amount;
        printf("Withdrawal successful. Your new balance is UGX %.2lf\n", accountBalance);
    } else {
        printf("Insufficient funds. Cannot withdraw UGX %.2lf\n", amount);
    }
}

// Main function
int main() {
    int choice;
    double amount;
    char userDial[6];

    printf("Dial *185# to access Airtel Money menu: ");
    scanf("%5s", userDial);

    if (strcmp(userDial, dial) != 0) {
        printf("Invalid MMI Code.\n");
        return 0;
    }

    do {
        printf("\n==============================\n");
        printf("       AIRTEL MONEY MENU      \n");
        printf("==============================\n");
        printf("1. Buy Airtime\n");
        printf("2. Check Airtime Balance\n");
        printf("3. Check Account Balance\n");
        printf("4. Withdraw Money\n");
        printf("5. Deposit Money\n");
        printf("6. Exit\n");
        printf("Enter your choice: ");

        if (scanf("%d", &choice) != 1) {
            printf("Invalid input. Please enter a number.\n");
            while (getchar() != '\n'); // clear input buffer
            continue;
        }

        switch (choice) {
            case 1:
                printf("Enter amount to buy airtime: UGX ");
                scanf("%lf", &amount);
                buyAirtime(amount);
                break;

            case 2:
                checkAirtimeBalance();
                break;

            case 3:
                checkAccountBalance();
                break;

            case 4:
                printf("Enter amount to withdraw: UGX ");
                scanf("%lf", &amount);
                withdraw(amount);
                break;

            case 5:
                printf("Enter amount to deposit: UGX ");
                scanf("%lf", &amount);
                deposit(amount);
                break;

            case 6:
                printf("Exiting Airtel Money Menu. Goodbye!\n");
                break;

            default:
                printf("Invalid choice. Please select from the menu options.\n");
                break;
        }
    } while (choice != 6);

    return 0;
}
