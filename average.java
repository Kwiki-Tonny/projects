import java.util.Scanner;

public class average {
    //function for grading students

    static char gradeFunction(double grade){
        if(grade>=90){
        return 'A';
        }else if(grade>=80){
            return 'B';
        }else if(grade>=70){
            return 'C';
        }else if(grade>=60){
            return 'D';
        }else return 'F';
    }    

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        //Capture user input about how many grades they want to enter and compute avearge for
        System.out.println("How many grades to you want to compute for(between 1 to 5)");
        int count = scanner.nextInt();

        //Validate input
        if(count<1 || count>5){
            System.out.println("Value out of bounds");
            scanner.close();
            return; //Exit
        }

        double sum =0.0;

        //Read and compute each input
        for(int i=1; i<=count; i++){
            System.out.println("Enter grade: "+i+" ");
            double grade = scanner.nextDouble();
            sum+=grade;
        }

        double grade = sum/count;

        System.out.println("Average: "+ grade);
        System.out.println("Grade value: "+ gradeFunction(grade));

        scanner.close();
    }
}


