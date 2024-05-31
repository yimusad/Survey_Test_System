import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ValidDate extends Question implements Serializable {
    private static final long serialVersionUID = 1L;

    ValidDate() {
        super();
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

    public SurveyResponseCorrectAnswer getTestResponse() {
        SurveyResponseCorrectAnswer enteredAnswers = new SurveyResponseCorrectAnswer();
        Scanner scanner = new Scanner(System.in);
        String line;
        line = scanner.nextLine();
        validateResponse(line);
        enteredAnswers.setAnswers(line);
        return enteredAnswers;
    }

    public void display() {
        System.out.println(super.prompt);
        System.out.println("A date should be entered in the following format: dd/mm/yyyy");
        System.out.println("\n");
    }

    public void create() {
        Scanner userQuestion = new Scanner(System.in);
        System.out.println("Enter the prompt of your valid date question: ");
        try {
            super.prompt = userQuestion.nextLine();
            if (prompt.length() <= 0 ) {
                System.out.println("Prompt cannot be empty.");
                create();
            }
            else {

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
        System.out.println("Do you wish to change this prompt? Yes or No");
        try {
            String choice = userEnters.nextLine();
            if (choice.equals("Yes") || choice.equals("No")) {
                if (choice.equals("Yes")) {
                    System.out.println("Enter the new prompt");
                    super.prompt = userEnters.nextLine();
                }
            } else {
                System.out.println("Make sure to enter either Yes or No");
                modify();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong, try again...  ");
            modify();
        }
    }


    // helpers
    public boolean validateResponse(String response) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        try {
            Date date = formatter.parse(response);
            return true;
        }
        catch (Exception e) {
            System.out.println("Your date does not have the proper format");
            getResponse();
            return false;
        }
    }

}

