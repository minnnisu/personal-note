package minnnisu.personalnote.controller.api;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.dto.notice.NoticeDeleteResponseDto;
import minnnisu.personalnote.dto.notice.NoticeDto;
import minnnisu.personalnote.dto.notice.NoticeRequestDto;
import minnnisu.personalnote.dto.notice.NoticeSaveResponseDto;
import minnnisu.personalnote.service.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class ApiNoticeController {
    private final NoticeService noticeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<NoticeSaveResponseDto> saveNotice(@Valid @RequestBody NoticeRequestDto noticeRequestDto) {
        NoticeDto noticeDto = noticeService.saveNotice(noticeRequestDto);
        return new ResponseEntity<>(NoticeSaveResponseDto.fromNoticeDto(noticeDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<NoticeDeleteResponseDto> deleteNotice(@RequestParam Long id) {
        noticeService.deleteNotice(id);
        return new ResponseEntity<>(new NoticeDeleteResponseDto(), HttpStatus.OK);
    }

}
