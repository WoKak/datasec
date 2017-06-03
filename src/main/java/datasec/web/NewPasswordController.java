package datasec.web;

import datasec.domain.LoggedUser;
import datasec.domain.PasswordToChange;
import datasec.domain.UserToRegister;
import datasec.domain.service.PasswordChangeService;
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
 * Created by Michał on 2017-06-02.
 */

@Controller
@RequestMapping("/change")
public class NewPasswordController {

    private PasswordChangeService passwordChangeService;

    @Autowired
    LoggedUser loggedUser;

    @Autowired
    public NewPasswordController(PasswordChangeService ptcs) {
        this.passwordChangeService = ptcs;
    }

    @RequestMapping(method = GET)
    public String changeFormGet(Model model) {

        if(loggedUser.isLogged()) {

            PasswordToChange passwordToChange = new PasswordToChange();
            model.addAttribute("passwordToChange", passwordToChange);
            return "change";
        }

        return "redirect:/login";
    }

    @RequestMapping(method = POST)
    public String changeFormPost(@ModelAttribute("passwordToChange") @Valid PasswordToChange passwordToChange, BindingResult result) {

        if (loggedUser.isLogged()) {

            this.passwordChangeService.change(passwordToChange, result);
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
