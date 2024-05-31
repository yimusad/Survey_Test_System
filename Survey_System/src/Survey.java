import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import utils.SerializationHelper;
import utils.SerializationHelper;

import java.io.FilenameFilter;

public class Survey implements Serializable {
    private static final long serialVersionUID = 1L;
    protected ArrayList<Question> questions = new ArrayList<Question>();
    public String name;
    private boolean checkExit;

    Survey() {
    }

    public String getName() {
        return name;
    }

    public void setName(String surveyName) {
        this.name = surveyName;
    }



    public void create() {
        Scanner name = new Scanner(System.in);
        System.out.println("Enter a name for this survey/test:");
        try {
            this.name = name.nextLine();
            if (this.name.length() <= 0 ) {
                System.out.println("The prompt cannot be empty.");
                create();
            }
            else {
                System.out.println("You have chosen to name this survey " + this.name);
                while (!checkExit) {
                    addQuestion();
                }
                checkExit = false;
            }

        }
        catch (Exception e) {
            System.out.println("Something went wrong, try again...");
            create();
        }
    }


    public void addQuestion() {
        Scanner question = new Scanner(System.in);
        String questionChoice;

        System.out.println("1. Add a new T/F question");
        System.out.println("2. Add a new multiple-choice question");
        System.out.println("3. Add a new short answer question");
        System.out.println("4. Add a new essay question");
        System.out.println("5. Add a new date question");
        System.out.println("6. Add a new matching question");
        System.out.println("7. Return to previous menu");

        questionChoice = question.nextLine();
        if (questionChoice.equals("1")) {
            TrueFalse trueFalse = new TrueFalse();
            trueFalse.create();
            this.questions.add(trueFalse);
        } else if (questionChoice.equals("2")) {
            MultipleChoice multipleChoice = new MultipleChoice();
            multipleChoice.create();
            this.questions.add(multipleChoice);
        } else if (questionChoice.equals("3")) {
            ShortAnswer shortAnswer = new ShortAnswer();
            shortAnswer.create();
            this.questions.add(shortAnswer);
        } else if (questionChoice.equals("4")) {
            Essay essay = new Essay();
            essay.create();
            this.questions.add(essay);
        } else if (questionChoice.equals("5")) {
            ValidDate validDate = new ValidDate();
            validDate.create();
            this.questions.add(validDate);
        } else if (questionChoice.equals("6")) {
            Matching matching = new Matching();
            matching.create();
            this.questions.add(matching);
        } else if (questionChoice.equals("7")) {
            checkExit = true;
            System.out.println("You chose to return to previous menu.");
        } else {
            System.out.println("Invalid choice, please choose again.");
            addQuestion();
        }
    }

    public void display() {
        for (int x = 0; x < questions.size(); x++) {
            System.out.println(x + 1 + ".");
            questions.get(x).display();
        }
    }

    public Survey take() {
        for (int x = 0; x < questions.size(); x++) {
            System.out.println((x+1) + ".") ;
            questions.get(x).display();
            questions.get(x).getResponse();
        }
        System.out.println("You have finished taking the current survey/test.");
        return this;
    }

    public void modifyQuestion(int questionNumber) {
        if (questionNumber >= 1 && questionNumber <= questions.size()) {
            questions.get(questionNumber - 1).modify();
        } else {
            System.out.println("Invalid question number.");
        }
        questions.get(questionNumber-1).modify();
    }



    public void save(String dirPath, Survey fileType ) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
        String fileName = this.getName() + timeStamp + ".ser";
        SerializationHelper.serialize(Survey.class, this, dirPath, fileName);
        System.out.println("Successfully taken and saved " + this.getName());
    }



    public String serialize(String dirPath, String fileName) {
        // using getClass() instead of Survey.class to handle both Survey and Test
        return SerializationHelper.serialize(getClass(), this, dirPath, fileName);
    }

    public static Survey deserialize(String filePath, Class<? extends Survey> clazz) {
        // deserialize as the specified class (Survey or Test)
        return SerializationHelper.deserialize(clazz, filePath);
    }


}
