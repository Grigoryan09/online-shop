package org.example.onlineshop.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.example.onlineshop.model.User;
import org.example.onlineshop.service.CategoryService;
import org.example.onlineshop.service.UserService;
import org.example.onlineshop.service.security.SpringUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MainController {

    @Value("${online.shop.upload.image.directory,path}")
    private String imageDirectoryPath;

    private final CategoryService categoryService;
    private final UserService userService;


    @GetMapping("/")
    public String index(@AuthenticationPrincipal SpringUser springUser, ModelMap modelMap) {
        if (springUser != null) {
            modelMap.addAttribute("user", springUser.getUser());
        }
        modelMap.addAttribute("categories", categoryService.findAll());
        return  "index";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String msg, ModelMap modelMap) {
        modelMap.addAttribute("msg", msg);
        return  "loginPage";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, @RequestParam(required = false) String role){
        if(userService.findOptionalByEmail(user.getEmail()).isPresent()){
            return  "redirect:/registerPage?msg=Username already exists";
        }

        if (role != null && !role.isEmpty()) {
            user.setRole(org.example.onlineshop.model.UserRole.valueOf(role));
        } else {
            user.setRole(org.example.onlineshop.model.UserRole.USER);
        }

        userService.save(user);
        return  "redirect:/login?msg=Registration successful, pls login!";
    }

    @GetMapping("/registerPage")
    public String registerPage(@RequestParam(required = false) String msg, ModelMap modelMap) {
        modelMap.addAttribute("msg", msg);
        return  "registerPage";
    }

    @GetMapping("/image/get")
    public @ResponseBody byte[] getImage(@RequestParam("pic") String picName ) {
        File file = new File(imageDirectoryPath + picName);
        if (file.exists()) {
            try {
                return  FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
               throw new  RuntimeException(e);
            }
        }
        return  null;
    }

}
