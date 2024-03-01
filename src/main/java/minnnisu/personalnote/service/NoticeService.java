package minnnisu.personalnote.service;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.domain.Notice;
import minnnisu.personalnote.dto.notice.NoticeDto;
import minnnisu.personalnote.dto.notice.NoticeRequestDto;
import minnnisu.personalnote.exception.CustomErrorException;
import minnnisu.personalnote.repository.NoticeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public List<NoticeDto> findAll() {
        return noticeRepository.findAll(Sort.by(Direction.DESC, "id"))
                .stream()
                .map(NoticeDto::fromEntity)
                .toList();
    }

    public NoticeDto saveNotice(NoticeRequestDto noticeRequestDto) {
        Notice notice = noticeRepository.save(new Notice(
                noticeRequestDto.getTitle(),
                noticeRequestDto.getContent()
        ));

        return NoticeDto.fromEntity(notice);
    }

    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchNoticeExistException));
        noticeRepository.delete(notice);
    }
}
