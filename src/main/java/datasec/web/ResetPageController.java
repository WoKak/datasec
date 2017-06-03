package datasec.web;

import datasec.domain.UserToReset;
import datasec.domain.service.ResetService;
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
@RequestMapping("/reset")
public class ResetPageController {

    private ResetService userToResetService;

    @Autowired
    public ResetPageController(ResetService utrs) {

        this.userToResetService = utrs;
    }

    @RequestMapping(method = GET)
    public String reset(Model model) {

        UserToReset userToReset = new UserToReset();
        model.addAttribute("userToReset", userToReset);
        return "reset";
    }

    @RequestMapping(method = POST)
    public String questionFormPost(@ModelAttribute("userToReset") @Valid UserToReset userToReset, BindingResult bindingResult) {

        this.userToResetService.reset(userToReset, bindingResult);
        return "redirect:/answer";
    }

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView handleAppException() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("exception");
        return mav;
    }
}
