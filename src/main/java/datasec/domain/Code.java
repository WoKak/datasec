package datasec.domain;

/**
 * Created by Michał on 2017-05-25.
 */
public class Code {

    private String text;

    public Code() {
    }

    public Code(String code) {
        this.text = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
