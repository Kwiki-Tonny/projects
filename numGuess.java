/* import java.util.Scanner;
import java.util.Random;
public class numGuess {
    //1. function to check if guess is equivalent to randomNumber
    static void numChecker(int guess, int randomNumber, int attempts){
        //Check whether number is in bound limiit
        if(guess<1 || guess>100){
            System.out.println("Guess is out of bounds");
        }
        //check if guess is correct
        else if(guess==randomNumber){
            System.out.println("You guessed right in "+ attempts +" attempts." );
        }
        //check if guess is too high ortoo low
        else if(guess>randomNumber){
            System.out.println("Too high");
        }else if(guess<randomNumber){
            System.out.println("Too low");
        }
    }

    //main function to capture guess, generate randomNumber
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        //generate random number
        int randomNumber = random.nextInt(100) + 1;
        int attempts = 0;
        boolean guessCorrectly = false;

        while(!guessCorrectly){
            System.out.print("Enter your guess: ");
            int guess = scanner.nextInt();
            attempts++;

            //check guess
            numChecker(guess, randomNumber, attempts);

            //udate condition
            if(guess==randomNumber){
                guessCorrectly = true;
            }
        }
        scanner.close();
    }

}
 */

import java.util.Scanner;
import java.util.Random;

public class numGuess {
    
    // ANSI color codes for terminal colors
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String BOLD = "\033[1m";
    
    static void displayTitle() {
        System.out.println(CYAN + "╔════════════════════════════════════════╗");
        System.out.println("║" + BOLD + "         NUMBER GUESSING GAME" + RESET + CYAN + "         ║");
        System.out.println("║               (1-100)" + RESET + CYAN + "              ║");
        System.out.println("╚════════════════════════════════════════╝" + RESET);
        System.out.println();
    }
    
    static void displayInstructions() {
        System.out.println(YELLOW + "🎯 " + BOLD + "HOW TO PLAY:" + RESET);
        System.out.println("┌────────────────────────────────────────┐");
        System.out.println("│ " + GREEN + "✓" + RESET + " I've picked a random number between 1-100 │");
        System.out.println("│ " + GREEN + "✓" + RESET + " You need to guess what it is             │");
        System.out.println("│ " + GREEN + "✓" + RESET + " I'll tell you if you're too high/low      │");
        System.out.println("│ " + GREEN + "✓" + RESET + " Try to guess in as few attempts as possible│");
        System.out.println("└────────────────────────────────────────┘" + RESET);
        System.out.println();
    }
    
    static void displayGameBoard(int attempts, int minGuess, int maxGuess) {
        System.out.println();
        System.out.println(BLUE + "╔════════════════════════════════════════╗");
        System.out.println("║" + BOLD + "           GAME STATS" + RESET + BLUE + "                ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║   Attempts: " + YELLOW + String.format("%-28d", attempts) + RESET + BLUE + "║");
        System.out.println("║   Range:    " + PURPLE + minGuess + " - " + maxGuess + RESET + BLUE + "                      ║");
        System.out.println("╚════════════════════════════════════════╝" + RESET);
        System.out.println();
    }
    
    static void numChecker(int guess, int randomNumber, int attempts) {
        System.out.println();
        
        if (guess < 1 || guess > 100) {
            System.out.println(RED + "✗ " + BOLD + "OUT OF BOUNDS!" + RESET);
            System.out.println("   Please enter a number between " + GREEN + "1" + RESET + " and " + GREEN + "100" + RESET);
        } 
        else if (guess == randomNumber) {
            System.out.println(GREEN + "╔════════════════════════════════════════╗");
            System.out.println("║" + BOLD + "          🎉 CONGRATULATIONS! 🎉" + RESET + GREEN + "        ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║                                        ║");
            System.out.println("║   You guessed the number " + YELLOW + randomNumber + RESET + GREEN + " in       ║");
            System.out.println("║           " + YELLOW + attempts + RESET + GREEN + " attempt" + (attempts == 1 ? "" : "s") + "!                ║");
            System.out.println("║                                        ║");
            
            // Display performance rating
            String rating;
            if (attempts <= 5) {
                rating = PURPLE + "★ ★ ★ ★ ★" + RESET + GREEN + " (Legendary!)";
            } else if (attempts <= 10) {
                rating = YELLOW + "★ ★ ★ ★ ☆" + RESET + GREEN + " (Excellent!)";
            } else if (attempts <= 15) {
                rating = CYAN + "★ ★ ★ ☆ ☆" + RESET + GREEN + " (Good job!)";
            } else if (attempts <= 20) {
                rating = BLUE + "★ ★ ☆ ☆ ☆" + RESET + GREEN + " (Not bad!)";
            } else {
                rating = RESET + "★ ☆ ☆ ☆ ☆" + RESET + GREEN + " (Keep trying!)";
            }
            
            System.out.println("║   Performance: " + rating + GREEN + "  ║");
            System.out.println("║                                        ║");
            System.out.println("╚════════════════════════════════════════╝" + RESET);
        } 
        else if (guess > randomNumber) {
            System.out.println(RED + "📉 " + BOLD + "TOO HIGH!" + RESET);
            System.out.println("   Try a " + CYAN + "lower" + RESET + " number");
            
            // Visual indicator
            int difference = guess - randomNumber;
            if (difference > 30) {
                System.out.println("   " + RED + "↙↙↙ Way too high! You're way above" + RESET);
            } else if (difference > 15) {
                System.out.println("   " + YELLOW + "↙ Still quite high" + RESET);
            } else {
                System.out.println("   " + GREEN + "↘ Getting close! Just a bit high" + RESET);
            }
        } 
        else if (guess < randomNumber) {
            System.out.println(BLUE + "📈 " + BOLD + "TOO LOW!" + RESET);
            System.out.println("   Try a " + CYAN + "higher" + RESET + " number");
            
            // Visual indicator
            int difference = randomNumber - guess;
            if (difference > 30) {
                System.out.println("   " + RED + "↗↗↗ Way too low! You're way below" + RESET);
            } else if (difference > 15) {
                System.out.println("   " + YELLOW + "↗ Still quite low" + RESET);
            } else {
                System.out.println("   " + GREEN + "↖ Getting close! Just a bit low" + RESET);
            }
        }
        
        // Add separator line
        if (guess != randomNumber) {
            System.out.println(YELLOW + "──────────────────────────────────────────" + RESET);
        }
    }
    
    static void displayGuessHistory(int[] guesses, int attemptCount) {
        if (attemptCount > 0) {
            System.out.println(PURPLE + "\n📊 " + BOLD + "GUESS HISTORY:" + RESET);
            System.out.print("   ");
            for (int i = 0; i < attemptCount; i++) {
                if (i > 0 && i % 5 == 0) {
                    System.out.print("\n   ");
                }
                System.out.print("[" + guesses[i] + "] ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        boolean playAgain = true;
        int bestScore = Integer.MAX_VALUE;
        
        while (playAgain) {
            // Clear screen (works on most terminals)
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            // Display title and instructions
            displayTitle();
            displayInstructions();
            
            // Generate random number
            int randomNumber = random.nextInt(100) + 1;
            int attempts = 0;
            boolean guessedCorrectly = false;
            int[] guessHistory = new int[100]; // Store up to 100 guesses
            int minGuess = 1;
            int maxGuess = 100;
            
            System.out.println(GREEN + "🔢 " + BOLD + "I'm thinking of a number between 1-100..." + RESET);
            System.out.println("   Can you guess what it is?\n");
            
            while (!guessedCorrectly) {
                // Display game board
                displayGameBoard(attempts, minGuess, maxGuess);
                
                // Display guess history
                if (attempts > 0) {
                    displayGuessHistory(guessHistory, attempts);
                }
                
                // Get user input with validation
                System.out.print(YELLOW + BOLD + "\n🎮 YOUR GUESS: " + RESET);
                
                try {
                    int guess = scanner.nextInt();
                    attempts++;
                    guessHistory[attempts - 1] = guess; // Store guess
                    
                    // Check the guess
                    numChecker(guess, randomNumber, attempts);
                    
                    // Update guess range hints
                    if (guess > randomNumber && guess < maxGuess) {
                        maxGuess = guess - 1;
                    } else if (guess < randomNumber && guess > minGuess) {
                        minGuess = guess + 1;
                    }
                    
                    // Check if correct
                    if (guess == randomNumber) {
                        guessedCorrectly = true;
                        
                        // Update best score
                        if (attempts < bestScore) {
                            bestScore = attempts;
                            System.out.println(GREEN + "\n🏆 " + BOLD + "NEW BEST SCORE!" + RESET + 
                                             " (Previous best: " + (bestScore - 1 == Integer.MAX_VALUE ? "None" : bestScore - 1) + " attempts)");
                        }
                        
                        // Ask if user wants to play again
                        System.out.print(YELLOW + "\n🔄 Play again? (y/n): " + RESET);
                        String response = scanner.next().toLowerCase();
                        
                        if (!response.equals("y") && !response.equals("yes")) {
                            playAgain = false;
                            System.out.println(CYAN + "\n👋 Thanks for playing!" + RESET);
                            System.out.println("   Your best score: " + GREEN + bestScore + RESET + " attempts");
                        }
                    }
                } catch (Exception e) {
                    System.out.println(RED + "⚠  Please enter a valid number!" + RESET);
                    scanner.nextLine(); // Clear invalid input
                }
            }
        }
        
        scanner.close();
    }
}