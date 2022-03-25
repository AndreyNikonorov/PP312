package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin/users")
    public String showUsers(Model model){
        List<User> users = userService.showUsers();
        model.addAttribute("users",users);
        return "users";
    }
    @GetMapping("/user")
    public String userGetPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userService.findByUserName(userDetails.getUsername()));
        return "user";
    }

    @GetMapping("/admin/users/addUser")
    public String addUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "addUser";
    }

    @PostMapping("/admin/users")
    public String addUser(@ModelAttribute("user") User user, @RequestParam(value = "inputRoles", required = false) Long[] inputRoles) {
        Set<Role> roles = new HashSet<>();
        if (inputRoles == null) {
            roles.add(roleService.getRoleByName("ROLE_USER"));
            user.setRoles(roles);
        } else {
            for(Long i: inputRoles) {
                roles.add(roleService.getRoleById(i));
            }
            user.setRoles(roles);
        }
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("users", userService.findById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "editUser";
    }

    @PatchMapping("/admin/users/{id}")
    public String editUser(@ModelAttribute("user") User user,
                               @PathVariable("id") Long id,
                               @RequestParam(value = "inputRoles", required = false) Long[] inputRoles) {

        Set<Role> temp = new HashSet<>();

        if (inputRoles == null) {
            temp.add(roleService.getRoleByName("ROLE_USER"));
            user.setRoles(temp);
        } else {
            for(Long i: inputRoles) {
                temp.add(roleService.getRoleById(i));
            }
            user.setRoles(temp);
        }
        userService.editUser(user);
        return "redirect:/admin/users";
    }
}
