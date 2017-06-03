package datasec.web;

import datasec.domain.LoggedUser;
import datasec.domain.NewQuestion;
import datasec.domain.service.QuestionService;
import datasec.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Michał on 2017-06-03.
 */

@Controller
@RequestMapping("/question")
public class QuestionPageController {

    private QuestionService questionService;

    @Autowired
    LoggedUser loggedUser;

    @Autowired
    public QuestionPageController(QuestionService qs) {

        this.questionService = qs;
    }

    @RequestMapping(method = GET)
    public String questionFormGet(Model model) {

        if(loggedUser.isLogged()) {

            NewQuestion newQuestion = new NewQuestion();
            model.addAttribute("question", newQuestion);
            return "question";
        }

        return "redirect:/login";
    }

    @RequestMapping(method = POST)
    public String questionFormPost(@ModelAttribute("question") @Valid NewQuestion q, BindingResult result) {

        if (loggedUser.isLogged()) {

            this.questionService.change(q, result);
            return "redirect:/home";
        }

        return "redirect:/login";
    }

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView handleAppException() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("exception");
        return mav;
    }
}
