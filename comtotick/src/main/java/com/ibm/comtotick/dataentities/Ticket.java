package com.ibm.comtotick.dataentities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private String title;
    private String category;
    private String priority;
    private String shortSummary;
    
    public Ticket(String content, String title, String category, String priority, String shortSummary) {
        this.content = content;
        this.title = title;
        this.category = category;
        this.priority = priority;
        this.shortSummary = shortSummary;
    }
}
