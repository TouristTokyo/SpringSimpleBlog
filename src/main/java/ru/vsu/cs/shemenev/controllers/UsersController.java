package ru.vsu.cs.shemenev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.shemenev.dao.UserDAO;
import ru.vsu.cs.shemenev.models.User;
import ru.vsu.cs.shemenev.utils.Action;
import ru.vsu.cs.shemenev.utils.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserDAO userDAO;
    private final UserValidator userValidator;

    @Autowired
    public UsersController(UserDAO users, UserValidator userValidator) {
        this.userDAO = users;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String usersShow(Model model) {
        if (!userDAO.isLogin()) {
            return "redirect:/";
        }
        model.addAttribute("users", userDAO.getUsers());
        model.addAttribute("currentUser", userDAO.getCurrentUser());
        return "users/usersAll";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        if (!userDAO.isLogin()) {
            return "redirect:/";
        }
        User user = userDAO.getUserOnId(id);
        if (user == null) {
            return "notFound";
        }
        model.addAttribute("user", user);
        model.addAttribute("currentUser", userDAO.getCurrentUser());
        model.addAttribute("posts", userDAO.getUserPosts(id));
        return "users/showUser";
    }

    @GetMapping("/{id}/edit")
    public String editHandleForUser(Model model, @PathVariable("id") int id) {
        if (!userDAO.isLogin()) {
            return "redirect:/";
        }
        model.addAttribute("user", userDAO.getUserOnId(id));
        return "users/update";
    }

    @PatchMapping("/{id}")
    public String editHandleForUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                    @PathVariable("id") int id) {
        userValidator.setAction(Action.EDIT);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasFieldErrors("handle")) {
            return "users/update";
        }
        userDAO.editHandle(user, id);
        return "redirect:/users/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userDAO.deleteUser(id);
        userDAO.signOut();
        return "redirect:/";
    }
}
