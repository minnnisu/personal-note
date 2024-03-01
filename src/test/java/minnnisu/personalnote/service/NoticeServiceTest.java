package minnnisu.personalnote.service;

import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.domain.Notice;
import minnnisu.personalnote.dto.notice.NoticeDto;
import minnnisu.personalnote.dto.notice.NoticeRequestDto;
import minnnisu.personalnote.exception.CustomErrorException;
import minnnisu.personalnote.repository.NoticeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {
    @InjectMocks
    NoticeService sut;

    @Mock
    NoticeRepository noticeRepository;

    @Test
    void givenNothing_whenRequestingNoticeList_thenReturnNoticeList() {
        // Given
        Notice notice = createNotice("새로운 공지사항", "새로운 공지사항입니다.");
        given(noticeRepository.findAll((Sort) any()))
                .willReturn(List.of(notice));

        // When
        List<NoticeDto> result = sut.findAll();

        // Then
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0).getId()).isEqualTo(NoticeDto.fromEntity(notice).getId());
        Assertions.assertThat(result.get(0).getTitle()).isEqualTo(NoticeDto.fromEntity(notice).getTitle());
        Assertions.assertThat(result.get(0).getContent()).isEqualTo(NoticeDto.fromEntity(notice).getContent());
        Assertions.assertThat(result.get(0).getCreatedAt()).isEqualTo(NoticeDto.fromEntity(notice).getCreatedAt());
        Assertions.assertThat(result.get(0).getUpdatedAt()).isEqualTo(NoticeDto.fromEntity(notice).getUpdatedAt());
    }

    @Test
    void givenNewNotice_whenSavingNewNotice_thenSaveNewNoticeAndReturnNoticeDto() {
        // Given
        Notice notice = createNotice("새로운 공지사항", "새로운 공지사항입니다.");
        NoticeRequestDto noticeRequestDto =
                NoticeRequestDto.builder()
                        .title("새로운 공지사항")
                        .content("새로운 공지사항입니다.")
                        .build();

        given(noticeRepository.save(any())).willReturn(notice);


        // When
        NoticeDto result = sut.saveNotice(noticeRequestDto);

        // Then
        Assertions.assertThat(result.getId()).isEqualTo(notice.getId());
        Assertions.assertThat(result.getTitle()).isEqualTo(notice.getTitle());
        Assertions.assertThat(result.getContent()).isEqualTo(notice.getContent());
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(notice.getCreatedAt());
        Assertions.assertThat(result.getUpdatedAt()).isEqualTo(notice.getUpdatedAt());
    }

    @Test
    void givenNoExistNoticeId_whenDeletingNotice_thenThrowsException() {
        // Given

        // When
        given(noticeRepository.findById(2L))
                .willThrow(new CustomErrorException(ErrorCode.NoSuchNoticeExistException));
        Throwable thrown = catchThrowable(() -> sut.deleteNotice(2L));

        // Then
        Assertions.assertThat(thrown)
                .isInstanceOf(CustomErrorException.class)
                .hasMessage(ErrorCode.NoSuchNoticeExistException.getMessage());
    }

    private Notice createNotice(String title, String content) {
        return Notice.of(
                1L,
                title,
                content,
                LocalDateTime.of(2023, 1, 1, 14, 0, 0),
                LocalDateTime.of(2023, 1, 1, 16, 0, 0)
        );

    }
}