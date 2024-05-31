import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Question implements Serializable {
    protected String prompt;
    private static final long serialVersionUID = 1L;
    public ArrayList<SurveyResponseCorrectAnswer> responses = new ArrayList<SurveyResponseCorrectAnswer>();

    Question() {
    }

    public void setQuestion() {
    }

    public String getQuestion() {
        return "";
    }

    public abstract void getResponse();
    public abstract SurveyResponseCorrectAnswer getTestResponse();

    public void setResponse() {}

    public abstract void display();

    public abstract void modify();

    public abstract boolean validateResponse(String response);
}
