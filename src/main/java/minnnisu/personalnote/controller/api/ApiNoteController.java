package minnnisu.personalnote.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.dto.note.*;
import minnnisu.personalnote.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class ApiNoteController {
    private final NoteService noteService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/image/{id}")
    public ResponseEntity<NoteImageListResponseDto> getNoteImages(@AuthenticationPrincipal User user,
                                                                  @PathVariable Long id) {
        List<NoteImageDto> noteImages = noteService.getNoteImages(user, id);
        return new ResponseEntity<>(NoteImageListResponseDto.fromDto(noteImages), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<NoteSaveResponseDto> saveNote(@AuthenticationPrincipal User user,
                                                        @Valid @RequestPart NoteSaveRequestDto note,
                                                        @RequestPart(required = false) List<MultipartFile> files) {

        NoteUserDto noteUserDto = noteService.saveNote(user, note, files);
        return new ResponseEntity<>(NoteSaveResponseDto.fromDto(noteUserDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<NoteUpdateResponseDto> updateNote(@AuthenticationPrincipal User user,
                                                            @PathVariable Long id,
                                                            @Valid @RequestPart NoteUpdateRequestDto note,
                                                            @RequestPart(required = false) List<MultipartFile> files) {
        NoteUserDto noteUserDto = noteService.updateNote(user, id, note, files);
        return new ResponseEntity<>(NoteUpdateResponseDto.fromDto(noteUserDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@AuthenticationPrincipal User user, @PathVariable Long id) {
        noteService.deleteNote(user, id);
        return new ResponseEntity<>(new NoteDeleteResponseDto(), HttpStatus.OK);
    }
}
