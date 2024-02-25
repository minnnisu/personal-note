package minnnisu.personalnote.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.dto.note.NoteResponseDto;
import minnnisu.personalnote.dto.note.NoteDto;
import minnnisu.personalnote.dto.note.NoteRequestDto;
import minnnisu.personalnote.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@Slf4j
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class ApiNoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponseDto> saveNote(@AuthenticationPrincipal User user, @Valid @RequestBody NoteRequestDto noteRequestDto){
        NoteDto noteDto = noteService.saveNote(user, noteRequestDto);
        return new ResponseEntity<>(NoteResponseDto.fromDto(noteDto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteNote(@AuthenticationPrincipal User user, @RequestParam Long id){
        noteService.deleteNote(user, id);
        return new ResponseEntity<>("노트를 성공적으로 삭제하였습니다", HttpStatus.OK);

    }
}
