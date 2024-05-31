import utils.FileUtils;
import utils.SerializationHelper;

import java.io.File;
import java.util.*;


import static java.util.Objects.isNull;

public class MainHelper {
    protected String surveyPath = "NewSurveys" + File.separator;
    protected String takenSurveyPath = "TakenSurveys" + File.separator;
    protected String testPath = "NewTests" + File.separator;
    protected String takenTestPath = "TakenTests" + File.separator;

    protected File surveyFile = new File(surveyPath);
    protected File testFile = new File(testPath);

    protected Survey loadedSurvey;
    protected Test loadedTest;

    public MainHelper() {
    }

    // Survey Menu Helpers
    public void createSurvey() {
        Survey survey = new Survey();
        survey.create();
        String fileName = survey.serialize(surveyPath, survey.getName() + ".ser");
        System.out.println("Successfully created and saved " + fileName);
    }

    public void displaySurvey() {
        if (isNull(loadedSurvey)) {
            System.out.println("You must have a survey loaded in order to display it.");
        }
        else {
            loadedSurvey.display();
        }
    }

    public void loadSurvey() {
        File[] filePaths = surveyFile.listFiles();
        if (filePaths == null || filePaths.length == 0) {
            System.out.println("No surveys available to load.");
            return;
        }

        int index = selectFile(filePaths);
        if (index >= 0) {
            String fileName = filePaths[index].getName();
            String filePath = surveyPath + fileName;
            try {
                loadedSurvey =  Survey.deserialize(filePath, Survey.class);
                if (loadedSurvey != null) {
                    System.out.println("You have successfully loaded the survey: " + fileName.replace(".ser", ""));
                } else {
                    System.out.println("Failed to load the survey. The file might be corrupted.");
                    loadSurvey();
                }
            } catch (Exception e) {
                System.out.println("An error occurred while loading the survey: " + e.getMessage());
                loadSurvey();
            }
        }
    }

    public void saveSurvey() {
        if (isNull(loadedSurvey)) {
            System.out.println("You must have a survey loaded in order to save it");
        } else {
            String fileName = loadedSurvey.serialize(surveyPath, loadedSurvey.getName() + ".ser");
            System.out.println("Successfully saved " + fileName);
        }
    }

    public void takeSurvey() {
        if (isNull(loadedSurvey)) {
            System.out.println("You must have a survey loaded in order to take it");
        }
        else {
            try {
//                Survey takenSurvey = loadedSurvey.take();
//                takenSurvey.save(takenSurveyPath);
                saveUnderTakenSurveySet(loadedSurvey.take());

            }
            catch (Exception e) {
                System.out.println("Something went wrong, restarting...");
                takeSurvey();
            }
        }
    }

    public void modifySurvey() {
        Scanner choice = new Scanner(System.in);
        if (isNull(loadedSurvey)) {
            System.out.println("You must have a survey loaded in order to modify it");
        }
        else {
            displaySurvey();
            System.out.println("Choose a question to edit: ");
            try {
                int number = Integer.parseInt(choice.nextLine());
                loadedSurvey.modifyQuestion(number);
            }
            catch (Exception e) {
                System.out.println("Enter a valid int!");
                modifySurvey();
            }
        }
    }
    public void tabulateSurvey() {
        System.out.println("Choose a file to load from below: ");
        File[] filePaths = (surveyFile.listFiles());
        Scanner choice = new Scanner(System.in);
        List<Survey> surveyResponses = new ArrayList<>();
        List<HashMap<List, Integer>> responseMap =  new ArrayList<HashMap<List, Integer>>();
        for (int x = 0; x < Objects.requireNonNull(filePaths).length; x++){
            System.out.println((x+1)+ ". " + filePaths[x].getName().replace(".ser", ""));
        }
        try {
            int index = Integer.parseInt(choice.nextLine());

            String filePath = takenSurveyPath + filePaths[index-1].getName().replace(".ser", "") + File.separator;
            surveyResponses = aggregateResponses(filePath, Survey.class, "Make sure to enter the right survey number!");
            assert surveyResponses != null;
            Survey survey1 = surveyResponses.get(0);

            for (int y = 0; y < survey1.questions.size(); y++) {
                responseMap.add(y, new HashMap<>());
            }

            for (int x = 0; x < Objects.requireNonNull(surveyResponses).size(); x++) {
                Survey survey = surveyResponses.get(x);
                for (int y = 0; y < survey.questions.size(); y++) {
                    Question question = survey.questions.get(y);
                    List<String> answer = question.responses.get(0).answers;
                    if (responseMap.get(y).get(answer) == null ) {
                        responseMap.get(y).put(question.responses.get(0).answers, 1);
                    }
                    else {
                        responseMap.get(y).put(question.responses.get(0).answers, responseMap.get(y).get(question.responses.get(0).answers) + 1);
                    }
                }
            }
            showTabulationSurvey(responseMap, survey1);
        }
        catch (Exception e) {
            System.out.println("Enter a valid int!");
            tabulateSurvey();
        }
    }




    // Test Menu Helpers
    public void createTest() {
        Test test = new Test();
        test.create();
        String fileName = test.serialize(testPath, test.getName() + ".ser");
        System.out.println("Successfully created and saved " + fileName);
    }

    public void displayTest(){
        if (isNull(loadedTest)) {
            System.out.println("You must have a test loaded in order to display it.");
        }
        else {
            loadedTest.display();
        }

    }
    public void displayTestWithAnswers(){
        if (isNull(loadedTest)) {
            System.out.println("You must have a test loaded in order to display it.");
        }
        else {
            loadedTest.displayWithAnswers();
        }
    }

    public void loadTest(){
        File[] filePaths = testFile.listFiles();
        if (filePaths == null || filePaths.length == 0) {
            System.out.println("No tests available to load.");
            return;
        }

        int index = selectFile(filePaths);
        if (index >= 0) {
            String fileName = filePaths[index].getName();
            String filePath = testPath + fileName;
            try {
//                loadedTest = Test.deserialize(filePath);
                loadedTest = (Test) Survey.deserialize(filePath, Test.class);
                if (loadedTest != null) {
                    System.out.println("You have successfully loaded the test: " + fileName.replace(".ser", ""));
                } else {
                    System.out.println("Failed to load the survey. The file might be corrupted...");
                    loadTest();
                }
            } catch (Exception e) {
                System.out.println("An error occurred while loading the survey: " + e.getMessage());
                loadTest();
            }
        }

    }

    public void saveTest(){
        if (isNull(loadedTest)) {
            System.out.println("You must have a test loaded in order to save it.");
        } else {
            String fileName = loadedTest.serialize(testPath, loadedTest.getName() + ".ser");
            System.out.println("Successfully saved " + fileName);
        }

    }

    public void takeTest() {
        if (isNull(loadedTest)) {
            System.out.println("You must have a test loaded in order to take it.");
        }
        else {
            try {
//                Test takenTest = (Test) loadedTest.take();
//                takenTest.save(takenTestPath, takenTest);
                saveUnderTakenTestSet((Test) loadedTest.take());

            }
            catch (Exception e) {
                System.out.println("Something went wrong, restarting...");
                takeTest();
            }
        }
    }

    public void modifyTest(){
        Scanner choice = new Scanner(System.in);
        if (isNull(loadedTest)) {
            System.out.println("You must have a load a test in order to modify it.");
        }
        else {
            displayTestWithAnswers();  // why display this?
            System.out.println("Choose a question to edit: ");
            try {
                int number = Integer.parseInt(choice.nextLine());
                loadedTest.modify(number);
            }
            catch (Exception e) {
                System.out.println("Enter a valid int!");
                modifyTest();
            }
        }
    }

    public void tabulateTest() {
        System.out.println("Choose a file to load from below: ");
        File[] filePaths = (testFile.listFiles());
        Scanner choice = new Scanner(System.in);
        List<Test> testResponses = new ArrayList<>();
        List<HashMap<List, Integer>> responseMap =  new ArrayList<HashMap<List, Integer>>();
        for (int x = 0; x < Objects.requireNonNull(filePaths).length; x++){
            System.out.println((x+1)+ ". " + filePaths[x].getName().replace(".ser", ""));
        }
        try {
            int index = Integer.parseInt(choice.nextLine());

            String filePath = takenTestPath + filePaths[index-1].getName().replace(".ser", "");
            testResponses = aggregateResponses(filePath, Test.class, "Make sure to enter the right test number!");
            assert testResponses != null;
            Test test1 = testResponses.get(0);
            for (int y = 0; y < test1.questions.size(); y++) {
                responseMap.add(y, new HashMap<>());
            }

            for (int x = 0; x < Objects.requireNonNull(testResponses).size(); x++) {
                Test test = testResponses.get(x);
                for (int y = 0; y < test.questions.size(); y++) {
                    Question question = test.questions.get(y);
                    List<String> answer = question.responses.get(0).answers;
                    if (responseMap.get(y).get(answer) == null ) {
                        responseMap.get(y).put(question.responses.get(0).answers, 1);
                    }
                    else {
                        responseMap.get(y).put(question.responses.get(0).answers, responseMap.get(y).get(question.responses.get(0).answers) + 1);
                    }
                }
            }
            showTabulationTest(responseMap, test1);
        }
        catch (Exception e) {
            System.out.println("Enter a valid int!");
            tabulateTest();
        }

    }

    public void gradeTest() {
        File[] filePaths = (testFile.listFiles());

        int fileIndex = selectFile(filePaths);
        if (fileIndex == -1) return;

        String pathName = takenTestPath + filePaths[fileIndex].getName().replace(".ser", "") + File.separator;
        File takenTests = new File(pathName);
        File[] testPaths = (takenTests.listFiles());

        System.out.println("Previously taken tests sets are listed below, select one to grade: ");

        int testIndex = selectFile(testPaths);
        if (testIndex == -1) return;

        String filePath = pathName + testPaths[testIndex].getName();
        Test test = SerializationHelper.deserialize(Test.class, filePath);
        test.grade();
    }








    // helper method to handle file selection
    private int selectFile(File[] files) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName().replace(".ser", ""));
        }
        System.out.print("Enter the number of the file: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < files.length) {
                return choice;
            }
            System.out.println("Invalid selection. Please select a number between 1 and " + files.length);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter an integer!.");
        }
        return -1; // invalid selection
    }


    public void saveUnderTakenTestSet(Test test) {
        // generate a unique identifier (UUID)
        UUID uniqueId = UUID.randomUUID();

        String fileName = test.name + "_" + uniqueId.toString() + ".ser";
        String filePath = takenTestPath + test.name + File.separator;

        // serialize and save the test
        SerializationHelper.serialize(Test.class, test, filePath, fileName);
        System.out.println("Successfully taken and saved " + test.name);
    }



    public void saveUnderTakenSurveySet(Survey survey) {
        UUID uniqueId = UUID.randomUUID();
        String fileName = survey.getName() + "_" + uniqueId.toString() + ".ser";
        String filePath = takenSurveyPath + survey.name + File.separator;
        SerializationHelper.serialize(Survey.class, survey, filePath, fileName);
        System.out.println("Successfully taken and saved " + survey.name);
    }



    // generic method used to aggregate Test Or Survey responses
    public static <T> List<T> aggregateResponses(String filePath, Class<T> responseType, String errorPrompt) {
        List<String> dirPath;
        ArrayList<T> loadResponses = new ArrayList<>();

        try {
            dirPath = FileUtils.getAllFilePathsInDir(filePath);
            for (String path : dirPath) {
                loadResponses.add(utils.SerializationHelper.deserialize(responseType, path));
            }
            return loadResponses;
        } catch (Exception e) {
            System.out.println(errorPrompt);
            return null;
        }
    }


    public void showTabulationTest(List<HashMap<List, Integer>> responseMap, Test test) {
        for (int x = 0; x < test.questions.size(); x++) {
            Question question = test.questions.get(x);

            System.out.println("Question:");
            question.display();

            System.out.println("Responses:");
            List<String> responses = question.responses.get(0).answers;
            for (String response : responses) {
                System.out.println(response);
            }

            System.out.println("Tabulation:");

            Set<Map.Entry<List, Integer>> entries = responseMap.get(x).entrySet();
            for (Map.Entry<List, Integer> entry : entries) {
                List<Integer> key = entry.getKey();
                int count = entry.getValue();

                // Check if it's not an essay question
                if (!isEssayQuestion(question)) {
                    System.out.println(key);
                }

                System.out.println("Count: " + count);
                System.out.println();
            }
        }
    }

    public void showTabulationSurvey(List<HashMap<List, Integer>> responseMap, Survey survey) {
        for (int x = 0; x < survey.questions.size(); x++) {
            Question question = survey.questions.get(x);

            System.out.println("Question:");
            question.display();

            System.out.println("Responses:");
            List<String> responses = question.responses.get(0).answers;
            for (String response : responses) {
                System.out.println(response);
            }

            System.out.println("Tabulation:");

            Set<Map.Entry<List, Integer>> entries = responseMap.get(x).entrySet();
            for (Map.Entry<List, Integer> entry : entries) {
                List<Integer> key = entry.getKey();
                int count = entry.getValue();

                // Check if it's not an essay question
                if (!isEssayQuestion(question)) {
                    System.out.println(key);
                }

                System.out.println("Count: " + count);
                System.out.println();
            }
        }
    }

    private boolean isEssayQuestion(Question question) {
        // Assuming that an essay question has a single response in the SurveyResponseCorrectAnswer
        SurveyResponseCorrectAnswer correctAnswer = question.responses.get(0);

        // If the correct answer contains multiple answers, consider it an essay question
        return correctAnswer.getResponse().size() > 1;
    }




}

