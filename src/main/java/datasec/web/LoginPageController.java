package datasec.web;

import datasec.domain.UserToLogin;
import datasec.domain.service.UserToLoginService;
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
 * Created by Michał on 2017-06-01.
 */

@Controller
@RequestMapping("/login")
public class LoginPageController {

    private UserToLoginService userToLoginService;

    @Autowired
    public LoginPageController(UserToLoginService us) {
        this.userToLoginService = us;
    }

    @RequestMapping(method = GET)
    public String getDataFromForm(Model model) {

        UserToLogin newUserToLogin = new UserToLogin();
        model.addAttribute("userToLogin", newUserToLogin);
        return "login";
    }

    @RequestMapping(method = POST)
    public String processAddNewWordForm(@ModelAttribute("userToLogin") @Valid UserToLogin newUserToLogin, BindingResult result) {

        this.userToLoginService.auth(newUserToLogin, result);
        return "redirect:/home";
    }

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView handleAppException() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("exception");
        return mav;
    }
}
