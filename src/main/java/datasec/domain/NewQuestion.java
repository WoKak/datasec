package datasec.domain;

import javax.validation.constraints.NotNull;

/**
 * Created by Michał on 2017-06-03.
 */
public class NewQuestion {

    @NotNull
    private String previous;

    @NotNull
    private String question;

    @NotNull
    private String answer;

    public NewQuestion() {
    }

    public NewQuestion(String previous, String question, String answer) {
        this.previous = previous;
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
