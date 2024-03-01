package minnnisu.personalnote.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.dto.ErrorResponseDto;
import minnnisu.personalnote.dto.NotValidRequestErrorResponseDto;
import minnnisu.personalnote.dto.note.NoteDeleteResponseDto;
import minnnisu.personalnote.dto.note.NoteRequestDto;
import minnnisu.personalnote.dto.note.NoteSaveResponseDto;
import minnnisu.personalnote.dto.note.NoteUserDto;
import minnnisu.personalnote.exception.CustomErrorException;
import minnnisu.personalnote.service.NoteService;
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


@WebMvcTest(ApiNoteController.class)
class ApiNoteControllerTest {
    @MockBean
    private NoteService noteService;

    private final MockMvc mockMvc;

    public ApiNoteControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockUser
    void givenNewNote_whenSavingNote_thenSaveNoteAndReturnNoteInfo() throws Exception {
        // Given
        String title = "새로운 노트";
        String content = "새로운 노트 입니다!";
        NoteRequestDto noteRequestDto = NoteRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        given(noteService.saveNote(any(), any())).willReturn(createNoteDto(title, content));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // When & Then
        String requestBody = new ObjectMapper().writeValueAsString(noteRequestDto);
        String responseBody = objectMapper.writeValueAsString(NoteSaveResponseDto.fromDto(createNoteDto(title, content)));

        mockMvc.perform(
                        post("/api/note").with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(responseBody));


    }

    @Test
    @WithMockUser
    void givenNotValidNote_whenSavingNewNote_thenReturnError() throws Exception {
        // Given
        String title = "새로운 노트입니다. 새로운 노트입니다. 새로운 노트입니다. 새로운 노트입니다.";
        String content = "";
        NoteRequestDto noteRequestDto = NoteRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        // When & Then
        String requestBody = new ObjectMapper().writeValueAsString(noteRequestDto);
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
                        post("/api/note").with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(responseBody));
    }

    @WithMockUser
    @Test
    void givenNoteId_whenDeletingNote_thenReturnSuccess() throws Exception {
        // Given
        doNothing().when(noteService).deleteNote(any(), any());

        // When & Then
        NoteDeleteResponseDto noteDeleteResponseDto =
                new NoteDeleteResponseDto();
        String responseBody = new ObjectMapper().writeValueAsString(noteDeleteResponseDto);

        mockMvc.perform(delete("/api/note").with(csrf())
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @WithMockUser
    @Test
    void givenNotExistNoteId_whenDeletingNote_thenReturnError() throws Exception {
        // Given
        doThrow(new CustomErrorException(ErrorCode.NoSuchNoteExistException)).when(noteService).deleteNote(any(), any());

        // When & Then
        ErrorResponseDto errorResponseDto = ErrorResponseDto
                .fromException(new CustomErrorException(ErrorCode.NoSuchNoteExistException));

        String responseBody = new ObjectMapper().writeValueAsString(errorResponseDto);


        mockMvc.perform(delete("/api/note").with(csrf())
                        .param("id", "3"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(responseBody));
    }

    private NoteUserDto createNoteDto(String title, String content) {
        return NoteUserDto.of(
                1L,
                title,
                content,
                LocalDateTime.of(2023, 1, 1, 14, 0, 0),
                LocalDateTime.of(2023, 1, 1, 16, 0, 0)
        );
    }
}