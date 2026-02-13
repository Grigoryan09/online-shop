package org.example.onlineshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.onlineshop.model.Comment;
import org.example.onlineshop.repository.CommentRepository;
import org.example.onlineshop.service.CommentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment findById(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }
}


