package org.example.onlineshop.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshop.model.Comment;
import org.example.onlineshop.model.Product;
import org.example.onlineshop.model.User;
import org.example.onlineshop.service.CommentService;
import org.example.onlineshop.service.ProductService;
import org.example.onlineshop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ProductService productService;
    private final UserService userService;

    @PostMapping("/comment/add")
    public String addComment(@RequestParam String comment,
                             @RequestParam int productId,
                             Authentication authentication) {

        User user = userService.getByEmail(authentication.getName());
        Product product = productService.findById(productId);

        Comment c = new Comment();
        c.setComment(comment);
        c.setProduct(product);
        c.setUser(user);

        commentService.save(c);
        return "redirect:/user/product/" + productId;
    }

    @PostMapping("/comment/{id}/delete")
    public String deleteComment(@PathVariable int id) {
        Comment comment = commentService.findById(id);
        if (comment == null || comment.getProduct() == null) {
            return "redirect:/";
        }
        int productId = comment.getProduct().getId();
        commentService.deleteById(id);
        return "redirect:/user/product/" + productId;
    }


}
