package minnnisu.personalnote.controller;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.domain.Note;
import minnnisu.personalnote.dto.NoteRegisterDto;
import minnnisu.personalnote.service.NoteService;
import minnnisu.personalnote.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public String getNote(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        List<Note> notes = noteService.findByUser(user);
        model.addAttribute("notes", notes);

        return "note/index";
    }

    @PostMapping
    public String saveNote(Authentication authentication, @ModelAttribute NoteRegisterDto noteDto){
        User user = (User) authentication.getPrincipal();
        noteService.saveNote(user, noteDto.getTitle(), noteDto.getContent());
        return "redirect:note";
    }

    @DeleteMapping
    public String deleteNote(Authentication authentication, @RequestParam Long id){
        User user = (User) authentication.getPrincipal();
        noteService.deleteNote(user, id);
        return "redirect:note";
    }

}
