package datasec.web;

import datasec.domain.UserToRegister;
import datasec.domain.service.UserToRegisterService;
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
@RequestMapping("/register")
public class RegisterPageController {

    private UserToRegisterService userToRegisterService;

    @Autowired
    public RegisterPageController(UserToRegisterService us) {
        this.userToRegisterService = us;
    }

    @RequestMapping(method = GET)
    public String getUserDataFromForm(Model model) {

        UserToRegister newUserToRegister = new UserToRegister();
        model.addAttribute("userToRegister", newUserToRegister);
        return "register";
    }

    @RequestMapping(method = POST)
    public String processAddNewUserForm(@ModelAttribute("userToRegister") @Valid UserToRegister newUserToRegister, BindingResult result) {

        this.userToRegisterService.addUser(newUserToRegister, result);
        return "redirect:/login";
    }

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView handleAppException() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("exception");
        return mav;
    }
}
