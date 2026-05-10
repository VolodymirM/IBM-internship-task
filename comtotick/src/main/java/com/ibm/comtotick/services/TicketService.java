package com.ibm.comtotick.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.comtotick.dataentities.Ticket;
import com.ibm.comtotick.repository.TicketRepo;

@Service
public class TicketService {

    @Autowired
    private TicketRepo ticketRepo;

    public Ticket getTicketById(Integer id) {
        return ticketRepo.findById(id)
        .orElse(new Ticket(null, "N/A", "Ticket not found",
        "No description available", null, null));
    }
    
    public List<Ticket> getTickets() { return ticketRepo.findAll(); }
    public void addTicket(Ticket ticket) { ticketRepo.save(ticket); }

}
