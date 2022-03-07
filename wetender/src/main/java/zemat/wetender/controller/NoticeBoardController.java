package zemat.wetender.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.noticeBoard.NoticeBoard;
import zemat.wetender.domain.noticeBoard.NoticeStatus;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardDto;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardInsertDto;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardLikesInsertDto;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardReplyInsertDto;
import zemat.wetender.service.NoticeBoardService;

import java.util.List;

@Controller
@RequestMapping("/noticeBoard")
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;


    // 공지사항 메인 페이지
    @GetMapping("/main")
    public String main(Model model,
                       @PageableDefault(size = 10) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String keyword) {

        // 검색 기능
        Page<NoticeBoard> noticeBoards = noticeBoardService.keywordFindPage(keyword, pageable);
        Page<NoticeBoardDto> noticeBoardDtos = noticeBoards.map(noticeBoard -> new NoticeBoardDto(noticeBoard));
        model.addAttribute("noticeBoardDtos", noticeBoardDtos);

        // 페이지
        int startPage = Math.max(1, noticeBoardDtos.getPageable().getPageNumber() - 4);
        int endPage = Math.min(noticeBoardDtos.getTotalPages(), noticeBoardDtos.getPageable().getPageNumber() + 4);
        if (endPage == 0) {
            startPage = 0;
        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // 전체 공지 사항
        Page<NoticeBoardDto> noticeBoardAllDtos = noticeBoardService.statusFindPage(NoticeStatus.ALL, pageable);
        model.addAttribute("noticeBoardAllDtos", noticeBoardAllDtos);

        return "noticeBoard/main";

    }

    // 공지사항 등록, 수정 페이지
    @GetMapping("/insert")
    public String insertForm(Model model, @RequestParam(required = false) Long noticeBoardId) {

        if(noticeBoardId != null) {
            NoticeBoardDto noticeBoardDto = noticeBoardService.findById(noticeBoardId);
            model.addAttribute("noticeBoardDto", noticeBoardDto);
        }

        else {
            NoticeBoardDto noticeBoardDto = new NoticeBoardDto();
            model.addAttribute("noticeBoardDto", noticeBoardDto);
        }

        return "noticeBoard/insert";

    }

    // 공지사항 등록
    @PostMapping("/insert")
    public String insert(@ModelAttribute NoticeBoardInsertDto noticeBoardInsertDto) {
        noticeBoardService.insert(noticeBoardInsertDto);
        return "redirect:/noticeBoard/main";
    }

    // 공지사항 삭제
    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long noticeBoardId) {
        noticeBoardService.delete(noticeBoardId);
        return "redirect:/noticeBoard/main";
    }

    // 공지사항 게시글 페이지
    @GetMapping("/detail")
    public String detail(Model model,
                         @RequestParam(required = true) Long noticeBoardId)
    {

        // 게시글
        NoticeBoardDto noticeBoardDto = noticeBoardService.findById(noticeBoardId);
        model.addAttribute("noticeBoardDto", noticeBoardDto);

        // 조회수 +1
        noticeBoardService.viewsUp(noticeBoardId);

        // 5개 게시판
        List<NoticeBoardDto> noticeBoardDtos = noticeBoardService.detail_list(noticeBoardId);
        model.addAttribute("noticeBoardDtos", noticeBoardDtos);

        // id 가저오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("sessionMember", null);
        } else {
            UserDetails principalDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("sessionMember", principalDetails);
        }

        return "noticeBoard/detail";
    }

    // 공지사항 댓글 등록
    @PostMapping("/replyInsert")
    public String replyInsert(@ModelAttribute NoticeBoardReplyInsertDto noticeBoardReplyInsertDto) {
        Long id = noticeBoardService.replyInsert(noticeBoardReplyInsertDto);
        return "redirect:/noticeBoard/detail?noticeBoardId=" + id;
    }



    // 공지사항 좋아요
    @PostMapping("/likesInsert")
    public String likesInsert(@ModelAttribute NoticeBoardLikesInsertDto noticeBoardLikesInsertDto) {
        Long id = noticeBoardService.likes(noticeBoardLikesInsertDto);
        return "redirect:/noticeBoard/detail?noticeBoardId=" + id;
    }



}
