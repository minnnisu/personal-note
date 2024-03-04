package minnnisu.personalnote.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.dto.note.NoteDetailDto;
import minnnisu.personalnote.dto.note.NoteSummaryDto;
import minnnisu.personalnote.service.NoteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public String getNote(@AuthenticationPrincipal User user, Model model) {
        List<NoteSummaryDto> notes = noteService.findByUser(user);
        model.addAttribute("notes", notes);

        return "note/index";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/register")
    public String getNoteRegisterPage() {
        return "note/register";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public String getNoteDetailPage(@PathVariable Long id, Model model) {
        NoteDetailDto noteDetailDto = noteService.getNoteDetail(id);
        model.addAttribute(noteDetailDto);
        return "note/detail";
    }
}
