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
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class SessionController {

    private final SessionService service;
    private final TicketService ticketService;
    private final UserService userService;

    public SessionController(SessionService service, TicketService ticketService, UserService userService) {
        this.service = service;
        this.ticketService = ticketService;
        this.userService = userService;
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
    public String formPayTicket(Model model) {
        //  model.addAttribute("line", service.getFindLines());
        //  model.addAttribute("cell", service.getFindCells());
        model.addAttribute("sessions", service.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("ticket", new Ticket(0, null, 0, 0, null));
        return "addTicket";
    }

    @PostMapping("/addTicket")
    public String addTicket(@ModelAttribute Ticket ticket,
                            @RequestParam("session.id") int sessionId,
                            @RequestParam("user.id") int userId) {
        //  model.addAttribute("line", service.approveLine(
        //          ticket.getSession_id()));
        //  model.addAttribute("cell", service.approveCells(
        //          ticket.getSession_id(), ticket.getCell()));
        //ticket.setSession_id(service.findById(sessionId));
        //ticket.setUser_id(userService.findById(userId));
        Optional<Ticket> addTicket = ticketService.add(ticket);
        if (addTicket.isPresent()) {
            return "redirect:/sessions";
        }
        return "sessions";
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

