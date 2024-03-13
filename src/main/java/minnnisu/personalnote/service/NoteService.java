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

    public NoteUserDto saveNote(User user, NoteSaveRequestDto noteSaveRequestDto, List<MultipartFile> files) {
        List<NoteImage> noteImages = new ArrayList<>();

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

    /**
     * 1. 업데이트 요청 받은 노트의 id와 User 객체와 매칭되는 노트가 실제로 존재하는지 확인
     * 1-1. 존재하지 않으면 NoSuchNoteExistException 에러를 발생시키고 에러를 응답하여 로직을 종료한다.
     * 2. 반복문을 통해 additionTargetFiles에 대해 다음을 반복한다
     * 2-1. additionTargetFiles에 담긴 이미지 파일을 저장한다
     * 2-2. additionTargetFiles에 담긴 이미지 파일의 정보를 데이터베이스 저장한다.
     * 2-3. 1과 2에서 에러가 발생할 경우 현재까지 저장한 이미지 파일 및 파일의 정보를 삭제하고 에러를 발생시키고 에러를 에러를 응답하고 로직을 종료한다.
     * 3. 반복문을 통해 noteUpdateRequestDto의 deletionTargetFiles에 대해 다음을 반복한다.
     * 3-1. 노트 id와 삭제할 파일 이름을 이용해서 해당 파일이 존재하는지 확인한다. 존재하지 않는다면 다음 반복으로 넘어간다.
     * 3-2. 노트 id와 삭제할 파일 이름을 이용해서 해당 파일을 삭제한다
     * 3-3. 노트 id와 삭제할 파일 이름을 이용해서 해당 파일의 정보를 데이터베이스에서 삭제한다
     * 3-4. 2과 3에서 에러가 발생할 경우 2의 반복 작업 및 현재까지의 이미지 삭제를 취소하고 에러를 발생시키고 에러를 에러를 응답하고 로직을 종료한다.
     * 4. 노트 정보 업데이트
     * 4-1. 노트 정보 업데이트에 실패하면 2와 3의 반복작업을 취소하고 에러를 발생시키고 에러를 에러를 응답하고 로직을 종료한다.
     * 5. noteUpdateResponseDto를 반환한다.
     */

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
                    String uniqueFileName = fileService.saveNoteImage(file);
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
