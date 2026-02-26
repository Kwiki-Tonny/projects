import java.util.Scanner;

public class ShoppingList {
      public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        // an array that captures five products maximumly
        String[] shoppingList = new String[5];
        int itemCount = 0;

        System.out.println("-----Welcome To Our Shopping List Creator-----");
        System.out.println("Enter upto five items and type 'done' when you are done entering the products");

        //Loop to collect the items
        for(int i=0; i<=5; i++){
          System.out.print("Enter item" + (i  + 1) + ":");
          String input = scanner.nextLine();

          //check if user wants to stop
          if(input.equalsIgnoreCase("done")){
            break;
          }

          shoppingList[i] = input;//add itms on the shopping list
          itemCount++;            //keep trck of how many were added
        }

        //print the entire list out
        System.out.println("\n-----Here is the full shopping list-----");
        for(int i=0; i<itemCount; i++){
          System.out.println(shoppingList[i]);
        }

        //print the total count of items in the shopping list
        System.out.print("\nThe toatl number of items is" + " "+ itemCount);

        //Search Toolfor items in the shopping list
        System.out.println("-----The Search Tool----");
        System.out.println("Enter item in search");
        String searchItem = scanner.nextLine();

        boolean found = false;

        //Loop through the list for the item
        for(int i=0; i<itemCount; i++){
          if(shoppingList[i].equalsIgnoreCase(searchItem)){
            System.out.println("Result: "+searchItem+" is found at position "+i);
            found = true;
          }
        }

        if(!found){
          System.out.println("Item does not exist in your list");
        }

        scanner.close();
      }
  }