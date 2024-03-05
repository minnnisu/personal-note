package minnnisu.personalnote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.domain.Note;
import minnnisu.personalnote.domain.NoteImage;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.dto.note.*;
import minnnisu.personalnote.exception.CustomErrorException;
import minnnisu.personalnote.repository.NoteImageRepository;
import minnnisu.personalnote.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteImageRepository noteImageRepository;
    private final FileService fileService;

    public List<NoteAdminDto> findAll() {
        List<Note> notes = noteRepository.findAll();
        return notes
                .stream()
                .map(NoteAdminDto::fromEntity)
                .toList();
    }

    public List<NoteSummaryDto> findByUser(User user) {
        List<Note> notes = noteRepository.findByUserOrderByIdDesc(user);

        return notes
                .stream()
                .map(NoteSummaryDto::fromEntity)
                .toList();
    }

    public NoteUserDto saveNote(User user, NoteRequestDto noteRequestDto, List<MultipartFile> files) {
        List<NoteImage> noteImages = new ArrayList<>();

        // 노트 저장
        Note note = noteRepository.save(new Note(
                noteRequestDto.getTitle(),
                noteRequestDto.getContent(),
                user
        ));

        // 스토리지 및 데이터베이스에 노트 이미지 저장
        if (files != null) {
            for (MultipartFile file : files) {
                if (!fileService.checkImageFile(file)) {
                    onSaveNoteFailure(note, noteImages);
                    throw new CustomErrorException(ErrorCode.NoImageFileError);
                }

                try {
                    String uniqueFileName = fileService.saveNoteImage(file);
                    noteImages.add(noteImageRepository.save(new NoteImage(note, uniqueFileName)));
                } catch (NullPointerException e) {
                    onSaveNoteFailure(note, noteImages);
                    throw new CustomErrorException(ErrorCode.NoImageNameError);

                } catch (Exception e) {
                    onSaveNoteFailure(note, noteImages);
                    throw new CustomErrorException(ErrorCode.InternalServerError);
                }
            }

        }

        return NoteUserDto.fromEntity(note, noteImages);
    }

    void onSaveNoteFailure(Note note, List<NoteImage> noteImages) {
        noteRepository.delete(note);
        for (NoteImage noteImage : noteImages) {
            noteImageRepository.deleteById(noteImage.getId());
            fileService.onSaveNoteFailure(noteImage.getImageName());
        }
    }

    public void deleteNote(User user, Long noteId) {
        Note note = noteRepository.findByIdAndUser(noteId, user)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchNoteExistException));
        noteRepository.delete(note);
    }

    public NoteDetailDto getNoteDetail(Long id) {
        Note note = noteRepository.getById(id);
        List<NoteImage> noteImages = noteImageRepository.findNoteImageByNote(note);
        return NoteDetailDto.fromEntity(note, noteImages);
    }
}
