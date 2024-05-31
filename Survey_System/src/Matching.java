import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Matching extends Question implements Serializable {
    private static final long serialVersionUID = 1L;
    public int options;
    public ArrayList<String> leftSet = new ArrayList<String>(); // options to match to
    public ArrayList<String> rightSet = new ArrayList<String>(); // options to be matched

    Matching() {
        super();
    }


    public void getResponse() {
        SurveyResponseCorrectAnswer enteredAnswers = new SurveyResponseCorrectAnswer();
        Scanner scanner = new Scanner(System.in);
        String line;
        try {
            for (int x = 1; x <= leftSet.size(); x++) {
                System.out.println("Enter your answer #" + (x));
                line = scanner.nextLine();
                if (!validateResponse(line)) {
                    break;
                }
                enteredAnswers.setAnswers(line);
            }
            super.responses.add(enteredAnswers);
        }
        catch (Exception e) {
            System.out.println("Make sure to enter a one letter at a time");
            getResponse();
        }
    }


    public SurveyResponseCorrectAnswer getTestResponse() {
        SurveyResponseCorrectAnswer enteredAnswers = new SurveyResponseCorrectAnswer();
        Scanner scanner = new Scanner(System.in);
        String line;
        // double check that leftSet is right
        try {
            for (int x = 1; x <= leftSet.size(); x++) {
                System.out.println("Enter your answer #" + (x));
                line = scanner.nextLine();
                if (!validateResponse(line)) {
                    break;
                }
                enteredAnswers.setAnswers(line);
            }
        }
        catch (Exception e) {
            System.out.println("Make sure to enter a one letter at a time");
            getTestResponse();
        }
        return enteredAnswers;
    }

    // format left and right side into 2 neat columns
    public void display() {
        System.out.print(prompt + "(For example: A1)"+"\n");
        for (int i = 0; i < leftSet.size() && i < rightSet.size(); i++) {
            char letterLabel = (char) ('A' + i);
            System.out.print(letterLabel + ") ");
            System.out.printf("%-20s", leftSet.get(i));
            System.out.print((i + 1) + ") ");
            System.out.printf("%-20s", rightSet.get(i));
            System.out.println();
        }
    }

    public void create() {
        Scanner userQuestion = new Scanner(System.in);
        System.out.println("Enter the prompt for your matching question: ");
        try {
            super.prompt = userQuestion.nextLine();
            if (prompt.length() <= 0 ) {
                System.out.println("The prompt cannot be empty.");
                create();
            }
            else {
                addOptions();
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong, try again...");
            create();
        }
    }

    public void modify() {
        Scanner userEnters = new Scanner(System.in);
        System.out.println(super.prompt);
        System.out.println("Do you wish to modify this prompt? (Yes/No)");

        try {
            String choice = userEnters.nextLine().trim();
            if ("Yes".equalsIgnoreCase(choice)) {
                System.out.println("Enter the new prompt:");
                super.prompt = userEnters.nextLine().trim();
                if (super.prompt.isEmpty()) {
                    System.out.println("The prompt cannot be empty. Please enter the prompt again.");
                    modify();
                } else {
                    modifyLeftChoices();
                }
            } else if ("No".equalsIgnoreCase(choice)) {
                modifyLeftChoices();
            } else {
                System.out.println("Invalid input. Please enter Yes or No.");
                modify();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong, try again...");
            modify();
        }
    }

    private void displayChoices() {
        for (int x = 0; x < leftSet.size(); x++) {
            System.out.println((x+1) + "." + leftSet.get(x));
        }
    }

    private void displayAnswers() {
        for (int x = 0; x < rightSet.size(); x++) {
            System.out.println((x+1)+ "." + rightSet.get(x));
        }
    }


    // helpers
    private void addOptions() {
        Scanner userQuestion = new Scanner(System.in);
        System.out.println("Enter the number of choices for your matching question: ");
        try {
            this.options = userQuestion.nextInt();
            if(utils.Validation.isIntBetweenInclusive(1,27, String.valueOf(options))) {
                addLeftOptions(options);
                addRightOptions(options);
            }
            else {
                System.out.println("Enter an int within the range b/w 1 and 27");
                addOptions();
            }
        }
        catch (Exception e) {
            System.out.println("Enter an integer.");
            addOptions();
        }
    }
    private void addLeftOptions(int number) {
        Scanner userChoice = new Scanner(System.in);
        for (int i = 0; i < number; i++) {
            System.out.println("Enter the choice for " + (i + 1) + ":");
            try {
                leftSet.add(userChoice.nextLine());
            }
            catch (Exception e) {
                System.out.println("Enter the answer properly");
                addLeftOptions(number);
            }
        }
    }
    private void addRightOptions(int number) {
        Scanner userAnswer = new Scanner(System.in);
        for (int i = 0; i < number; i++) {
            System.out.println("Enter the answer for " + leftSet.get(i) + ":");
            try {
                rightSet.add(userAnswer.nextLine());
            }
            catch (Exception e) {
                System.out.println("Enter the answer properly");
                addRightOptions(number);
            }
        }
    }
    public void modifyLeftChoices() {
        Scanner userEnters = new Scanner(System.in);
        displayChoices();
        System.out.println("Do you wish to edit the left set of options? (Yes/No)");
        try {
            String choice = userEnters.nextLine();
            if (choice.equalsIgnoreCase("Yes") || choice.equalsIgnoreCase("No")) {
                if (choice.equalsIgnoreCase("Yes")) {
                    System.out.println("Which option do you wish to modify? (enter an int)");
                    int number = Integer.parseInt(userEnters.nextLine());
                    if (utils.Validation.isIntBetweenInclusive(1, leftSet.size(), String.valueOf(number))) {
                        System.out.println("Enter the new option:");
                        leftSet.set(number - 1, userEnters.nextLine());
                    }
                }
                modifyRightChoices();
            } else {
                System.out.println("Enter Yes or No.");
                modifyLeftChoices();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong, try again...");
            modifyLeftChoices();
        }
    }
    private void modifyRightChoices() {
        Scanner userEnters = new Scanner(System.in);
        displayAnswers();
        System.out.println("Do you wish to edit the right set of options? (Yes/No)");
        try {
            String choice = userEnters.nextLine();
            if (choice.equalsIgnoreCase("Yes") || choice.equalsIgnoreCase("No")) {
                if (choice.equalsIgnoreCase("Yes")) {
                    System.out.println("Which option do you wish to modify? (enter an int)");
                    int number = Integer.parseInt(userEnters.nextLine());
                    if (utils.Validation.isIntBetweenInclusive(1, leftSet.size(), String.valueOf(number))) {
                        System.out.println("Enter the new option:");
                        rightSet.set(number - 1, userEnters.nextLine());
                    }
                }
            } else {
                System.out.println("Enter Yes or No.");
                modifyRightChoices();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong, try again...");
            modifyRightChoices();
        }
    }

    public boolean validateResponse(String response) {
        ArrayList<Character> validAnswers = new ArrayList<Character>();
        ArrayList<Integer> validAnswer = new ArrayList<Integer>();
        char c = 'A';
        for (int x = 0; x < leftSet.size(); x++) {
            validAnswers.add(c);
            c++;
        }
        for (int x = 1; x <= leftSet.size(); x++) {
            validAnswer.add(x);
        }
        try {
            if (validAnswers.contains(response.charAt(0)) && validAnswer.contains(Character.getNumericValue(response.charAt(1))) && response.length() == 2) {
                return true;
            } else {
                System.out.println("Please make sure you only enter capital letters as your answer");
                getResponse();
                return false;
            }
        }
        catch (Exception e)
        {
            System.out.println("Please make sure you only enter capital letters as your answer");
            getResponse();
            return false;
        }
    }


}
