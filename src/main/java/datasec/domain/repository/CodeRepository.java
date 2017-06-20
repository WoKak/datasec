package datasec.domain.repository;

import datasec.domain.Code;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michał on 2017-05-25.
 */

/**
 * Code repo class used for storing snippets
 */
public class CodeRepository {

    private List<Code> snippets;

    public CodeRepository() {
        this.snippets = new ArrayList<>(0);
    }

    public List<Code> getSnippets() {
        return snippets;
    }

}
