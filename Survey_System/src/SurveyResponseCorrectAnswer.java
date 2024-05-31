import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SurveyResponseCorrectAnswer implements Serializable {
    private static final long serialVersionUID = 1L;
    ArrayList<String> answers = new ArrayList<String>();

    SurveyResponseCorrectAnswer() {

    }

    public ArrayList<String> getResponse() {
        return this.answers;
    }

    public void setAnswers(String answer) {
        this.answers.add(answer);
    }

    // display test or survey w/o answers
    public void display() {
        for (int x = 0; x < answers.size(); x++) {
            System.out.println(answers.get(x));
        }
    }

    // display test with correct answers
    public String displayAnswer() {
        return answers.stream().collect(Collectors.joining());
    }


}

