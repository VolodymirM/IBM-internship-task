package com.ibm.comtotick.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibm.comtotick.dataentities.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer> {

}
