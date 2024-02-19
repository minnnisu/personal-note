package minnnisu.personalnote.admin;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.note.Note;
import minnnisu.personalnote.note.NoteService;
import minnnisu.personalnote.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.Authenticator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final NoteService noteService;

    /**
     * 관리자인 경우 노트 조회
     *
     * @ return index.html
     */
    @GetMapping
    public String getNoteAdmin(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        List<Note> notes = noteService.findByUser(user);
        model.addAttribute("notes", notes);
        return "admin/index";
    }


}