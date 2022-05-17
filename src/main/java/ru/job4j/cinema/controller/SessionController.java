package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

@Controller
public class SessionController {

    private final SessionService service;
    private final TicketService ticketService;

    public SessionController(SessionService service, TicketService ticketService) {
        this.service = service;
        this.ticketService = ticketService;
    }

    @GetMapping("/sessions")
    public String sessions(Model model) {
        model.addAttribute("sessions", service.findAll());
        return "sessions";
    }

    @GetMapping("/tickets")
    public String tickets(Model model, HttpSession session) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "tickets";
    }

    @GetMapping("/formPayTicket")
    public String formPayTicket(Model model, @ModelAttribute Ticket ticket) {
        model.addAttribute("line", service.getFindLines());
        model.addAttribute("cell", service.getFindCells());
        model.addAttribute("sessions", service.findAll());
        return "addTicket";
    }

    @PostMapping("/addTicket")
    public String addTicket(Model model ,@ModelAttribute Ticket ticket, @RequestParam("session.id") int id) {
        model.addAttribute("line", service.approveLine(
                ticket.getSession_id()));
        model.addAttribute("cell", service.approveCells(
                ticket.getSession_id(), ticket.getCell()));
        ticketService.add(ticket);
        return "redirect:/sessions";
    }

    private User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Гость");
        }
        return user;
    }
}

