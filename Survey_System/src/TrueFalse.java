import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class TrueFalse extends MultipleChoice implements Serializable {
    private static final long serialVersionUID = 1L;

    TrueFalse() {
        super();
    }

    public void display() {
        super.display();
    }

    public void getResponse() {
        super.getResponse();
    }

    public SurveyResponseCorrectAnswer getTestResponse() {
        return super.getTestResponse();
    }




    @Override
    public void create() {
        Scanner userQuestion = new Scanner(System.in);
        System.out.println("Enter the prompt for your true and false question: ");
        try {
            super.prompt = userQuestion.nextLine();
            if (prompt.length() <= 0 ) {
                System.out.println("Prompt cannot be empty");
                create();
            }
            else {
                super.choices.add("True");
                super.choices.add("False");
                super.maxAnswer = 1;
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong, try again.");
            create();
        }
    }

    @Override
    public void modify() {
        Scanner userEnters = new Scanner(System.in);
        System.out.println(super.prompt);
        System.out.println("Do you wish to change this prompt? (Yes/No)");
        try {
            String choice = userEnters.nextLine();
            if (choice.equalsIgnoreCase("Yes") || choice.equalsIgnoreCase("No")) {
                if (choice.equalsIgnoreCase("Yes")) {
                    System.out.println("Enter the new prompt");
                    super.prompt = userEnters.nextLine();
                }
            } else {
                System.out.println("Please enter Yes or No");
                modify();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong, try again..");
            modify();
        }
    }

    // helper
    @Override
    public boolean validateResponse(String response) {
        if (response.equalsIgnoreCase("A") || response.equalsIgnoreCase("B")){
            return true;
        }
        else {
            System.out.println("Something went wrong, try again...");
            getResponse();
            return false;
        }
    }

}
