package minnnisu.personalnote.controller;

import minnnisu.personalnote.service.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoticeController.class)
class NoticeControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    NoticeService noticeService;

    public NoticeControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockUser
    void givenNothing_whenRequestingNoticePage_thenReturnsIndexPage() throws Exception {
        // Given
        given(noticeService.findAll()).willReturn(List.of());

        // When & Then
        mockMvc.perform(get("/notice"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("notice/index"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("notices"));
    }
}