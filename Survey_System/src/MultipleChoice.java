import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class MultipleChoice extends Question implements Serializable {
    protected ArrayList<String> choices = new ArrayList<String>();
    protected int numOfChoices;
    protected int maxAnswer;
    private static final long serialVersionUID = 1L;

    MultipleChoice() {
        super();
    }

    public void getResponse() {
        SurveyResponseCorrectAnswer enteredAnswers = new SurveyResponseCorrectAnswer();
        Scanner scanner = new Scanner(System.in);
        String answer;
        try {
            for (int x = 1; x <= maxAnswer; x++) {
                System.out.println("Enter your answer #" + (x));
                answer = scanner.nextLine();
                if (!validateResponse(answer)) {
                    break;
                }
                enteredAnswers.setAnswers(answer);
            }
            super.responses.add(enteredAnswers);
        }
        catch (Exception e) {
            System.out.println("Make sure to enter one letter at a time.");
            getResponse();
        }
    }


    public SurveyResponseCorrectAnswer getTestResponse() {
        SurveyResponseCorrectAnswer enteredAnswers = new SurveyResponseCorrectAnswer();
        Scanner scanner = new Scanner(System.in);
        String answer;
        try {
            for (int x = 1; x <= maxAnswer; x++) {
                System.out.println("Enter your answer #" + (x));
                answer = scanner.nextLine();
                if (!validateResponse(answer)) {
                    break;
                }
                enteredAnswers.setAnswers(answer);
            }
        }
        catch (Exception e) {
            System.out.println("Make sure to enter a one letter at a time");
            getTestResponse();
        }
        return enteredAnswers;
    }

    public void display() {
        System.out.println(super.prompt);
        System.out.println("You have to enter " + maxAnswer);
        displayChoices();
        System.out.println("\n");
    }

    public void addChoice(int number) {
        Scanner choice = new Scanner(System.in);
        for (int i = 0; i < number; i++) {
            System.out.println("Enter the choice for " + (i + 1) + ":");
            try {
                choices.add(choice.nextLine());
            }
            catch (Exception e) {
                System.out.println("Enter the answer properly");
                addChoice(number);
            }
        }
    }

    public void displayChoices() {
        char c = 'A';
        for (String choice : choices) {
            System.out.println(c + "." + choice);
            c++;
        }
    }

    public void create() {
        Scanner userQuestion = new Scanner(System.in);
        System.out.println("Enter the prompt for your multiple-choice question: ");
        try {
            super.prompt = userQuestion.nextLine();
            if (prompt.length() <= 0 ) {
                System.out.println("Make sure prompt is has words in it");
                create();
            }
            else {
                questionChoices();
                maxNumberOfResponses();
            }

        }
        catch (Exception e) {
            System.out.println("Enter the prompt properly");
            create();
        }
    }

    public void modify() {
        Scanner userEnters = new Scanner(System.in);
        System.out.println(super.prompt);
        System.out.println("Do you wish to modify this prompt? (Yes/No)");
        try {
            String choice = userEnters.nextLine();
            if (choice.equalsIgnoreCase("Yes") || choice.equalsIgnoreCase("No")) {
                if (choice.equalsIgnoreCase("Yes")) {
                    System.out.println("Enter the new prompt:");
                    super.prompt = userEnters.nextLine();
                }
                modifyLength();
            }
            else {
                System.out.println("Please enter Yes or No");
                modify();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong, try again..");
            modify();
        }
    }

    // helper methods
    public boolean validateResponse(String response) {
        char userChoice = response.toUpperCase().charAt(0); // convert to uppercase for case-insensitive comparison

        if (userChoice >= 'A' && userChoice < 'A' + choices.size() && response.length() == 1) {
            return true;
        }
        System.out.println("Please enter a valid character A-Z.");
        getResponse();
        return false;
    }
    private void questionChoices() {
        Scanner userQuestion = new Scanner(System.in);
        System.out.println("Enter the number of choices for your multiple choice question: ");
        try {
            this.numOfChoices = userQuestion.nextInt();
            if(utils.Validation.isIntBetweenInclusive(1,27, String.valueOf(numOfChoices))) {
                addChoice(numOfChoices);
            }
            else {
                System.out.println("Make sure to enter an int within the range of 1 and 27");
                questionChoices();
            }
        }
        catch (Exception e) {
            System.out.println("Make sure to enter an integer");
            questionChoices();
        }
    }

    private void maxNumberOfResponses() {
        Scanner userQuestion = new Scanner(System.in);
        System.out.println("Enter the max number of answers that the user can enter: ");
        try {
            this.maxAnswer = userQuestion.nextInt();
            if(utils.Validation.isIntBetweenInclusive(1,choices.size(), String.valueOf(numOfChoices))) {
            }
            else {
                System.out.println("Make sure to enter an int within the range of 1 and 27");
                maxNumberOfResponses();
            }
        }
        catch (Exception e) {
            System.out.println("Make sure to enter an integer");
            maxNumberOfResponses();
        }
    }

    private void modifyLength() {
        Scanner userEnters = new Scanner(System.in);
        System.out.println(maxAnswer);
        System.out.println("Do you wish to change the number of valid answers? Yes or No");
        try {
            String choice = userEnters.nextLine();
            if (choice.equalsIgnoreCase("Yes") || choice.equalsIgnoreCase("No")) {
                if (choice.equalsIgnoreCase("Yes")) {
                    System.out.println("Enter the new max answer");
                    this.maxAnswer = userEnters.nextInt();
                }
                modifyChoices();
            }
            else {
                System.out.println("Make sure to enter Yes or No");
                modifyLength();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong, try again..");
            modifyLength();
        }
    }

    public void modifyChoices() {
        Scanner userEnters = new Scanner(System.in);
        displayChoices();
        int number = 0;
        System.out.println("Do you wish to modify the choices? (Yes/No)");
        try {
            String choice = userEnters.nextLine();
            if (choice.equalsIgnoreCase("Yes") || choice.equalsIgnoreCase("No")) {
                if (choice.equalsIgnoreCase("Yes")) {
                    System.out.println("Which choice do you wish to modify?");
                    number = Integer.parseInt(userEnters.nextLine());
                    if (utils.Validation.isIntBetweenInclusive(1, choices.size(), String.valueOf(number))) {
                        System.out.println("Enter the new choice");
                        choices.set(number - 1, userEnters.nextLine());
                    } else {
                        System.out.println("Enter an int within bounds of total choices.");
                        modifyChoices();
                    }
                }
            }
            else {
                System.out.println("Enter Yes or No");
                modify();
            }
        }
        catch (Exception e) {
            System.out.println("An int is expected");
            modifyChoices();
        }
    }

}

