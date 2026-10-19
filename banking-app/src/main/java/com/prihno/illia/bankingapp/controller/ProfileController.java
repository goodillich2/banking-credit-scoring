package com.prihno.illia.bankingapp.controller;


import com.prihno.illia.bankingapp.entity.user.User;
import com.prihno.illia.bankingapp.entity.user.details.HomeOwnership;
import com.prihno.illia.bankingapp.entity.user.details.PreviousDefault;
import com.prihno.illia.bankingapp.entity.user.details.UserDetails;
import com.prihno.illia.bankingapp.repository.UserRepository;
import com.prihno.illia.bankingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping()
    public String getProfile(Model model) throws Exception {

        //получаем email юзера
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(userEmail);

        //получаем обьект юзеря по имени ()
        User user = userService.findByEmail(userEmail);

        model.addAttribute("user", user);
        return "profile";
    }


    @GetMapping("/update")
    public String updateProfile(Model model) throws Exception {

        //получаем email юзера
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        //получаем обьект юзеря по email
        User user = userService.findByEmail(userEmail);

        model.addAttribute("user", user);
        return "updateProfile";
    }


    @PostMapping("/update")
    public String updateProfile(@RequestParam(name = "firstName") String firstName,
                                @RequestParam(name = "lastName") String lastName,
                                @RequestParam(name = "email") String email
    ) throws Exception {

        //получаем email юзера
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        //получаем обьект юзеря по email
        User user = userService.findByEmail(userEmail);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.flush();

        return "redirect:/profile";
    }

    @GetMapping("/profileDetails")
    public String profileDetails(Model model) throws Exception {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userEmail);

        UserDetails userDetails = user.getUserDetails() == null ? new UserDetails() : user.getUserDetails();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("homeOwnershipOptions", HomeOwnership.values());
        model.addAttribute("previousDefaultOptions", PreviousDefault.values());
        return "profileDetails";
    }

    @PostMapping("/profileDetails")
    public String profileDetails(@ModelAttribute UserDetails userDetails, RedirectAttributes redirectAttributes) throws Exception {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userEmail);

        if (user.getUserDetails() == null) {
            userDetails.setUser(userService.findByEmail(userEmail));
            userService.saveDetails(userDetails);
        } else {
            UserDetails existingUserDetails = user.getUserDetails();
            existingUserDetails.setAge(userDetails.getAge());
            existingUserDetails.setMonthIncome(userDetails.getMonthIncome());
            existingUserDetails.setEmploymentLength(userDetails.getEmploymentLength());
            existingUserDetails.setHomeOwnership(userDetails.getHomeOwnership());
            existingUserDetails.setPreviousLoans(userDetails.getPreviousLoans());
            existingUserDetails.setPreviousDefault(userDetails.getPreviousDefault());
            userService.saveDetails(existingUserDetails);
        }
        redirectAttributes.addFlashAttribute("successMessage", "Profile details saved!");
        return "redirect:/profile";
    }
}
