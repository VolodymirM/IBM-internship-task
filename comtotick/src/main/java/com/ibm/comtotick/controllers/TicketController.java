package com.ibm.comtotick.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.comtotick.dataentities.Ticket;
import com.ibm.comtotick.services.TicketService;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public List<Ticket> getTickets() {
        return ticketService.getTickets();
    }
    
    @GetMapping("/tickets/{id}")
    public Ticket getTicketById(@PathVariable Integer id) {
        return ticketService.getTicketById(id);
    }
    
}
