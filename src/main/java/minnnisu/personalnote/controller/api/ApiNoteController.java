package minnnisu.personalnote.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.dto.note.NoteDeleteResponseDto;
import minnnisu.personalnote.dto.note.NoteRequestDto;
import minnnisu.personalnote.dto.note.NoteSaveResponseDto;
import minnnisu.personalnote.dto.note.NoteUserDto;
import minnnisu.personalnote.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class ApiNoteController {
    private final NoteService noteService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<NoteSaveResponseDto> saveNote(@AuthenticationPrincipal User user, @Valid @RequestBody NoteRequestDto noteRequestDto) {
        NoteUserDto noteUserDto = noteService.saveNote(user, noteRequestDto);
        return new ResponseEntity<>(NoteSaveResponseDto.fromDto(noteUserDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping
    public ResponseEntity<?> deleteNote(@AuthenticationPrincipal User user, @RequestParam Long id) {
        noteService.deleteNote(user, id);
        return new ResponseEntity<>(new NoteDeleteResponseDto(), HttpStatus.OK);

    }
}
