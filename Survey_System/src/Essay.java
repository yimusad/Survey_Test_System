import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Essay extends Question implements Serializable {
    public int length;
    private static final long serialVersionUID = 1L;

    Essay() {
        super();
    }

    public int getLength() {
        return this.length;
    }

    public boolean setLength() {
        Scanner userQuestion = new Scanner(System.in);
        System.out.println("Enter the length (in characters) of your essay question response: ");
        String input = userQuestion.nextLine();
        try {
            int inputLength = Integer.parseInt(input);
            if (utils.Validation.isIntBetweenInclusive(200, Integer.MAX_VALUE, String.valueOf(inputLength))) {
                this.length = inputLength;
                return true;
            } else {
                System.out.println("The length must be at least 200 characters");
                setLength();
                return false;

            }
        } catch (NumberFormatException e) {
            System.out.println("Enter an integer.");
            setLength();
            return false;
        }
    }

    public void getResponse() {
        SurveyResponseCorrectAnswer enteredAnswers = new SurveyResponseCorrectAnswer();
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tokens = new ArrayList<>();
        System.out.println("Enter your response here (press Enter twice to finish):");

        String line;
        while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
            enteredAnswers.setAnswers(line);
            tokens.add(line);
        }

        String answer = String.join("\n", tokens);
        if (validateResponse(answer)) {
            enteredAnswers.setAnswers(answer);
            super.responses.add(enteredAnswers);
        }
    }

    // essay questions are open ended and cannot be graded
    public SurveyResponseCorrectAnswer getTestResponse() {
        SurveyResponseCorrectAnswer answer = new SurveyResponseCorrectAnswer();
        answer.setAnswers("");
        return answer;
    }

    public void display() {
        System.out.println(super.prompt);
        System.out.println("Maximum length is: " + this.length);
        System.out.println("\n");
    }

    public void create() {
        Scanner userQuestion = new Scanner(System.in);
        System.out.println("Enter the prompt of your essay question: ");
        try {
            super.prompt = userQuestion.nextLine();
            if (prompt.length() <= 0 ) {
                System.out.println("The prompt cannot be empty.");
                create();
            }
            else {
                setLength();
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong, try again...");
            create();
        }
    }

    public void modify() {
        Scanner userEnters = new Scanner(System.in);

        // modify the prompt
        System.out.println(super.prompt);
        System.out.println("Do you wish to modify this prompt? (Yes/No)");
        String choice = userEnters.nextLine().trim();
        if ("Yes".equalsIgnoreCase(choice)) {
            System.out.println("Enter the new prompt:");
            super.prompt = ""; // clear the current prompt
            while (super.prompt.isEmpty()) {
                super.prompt = userEnters.nextLine().trim();
                if (super.prompt.isEmpty()) {
                    System.out.println("The prompt cannot be empty. Please enter the prompt again.");
                }
            }
        }

        // modify the length
        System.out.println("Current length: " + length);
        System.out.println("Do you wish to modify the length of the response? (Yes/No)");
        choice = userEnters.nextLine().trim();
        if ("Yes".equalsIgnoreCase(choice)) {
            System.out.println("Enter the new length:");
            while (!setLength()) {
                System.out.println("Please enter a valid length between 1 and " + this.length);
            }
        }
    }

    // helpers
    public boolean validateResponse(String answer) {
        //check that the answer is not empty and does not exceed the maximum length set at creation
        if (answer.length() > 0 && answer.length() <= this.length) {
            return true;
        } else {
            if (answer.length() == 0) {
                System.out.println("The answer cannot be empty. Please provide a response.");
                getResponse();
            } else {
                System.out.println("Please make sure your answer does not exceed the maximum length of " + this.length + " characters.");
                getResponse();
            }
            return false;
        }
    }

}
