package zemat.wetender.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.noticeBoard.NoticeBoard;
import zemat.wetender.domain.noticeBoard.NoticeBoardReply;
import zemat.wetender.domain.noticeBoard.NoticeStatus;
import zemat.wetender.dto.noticeBoardDto.*;
import zemat.wetender.service.MemberService;
import zemat.wetender.service.NoticeBoardService;

import javax.lang.model.SourceVersion;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/noticeBoard")
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;
    private final MemberService memberService;

    @ModelAttribute("sideMenuItems")
    public Map<String, Boolean> sideMenu() {
        Map<String, Boolean> sideMenuItems = new LinkedHashMap<>();
        sideMenuItems.put("noticeBoard", true);
        sideMenuItems.put("cocktail", false);
        sideMenuItems.put("liquor", false);
        sideMenuItems.put("community", false);
        sideMenuItems.put("suggestion", false);
        return sideMenuItems;
    }

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

        // id 가져오기
        UserDetails sessionMember = memberService.getSessionMember();
        model.addAttribute("sessionMember", sessionMember);



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

        model.addAttribute("sessionMember", memberService.getSessionMember());

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
                         @RequestParam(required = true) Long noticeBoardId) {

        // 게시글
        NoticeBoardDto noticeBoardDto = noticeBoardService.findById(noticeBoardId);
        model.addAttribute("noticeBoardDto", noticeBoardDto);

        // 조회수 +1
        noticeBoardService.viewsUp(noticeBoardId);

        // 5개 게시판
        List<NoticeBoardDto> noticeBoardDtos = noticeBoardService.detail_list(noticeBoardId);
        model.addAttribute("noticeBoardDtos", noticeBoardDtos);

        // id 가저오기
        UserDetails sessionMember = memberService.getSessionMember();
        model.addAttribute("sessionMember",sessionMember);

        if(sessionMember != null) {
            boolean checkResult = noticeBoardService.likesCheck(noticeBoardId, sessionMember.getUsername());
            model.addAttribute("checkResult", checkResult);
        }

        return "noticeBoard/detail";
    }

    // 공지사항 댓글 등록 Ajax
    @RequestMapping(value = "/replyInsertAjax", method = RequestMethod.POST)
    public String replyInsert(Model model, NoticeBoardReplyInsertDto Dto) {
        Long id = noticeBoardService.replyInsert(Dto);
        NoticeBoardDto noticeBoardDto = noticeBoardService.findById(id);

        model.addAttribute("noticeBoardDto", noticeBoardDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/noticeBoard/detail :: #replyResult";
    }

    // 공지사항 댓글 수정 Ajax
    @RequestMapping(value = "/replyEditAjax", method = RequestMethod.POST)
    public String replyEdit(Model model, NoticeBoardReplyInsertDto Dto) {
        noticeBoardService.replyEdit(Dto);
        NoticeBoardDto noticeBoardDto = noticeBoardService.findById(Dto.getNoticeBoardId());

        model.addAttribute("noticeBoardDto", noticeBoardDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/noticeBoard/detail :: #replyResult";
    }

    // 공지사항 댓글 삭제 Ajax
    @RequestMapping(value = "/replyDeleteAjax", method = RequestMethod.POST)
    public String replyDeleteAjax(Model model, NoticeBoardReplyDeleteDto Dto) {
        Long noticeBoardReplyId = Dto.getNoticeBoardReplyId();
        noticeBoardService.replyDelete(noticeBoardReplyId);

        Long noticeBoardId = Dto.getNoticeBoardId();
        NoticeBoardDto noticeBoardDto = noticeBoardService.findById(noticeBoardId);

        model.addAttribute("noticeBoardDto", noticeBoardDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/noticeBoard/detail :: #replyResult";
    }

    // 공지사항 좋아요
    @RequestMapping(value = "/likesInsert", method=RequestMethod.POST)
    public String likesInsert(NoticeBoardLikesInsertDto Dto, Model model) {
        Long id = noticeBoardService.likes(Dto);
        boolean checkResult = noticeBoardService.likesCheck(Dto.getNoticeBoardIdLikes(), Dto.getMemberNameLikes());
        model.addAttribute("checkResult", checkResult);


        NoticeBoardDto noticeBoardDto = noticeBoardService.findById(Dto.getNoticeBoardIdLikes());


        model.addAttribute("noticeBoardDto", noticeBoardDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/noticeBoard/detail :: #likesResult";
    }



    // 공지사항 정렬
    @RequestMapping(value = "/sortBy", method = RequestMethod.POST)
    public String sortBy(Model model, NoticeBoardKeywordSortDto Dto,
                         Pageable pageable) {

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, Dto.getSortBy()));

        Page<NoticeBoard> noticeBoards = noticeBoardService.keywordFindPage(Dto.getKeyword(), pageRequest);
        Page<NoticeBoardDto> noticeBoardDtos = noticeBoards.map(noticeBoard -> new NoticeBoardDto(noticeBoard));

        int startPage = Math.max(1, noticeBoardDtos.getPageable().getPageNumber() - 4);
        int endPage = Math.min(noticeBoardDtos.getTotalPages(), noticeBoardDtos.getPageable().getPageNumber() + 4);
        if (endPage == 0) {
            startPage = 0;
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("noticeBoardDtos", noticeBoardDtos);

        return "/noticeBoard/main :: #noticeBoardResult";
    }


}
