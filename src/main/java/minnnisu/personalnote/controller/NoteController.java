package minnnisu.personalnote.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.dto.note.NoteSaveResponseDto;
import minnnisu.personalnote.service.NoteService;
import minnnisu.personalnote.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public String getNote(Authentication authentication, Model model) {
        log.info("GET /note");
        User user = (User) authentication.getPrincipal();
        List<NoteSaveResponseDto> notes = noteService.findByUser(user)
                .stream()
                .map(NoteSaveResponseDto::fromDto)
                .toList();
        model.addAttribute("notes", notes);

        return "note/index";
    }
}
