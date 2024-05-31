import utils.SerializationHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class Test extends Survey {
    private static final long serialVersionUID = 1L;
    public ArrayList<SurveyResponseCorrectAnswer> correctResponses = new ArrayList<SurveyResponseCorrectAnswer>();



    Test(){
    }

    @Override
    public void create() {
        super.create();
        System.out.println("Please enter the expected response for each question:");
        for (int x = 0; x < questions.size(); x++) {
            System.out.println(x + 1 + ".");
            questions.get(x).display();
            enterExpectedResponse(questions.get(x));
        }
    }


    // to add the correct answer during question creation
    private void enterExpectedResponse(Question question ){
        SurveyResponseCorrectAnswer answer = question.getTestResponse();
        correctResponses.add(answer);
    }

    private void modifyExpectedResponse(Question question, int questionNumber){
        SurveyResponseCorrectAnswer answer = question.getTestResponse();
        correctResponses.set(questionNumber, answer);

    }

    public void displayWithAnswers() {
        for (int x = 0; x < questions.size(); x++) {
            System.out.println(x + 1 + ".");
            questions.get(x).display();
            System.out.println("The correct answer is:");
            correctResponses.get(x).display();
        }
    }


    public void modify(int number) {
        Scanner userEnters = new Scanner(System.in);
        System.out.println("Would you like to modify the question? Yes/No");
        try {
            String choice = userEnters.nextLine();
            if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")) {
                if (choice.equalsIgnoreCase("yes")) {
                    questions.get(number - 1).modify();
                }
            }
        }
        catch (Exception e){
            System.out.println("Make sure to enter either Yes or No");
            modify(number);
        }

        correctResponses.get(number - 1).display();
        System.out.println("Would you like to modify the answer? Yes/No");
        try {
            String choice = userEnters.nextLine();
            if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")) {
                if (choice.equalsIgnoreCase("yes")) {
                    System.out.println("Enter your new answer");
                    modifyExpectedResponse(questions.get(number - 1), number - 1);
                    System.out.println("New answer added!");
                }
            }
        } catch (Exception e) {
            System.out.println("Make sure to enter either Yes or No");
            modify(number);
        }
    }


    public void grade() {
        int totalQuestions = correctResponses.size();
        float grade = calculateGrade(totalQuestions, 100.0f / totalQuestions);
        int numEssay = 0;

        for (int x = 0; x < totalQuestions; x++) {
            if (correctResponses.get(x).displayAnswer().equals("")) {
                numEssay += 1;
            }
        }

        if (numEssay != 0) {
            System.out.println("You received an " + grade + " on the test. The test was worth 100 points, but only " + grade + " points could be autograded because there was " + numEssay + " essay question(s) out of " + totalQuestions + " total questions.");
        } else {
            System.out.println("You received an " + grade + " on the test.");
        }
    }


    // helper method
    private float calculateGrade(int totalQuestions, float questionValue) {
        float grade = 0;
        for (int i = 0; i < totalQuestions; i++) {
            if (correctResponses.get(i).displayAnswer().equals(questions.get(i).responses.get(0).displayAnswer())) {
                grade += questionValue;
            }
        }
        return grade;
    }


}

