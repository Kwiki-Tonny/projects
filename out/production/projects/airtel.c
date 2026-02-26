/*
 * =====================================================================================
 *
 *Author: Ssenkuba Tonny Kwikiriza
 *
 * Filename:  airtel.c
 *
 * Description:  A C program to demonstrate how airtel money buying airtime works.
 *
 * Purpose:  This example shows how to code something that resembles airtel money system
 run it for academic purposes,we are focusing on turning TaD assigments to code.
 *
 * =====================================================================================
 */


#include <stdio.h>
#include <math.h>
#include<string.h>

//global variables to declare airtime and account balance
double accountBalance = 10000;
double airtimeAccount = 2000;
char userPIN[5] = "2005";
char dial[6] = "*185#";

//function that checks account balance
void checkAccountBalance(){
    printf("Your current airtime balance is Ugx %.2lf\n", accountBalance);
}

//function that checks airtime balance
void checkAirtimeBalance(){
    printf("Your current airtime balance is Ugx %.2lf\n", airtimeAccount);
}

//function that buys airtime//
double buyAirtime(double amount){
    if(amount<=accountBalance){
        char userPINInput[5] = " ";
        int attempts = 0;

    //the while loop is to validte PIN
        while(strcmp(userPINInput, userPIN) != 0)
        printf("Please enter your PIN to complete transaction\n");
        scanf("%5s", &userPINInput);
        attempts++;

    //this if validates number of attempts before breaking and its where the actual computation happens
        if(attempts<=4){
        accountBalance-=amount;
        airtimeAccount+=amount;
        printf("Your new Airtime Balance is Ugx %.2lf and your new Account Balance is Ugx %.2lf.
            Thank you for Choosing Airtel Money Services\n", airtimeAccount, accountBalance); 
        }else{
            printf("Maximum attempts reached. Try again later\n");
            break;
        }
      
    }else{
        printf("You have insufficient Balance on your Account to complete this transaction.\n");
    }
    return airtimeAccount+=amount;
}

//function that deposits money
double deposit(double amount){
    accountBalance+=amount;
     printf("Deposit successful. Your new balance is: Ugx %.2lf\n", accountBalance);
}
//Function that withdraws money
double withdraw(double amount) {
 if (amount <= balance) {
 accountBalance-= amount;
 printf("Withdrawal successful. Your new balance is: Ugx %.2lf\n", accountBalance);
 } else {
 printf("Insufficient funds. Cannot withdraw Ugx %.2lf\n", amount);
 }
}
//main function unit
int main(){

    int choice;
    double amount; 

    char userDial[4] = " ";
    printf("Dial *185# to access menu\n");
    scanf("%4s", &userDial);

    if(strcmp(userDial, dial) != 0){
       printf("Invalid MMI Code\n");
    }else {
        printf("Invalid MMI Code");
    }

    do{

        //This is a user's guide to select the right choice
        printf("AIRTEL MONEY Menu\n");
        printf("1. Buy Airtime\n");
        printf("2. Check Airtime Balance\n");
        printf("3. Check Account Balance\n");
        printf("4. Withdraw Money\n");
        printf("5. Deposit");
        printf("6. Cancel\n");
        printf("Enter your choice to continue: ");

        //this is an error handling code|just in-case user enters something is not an integer|it will be denied|it aslo takes in user input
        if (scanf("%d", &choice) != 1) {
        printf("Invalid input.");
        // Clear the input buffer|before the code re-runs
        while (getchar() != '\n');
        continue;
    }

// switch code to navigate the menu
    switch(choice){
        case 1:
        printf("Enter amount: Ugx");
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
        printf("Enter amount to withdraw: Ugx");
        scanf("%lf", &amount);
        withdraw(amount);

        case 5:
        printf("Enter amount to deposit: Ugx");
        scanf("%lf", &amount);
        deposit(amount);
    }

    }while(choice !=6);

    return 0;
}

