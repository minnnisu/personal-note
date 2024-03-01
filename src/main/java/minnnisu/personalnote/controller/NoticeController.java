package minnnisu.personalnote.controller;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.dto.notice.NoticeDto;
import minnnisu.personalnote.service.NoticeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String getNotice(Model model) {
        List<NoticeDto> notices = noticeService.findAll();
        model.addAttribute("notices", notices);
        return "notice/index";
    }
}
