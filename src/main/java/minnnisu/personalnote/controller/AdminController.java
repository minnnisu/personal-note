package minnnisu.personalnote.controller;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.dto.note.NoteAdminDto;
import minnnisu.personalnote.service.NoteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final NoteService noteService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String getNoteAdmin(Model model) {
        List<NoteAdminDto> notes = noteService.findAll();
        model.addAttribute("notes", notes);
        return "admin/index";
    }

}
