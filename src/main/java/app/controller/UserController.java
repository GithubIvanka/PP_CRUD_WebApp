package app.controller;

import app.model.User;
import app.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    final private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/users")
    public String users(@NotNull Model model) {
        model.addAttribute("users", service.getAllUsers());
        return "/users/users";
    }

    @GetMapping(value = "/")
    public String index() {
        return "redirect:/users";
    }

    @GetMapping(value = "/users/{id}")
    public String user(@PathVariable("id") int id, @NotNull Model model) {
        model.addAttribute("user", service.getUser(id));
        return "/users/user";
    }

    @GetMapping(value = "/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "/users/new";
    }

    @PostMapping(value = "/users")
    public String create(@ModelAttribute("user") User user) {
        service.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping(value = "/users/{id}/edit")
    public String edit(@NotNull Model model, @PathVariable("id") int id) {
        model.addAttribute("user", service.getUser(id));
        return "/users/edit";
    }

    @PostMapping(value = "/users/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        service.updateUser(id, user);
        return "redirect:/users";
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable("id") int id) {
        service.removeUser(id);
        return "redirect:/users";
    }
}
