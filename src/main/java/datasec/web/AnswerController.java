package datasec.web;

import datasec.domain.Answer;
import datasec.domain.Question;
import datasec.domain.service.ResetService;
import datasec.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.sql.SQLException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Michał on 2017-06-03.
 */

@Controller
@RequestMapping("/answer")
public class AnswerController {

    private ResetService resetService;

    private Question question;

    @Autowired
    public AnswerController(ResetService utrs) {

        this.resetService = utrs;
    }

    @RequestMapping(method = GET)
    public String resetGet(Model model) throws SQLException {

        question = this.resetService.getQuestion();
        model.addAttribute("question", question.getQuestion());

        Answer answer = new Answer();
        model.addAttribute("answer", answer);

        return "answer";
    }

    @RequestMapping(method = POST)
    public String resetPost(@ModelAttribute("answer") @Valid Answer answer) {

        this.resetService.assertAnswers(answer, question);
        return "redirect: /change";
    }


    @ExceptionHandler(ApplicationException.class)
    public ModelAndView handleAppException() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("exception");
        return mav;
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSQLException() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("exception");
        return mav;
    }
}
