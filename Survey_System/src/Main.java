import java.io.File;
import java.util.Scanner;

public class Main {

    private MainHelper mainHelper;

    public static void main(String[] args){
        Main main = new Main();
        main.mainHelper = new MainHelper();
        while (true) {
            main.startProgram();
        }
    }


    public void startProgram() {
        Scanner option = new Scanner(System.in);
        System.out.println("1) Survey");
        System.out.println("2) Test");
        System.out.println("Please enter 3 if you would like to exit :)");


        String userChoice = option.nextLine();
        while (true) {
            if (userChoice.equals("1")) {
                displaySurveyMenu();
            }
            else if (userChoice.equals("2")) {
                displayTestMenu();
            }
            else if (userChoice.equals("3")) {
                System.out.println("Exiting...");
                System.exit(0);
            }
            else {
                System.out.println("Invalid choice, please choose again");
                startProgram();
            }
        }
    }

    public void displaySurveyMenu() {
        Scanner choice = new Scanner(System.in);

        System.out.println("1) Create a new Survey");
        System.out.println("2) Display an existing Survey");
        System.out.println("3) Load an existing Survey");
        System.out.println("4) Save the current Survey");
        System.out.println("5) Take the current Survey");
        System.out.println("6) Modify the current Survey");
        System.out.println("7) Tabulate a Survey");
        System.out.println("8) Return to previous menu ");
        System.out.print("Please select an option: ");

        String userChoice = choice.nextLine();

        if (userChoice.equals("1")) {
            mainHelper.createSurvey();
        }
        else if (userChoice.equals("2")) {
            mainHelper.displaySurvey();
        }
        else if (userChoice.equals("3")) {
            mainHelper.loadSurvey();
        }
        else if (userChoice.equals("4")) {
            mainHelper.saveSurvey();
        }
        else if (userChoice.equals("5")) {
            mainHelper.takeSurvey();
        }
        else if (userChoice.equals("6")) {
            mainHelper.modifySurvey();
        }
        else if (userChoice.equals("7")){
            mainHelper.tabulateSurvey();
        }
        else if (userChoice.equals("8")) {
            System.out.println("You have chosen to return to previous menu.");
            startProgram();
        }
        else {
            System.out.println("Invalid choice, please choose again.");
            displaySurveyMenu();
        }
    }

    public void displayTestMenu() {
        Scanner choice = new Scanner(System.in);

        System.out.println("1) Create a new Test");
        System.out.println("2) Display an existing Test without correct answers");
        System.out.println("3) Display an existing Test with correct answers");
        System.out.println("4) Load an existing Test");
        System.out.println("5) Save the current Test");
        System.out.println("6) Take the current Test");
        System.out.println("7) Modify the current Test");
        System.out.println("8) Tabulate a Test");
        System.out.println("9) Grade a Test");
        System.out.println("10) Return to previous menu");
        System.out.print("Please select an option: ");


        String userChoice = choice.nextLine();

        if (userChoice.equals("1")) {
            mainHelper.createTest();
        }
        else if (userChoice.equals("2")) {
            mainHelper.displayTest();
        }
        else if (userChoice.equals("3")) {
            mainHelper.displayTestWithAnswers();
        }
        else if (userChoice.equals("4")) {
            mainHelper.loadTest();
        }
        else if (userChoice.equals("5")) {
            mainHelper.saveTest();
        }
        else if (userChoice.equals("6")) {
            mainHelper.takeTest();
        }
        else if (userChoice.equals("7")) {
            mainHelper.modifyTest();
        }
        else if (userChoice.equals("8")) {
            mainHelper.tabulateTest();
        }
        else if (userChoice.equals("9")) {
            mainHelper.gradeTest();
        }
        else if (userChoice.equals("10")) {
            System.out.println("You have chosen to return to the previous menu.");
            startProgram();
        }
        else {
            System.out.println("Invalid choice, please choose again.");
            displayTestMenu();
        }

    }







}
