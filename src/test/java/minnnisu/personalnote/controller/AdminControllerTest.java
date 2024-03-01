package minnnisu.personalnote.controller;

import minnnisu.personalnote.service.NoteService;
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


@WebMvcTest(AdminController.class)
class AdminControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    NoteService noteService;

    public AdminControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockUser
    void givenNothing_whenRequestingAdminPage_thenReturnsIndexPage() throws Exception {
        // Given
        given(noteService.findAll()).willReturn(List.of());

        // When & Then
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("admin/index"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("notes"));
    }
}