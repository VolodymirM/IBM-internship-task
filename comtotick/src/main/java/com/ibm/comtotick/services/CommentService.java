package com.ibm.comtotick.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.comtotick.dataentities.Comment;
import com.ibm.comtotick.dataentities.Ticket;
import com.ibm.comtotick.repository.CommentRepo;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private HuggingFaceService huggingFaceService;

    public void addComment(Comment comment) {
        try {
            commentRepo.save(comment);

            HuggingFaceService.TicketDraft draft = huggingFaceService.analyze(comment.getContent());

            if (draft != null) {
                ticketService.addTicket(new Ticket(
                    comment.getContent(),
                    draft.title(),
                    draft.category(),
                    draft.priority(),
                    draft.summary()
                ));
            }

        } catch (Exception e) {
            System.out.println("Error processing comment: " + e.getMessage());
        }
    }

    public List<Comment> getComments() { return commentRepo.findAll(); }
}