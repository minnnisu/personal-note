package minnnisu.personalnote.controller.api;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.dto.note.NoteResponseDto;
import minnnisu.personalnote.dto.note.NoteDto;
import minnnisu.personalnote.dto.note.NoteRequestDto;
import minnnisu.personalnote.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class ApiNoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponseDto> saveNote(Authentication authentication, @Valid @RequestBody NoteRequestDto noteRequestDto){
        User user = (User) authentication.getPrincipal();
        NoteDto noteDto = noteService.saveNote(user, noteRequestDto);
        return new ResponseEntity<>(NoteResponseDto.fromDto(noteDto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteNote(Authentication authentication, @RequestParam Long id){
        User user = (User) authentication.getPrincipal();
        noteService.deleteNote(user, id);
        return new ResponseEntity<>("노트를 성공적으로 삭제하였습니다", HttpStatus.OK);

    }
}
