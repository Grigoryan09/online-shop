package org.example.onlineshop.service;

import org.example.onlineshop.model.Comment;

public interface CommentService {

    void save(Comment comment);

    Comment findById(Integer id);

    void deleteById(int id);
}
