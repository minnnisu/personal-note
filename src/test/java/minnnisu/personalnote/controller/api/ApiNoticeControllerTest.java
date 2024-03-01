package minnnisu.personalnote.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.dto.ErrorResponseDto;
import minnnisu.personalnote.dto.NotValidRequestErrorResponseDto;
import minnnisu.personalnote.dto.notice.NoticeDeleteResponseDto;
import minnnisu.personalnote.dto.notice.NoticeDto;
import minnnisu.personalnote.dto.notice.NoticeRequestDto;
import minnnisu.personalnote.dto.notice.NoticeSaveResponseDto;
import minnnisu.personalnote.exception.CustomErrorException;
import minnnisu.personalnote.service.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiNoticeController.class)
class ApiNoticeControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    NoticeService noticeService;

    public ApiNoticeControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockUser
    void givenNewNotice_whenSavingNewNotice_thenSaveNoticeAndReturnNoticeDto() throws Exception {
        // Given
        String title = "새로운 공지사항";
        String content = "새로운 공지사항 입니다!";

        NoticeDto noticeDto = createNoticeDto(title, content);
        NoticeRequestDto noticeRequestDto = NoticeRequestDto.of(title, content);

        given(noticeService.saveNotice(any())).willReturn(noticeDto);

        // When & Then
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String requestBody = new ObjectMapper().writeValueAsString(noticeRequestDto);
        String responseBody = objectMapper.writeValueAsString(NoticeSaveResponseDto.fromNoticeDto(noticeDto));

        mockMvc.perform(
                        post("/api/notice").with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    @WithMockUser
    void givenNotValidNotice_whenSavingNewNotice_thenReturnError() throws Exception {
        // Given
        String title = "새로운 공지사항입니다. 새로운 공지사항입니다. 새로운 공지사항입니다. 새로운 공지사항입니다.";
        String content = "";

        NoticeRequestDto noticeRequestDto = NoticeRequestDto.of(title, content);

        // When & Then
        String requestBody = new ObjectMapper().writeValueAsString(noticeRequestDto);
        List<NotValidRequestErrorResponseDto.ErrorDescription> errorDescriptions = List.of(
                new NotValidRequestErrorResponseDto.ErrorDescription("title", "최대 20자까지 입력 가능합니다."),
                new NotValidRequestErrorResponseDto.ErrorDescription("content", "본문을 작성해세요.")
        );
        NotValidRequestErrorResponseDto notValidRequestErrorResponseDto =
                new NotValidRequestErrorResponseDto(
                        ErrorCode.NotValidRequestException.name(),
                        ErrorCode.NotValidRequestException.getMessage(),
                        errorDescriptions
                );
        String responseBody = new ObjectMapper().writeValueAsString(notValidRequestErrorResponseDto);

        mockMvc.perform(
                        post("/api/notice").with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(responseBody));
    }

    @WithMockUser
    @Test
    void givenNoticeId_whenDeletingNotice_thenReturnSuccess() throws Exception {
        // Given
        doNothing().when(noticeService).deleteNotice(any());

        // When & Then
        NoticeDeleteResponseDto noticeDeleteResponseDto =
                new NoticeDeleteResponseDto();
        String responseBody = new ObjectMapper().writeValueAsString(noticeDeleteResponseDto);

        mockMvc.perform(delete("/api/notice").with(csrf())
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @WithMockUser
    @Test
    void givenNotExistNoticeId_whenDeletingNotice_thenReturnError() throws Exception {
        // Given
        doThrow(new CustomErrorException(ErrorCode.NoSuchNoticeExistException)).when(noticeService).deleteNotice(any());

        // When & Then
        ErrorResponseDto errorResponseDto = ErrorResponseDto
                .fromException(new CustomErrorException(ErrorCode.NoSuchNoticeExistException));

        String responseBody = new ObjectMapper().writeValueAsString(errorResponseDto);


        mockMvc.perform(delete("/api/notice").with(csrf())
                        .param("id", "3"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(responseBody));
    }

    private NoticeDto createNoticeDto(String title, String content) {
        return NoticeDto.of(
                1L,
                title,
                content,
                LocalDateTime.of(2023, 1, 1, 14, 0, 0),
                LocalDateTime.of(2023, 1, 1, 16, 0, 0)
        );
    }
}