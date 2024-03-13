package minnnisu.personalnote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.constant.UploadPathType;
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

    public NoteUserDto saveNote(User user, NoteSaveRequestDto noteSaveRequestDto, List<MultipartFile> files) {
        List<String> addedImages = new ArrayList<>();

        // 노트 저장
        Note note = noteRepository.save(new Note(
                noteSaveRequestDto.getTitle(),
                noteSaveRequestDto.getContent(),
                user
        ));

        // 스토리지 및 데이터베이스에 노트 이미지 저장
        if (files != null) {
            for (MultipartFile file : files) {
                if (!fileService.checkImageFile(file)) {
                    fileService.deleteFiles(UploadPathType.IMAGE, addedImages);
                    throw new CustomErrorException(ErrorCode.NoImageFileError);
                }

                try {
                    String uniqueFileName = fileService.saveFiles(UploadPathType.IMAGE, file);
                    NoteImage noteImage = noteImageRepository.save(new NoteImage(note, uniqueFileName));
                    addedImages.add(noteImage.getImageName());
                } catch (NullPointerException e) {
                    fileService.deleteFiles(UploadPathType.IMAGE, addedImages);
                    throw new CustomErrorException(ErrorCode.NoImageNameError);

                } catch (Exception e) {
                    fileService.deleteFiles(UploadPathType.IMAGE, addedImages);
                    throw new CustomErrorException(ErrorCode.InternalServerError);
                }
            }

        }

        return NoteUserDto.fromEntity(note, noteImageRepository.findNoteImageByNote(note));
    }

    public NoteUserDto updateNote(User user, Long noteId, NoteUpdateRequestDto noteUpdateRequestDto, List<MultipartFile> additionTargetFiles) {
        List<String> addedImages = new ArrayList<>();

        Note note = noteRepository.findByIdAndUser(noteId, user).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchNoteExistException));

        // 노트 제목 및 본문 업데이트
        note.update(noteUpdateRequestDto.getTitle(), noteUpdateRequestDto.getContent());
        noteRepository.save(note);


        // 새로운 이미지 추가
        if (additionTargetFiles != null) {
            for (MultipartFile file : additionTargetFiles) {
                if (!fileService.checkImageFile(file)) {
                    fileService.deleteFiles(UploadPathType.IMAGE, addedImages);
                    throw new CustomErrorException(ErrorCode.NoImageFileError);
                }

                try {
                    String uniqueFileName = fileService.saveFiles(UploadPathType.IMAGE, file);
                    NoteImage noteImage = noteImageRepository.save(new NoteImage(note, uniqueFileName));
                    addedImages.add(noteImage.getImageName());
                } catch (NullPointerException e) {
                    fileService.deleteFiles(UploadPathType.IMAGE, addedImages);
                    throw new CustomErrorException(ErrorCode.NoImageNameError);

                } catch (Exception e) {
                    fileService.deleteFiles(UploadPathType.IMAGE, addedImages);
                    throw new CustomErrorException(ErrorCode.InternalServerError);
                }
            }
        }

        // 삭제 할 이미지 삭제
        List<String> deletionTargetImages = noteUpdateRequestDto.getDeletionTargetImages();
        if (deletionTargetImages != null) {
            for (String deletionTargetImage : deletionTargetImages) {
                NoteImage noteImage = noteImageRepository.findByImageNameAndNote(deletionTargetImage, note)
                        .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchNoteImageExistError));
                noteImageRepository.delete(noteImage);
            }

            fileService.deleteFiles(UploadPathType.IMAGE, deletionTargetImages);
        }

        return NoteUserDto.fromEntity(note, noteImageRepository.findNoteImageByNote(note));
    }
}
