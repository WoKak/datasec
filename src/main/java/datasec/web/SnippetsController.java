package datasec.web;

import datasec.domain.LoggedUser;
import datasec.domain.repository.CodeRepository;
import datasec.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Michał on 2017-05-25.
 */

@Controller
@RequestMapping("/snippets")
public class SnippetsController {

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private LoggedUser loggedUser;

    @RequestMapping(method = GET)
    public String list(Model model) {

        if (loggedUser.isLogged()) {
            model.addAttribute("codes", codeRepository.getSnippets());
            return "snippets";
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
