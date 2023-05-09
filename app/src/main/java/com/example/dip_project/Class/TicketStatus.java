package com.example.dip_project.Class;

public class TicketStatus {
    String ticketName;
    String status;

    public TicketStatus(String ticketName, String status) {
        this.ticketName = ticketName;
        this.status = status;
    }

    public String getTicketName() {
        return ticketName;
    }

    public String getStatus() {
        return status;
    }
}
