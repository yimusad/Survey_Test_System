import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class ShortAnswer extends Essay implements Serializable {
    private static final long serialVersionUID = 1L;

    ShortAnswer() {
        super();
    }

    public int getLength() {
        return super.length;
    }

    public void getResponse() {
        SurveyResponseCorrectAnswer enteredAnswers = new SurveyResponseCorrectAnswer();
        Scanner scanner = new Scanner(System.in);
        String line;
        line = scanner.nextLine();
        validateResponse(line);
        enteredAnswers.setAnswers(line);
        super.responses.add(enteredAnswers);
    }

    // since short answers can have wrong or right answers
    public SurveyResponseCorrectAnswer getTestResponse() {
        SurveyResponseCorrectAnswer enteredAnswers = new SurveyResponseCorrectAnswer();
        Scanner scanner = new Scanner(System.in);
        String line;
        try {
            line = scanner.nextLine();
            validateResponse(line);
            enteredAnswers.setAnswers(line);
        }
        catch (Exception e) {
            System.out.println("Make sure to enter it properly");
        }
        return enteredAnswers;
    }

    @Override
    public boolean setLength() {
        Scanner userQuestion = new Scanner(System.in);
        System.out.println("Enter the number length of your short answer question (1-200 characters): ");
        String input = userQuestion.nextLine();
        try {
            int inputLength = Integer.parseInt(input);
            if (utils.Validation.isIntBetweenInclusive(1, 200, String.valueOf(inputLength))) {
                this.length = inputLength;
                return true;
            } else {
                System.out.println("Please enter an int within the range of 1 and 200.");
                setLength();
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Make sure to enter an integer.");
            setLength();
            return false;
        }
    }

    public void display() {
        super.display();
    }


    @Override
    public void create() {
        Scanner userQuestion = new Scanner(System.in);
        System.out.println("Enter the prompt of your short answer question: ");
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




    // helpers
    public boolean validateResponse(String response) {
        return super.validateResponse(response);
    }


}
