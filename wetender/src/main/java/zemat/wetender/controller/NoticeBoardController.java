package zemat.wetender.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.noticeBoard.NoticeBoard;
import zemat.wetender.domain.noticeBoard.NoticeStatus;
import zemat.wetender.dto.noticeBoardDto.*;
import zemat.wetender.dto.noticeBoardDto.reply.NoticeBoardReplyDeleteDto;
import zemat.wetender.dto.noticeBoardDto.reply.NoticeBoardReplyInsertDto;
import zemat.wetender.service.MemberService;
import zemat.wetender.service.NoticeBoardService;

@Controller
@RequestMapping("/noticeBoard")
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;
    private final MemberService memberService;



    /*@ModelAttribute("sideMenuItems")
    public Map<String, Boolean> sideMenu() {
        Map<String, Boolean> sideMenuItems = new LinkedHashMap<>();
        sideMenuItems.put("noticeBoard", true);
        sideMenuItems.put("cocktail", false);
        sideMenuItems.put("liquor", false);
        sideMenuItems.put("community", false);
        sideMenuItems.put("suggestion", false);
        return sideMenuItems;
    }*/

    @ModelAttribute("sessionMember")
    public UserDetails getSessionMember() {
        return memberService.getSessionMember();
    }

    // 공지사항 메인 페이지
    @GetMapping("/main")
    public String main(Model model,
                       @PageableDefault(size = 5) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String keyword,
                       @RequestParam(required = false) String sortBy)  {


        if(sortBy == null) sortBy = "createdBy";

        int page = pageable.getPageNumber();

        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, sortBy));
        Page<NoticeBoard> noticeBoards = noticeBoardService.keywordFindPage(keyword, pageRequest);
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
        model.addAttribute("sortBy", sortBy);

        // 전체 공지 사항
        Page<NoticeBoardDto> noticeBoardAllDtos = noticeBoardService.statusFindPage(NoticeStatus.ALL, pageable);
        model.addAttribute("noticeBoardAllDtos", noticeBoardAllDtos);

        model.addAttribute("sideMenu", "nav-side-menu-noticeBoard");

        return "noticeBoard/main";
    }

    // 공지사항 메인 정렬
    @RequestMapping(value = "/sortBy", method = RequestMethod.POST)
    public String sortBy(Model model, NoticeBoardKeywordSortDto Dto,
                         Pageable pageable) {


        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, Dto.getSortBy()));

        Page<NoticeBoard> noticeBoards = noticeBoardService.keywordFindPage(Dto.getKeyword(), pageRequest);
        Page<NoticeBoardDto> noticeBoardDtos = noticeBoards.map(noticeBoard -> new NoticeBoardDto(noticeBoard));

        int startPage = Math.max(1, noticeBoardDtos.getPageable().getPageNumber() - 4);
        int endPage = Math.min(noticeBoardDtos.getTotalPages(), noticeBoardDtos.getPageable().getPageNumber() + 4);
        if (endPage == 0) {
            startPage = 0;
        }

        model.addAttribute("sortBy", Dto.getSortBy());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("noticeBoardDtos", noticeBoardDtos);

        return "/noticeBoard/main :: #noticeBoardResult";
    }


    // 공지사항 등록 페이지
    @GetMapping("/insert")
    public String insertForm(Model model) {
        model.addAttribute("noticeBoardInsertDto", new NoticeBoardInsertDto());
        return "noticeBoard/insert";
    }

    // 공지사항 등록
    @PostMapping("/insert")
    public String insert(@Validated @ModelAttribute NoticeBoardInsertDto noticeBoardInsertDto,
                         BindingResult bindingResult,
                         Model model) {

        if(bindingResult.hasErrors()) {
            return "noticeBoard/insert";
        }

        noticeBoardService.insert(noticeBoardInsertDto);

        return "redirect:/noticeBoard/main";
    }

    // 공지사항 수정 페이지
    @GetMapping("/update")
    public String updateForm(Model model, @RequestParam(required = false) Long noticeBoardId) {

        NoticeBoardDto noticeBoardDto = noticeBoardService.findById(noticeBoardId);

        model.addAttribute("noticeBoardDto", noticeBoardDto);

        return "noticeBoard/update";
    }

    // 공지사항 수정
    @PostMapping("/update")
    public String update(@Validated @ModelAttribute("noticeBoardDto") NoticeBoardUpdateDto noticeBoardUpdateDto,
                         BindingResult bindingResult,
                         Model model) {

        if(bindingResult.hasErrors()) {
            return "noticeBoard/update";
        }

        noticeBoardService.update(noticeBoardUpdateDto);

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
                         @RequestParam(required = true) Long noticeBoardId,
                         @PageableDefault(size=2) Pageable pageable) {

        // 게시글
        NoticeBoardDto noticeBoardDto = noticeBoardService.findById(noticeBoardId);
        model.addAttribute("noticeBoardDto", noticeBoardDto);

        // 조회수 +1
        noticeBoardService.viewsUp(noticeBoardId);

        // 5개 게시판
        Page<NoticeBoardDto> noticeBoardDtoList = noticeBoardService.detail_list(noticeBoardId, pageable);

        int startPage = Math.max(1, noticeBoardDtoList.getPageable().getPageNumber() - 3);
        int endPage = Math.min(noticeBoardDtoList.getTotalPages(), noticeBoardDtoList.getPageable().getPageNumber() + 3);
        if (endPage == 0) {
            startPage = 0;
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("noticeBoardDtoList", noticeBoardDtoList);

        // id 가저오기
        UserDetails sessionMember = memberService.getSessionMember();

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

        return "/noticeBoard/detail :: #replyResult";
    }

    // 공지사항 댓글 수정 Ajax
    @RequestMapping(value = "/replyEditAjax", method = RequestMethod.POST)
    public String replyEdit(Model model, NoticeBoardReplyInsertDto Dto) {
        noticeBoardService.replyEdit(Dto);
        NoticeBoardDto noticeBoardDto = noticeBoardService.findById(Dto.getNoticeBoardId());

        model.addAttribute("noticeBoardDto", noticeBoardDto);

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

        return "/noticeBoard/detail :: #likesResult";
    }

    // 페이지 Ajax GET ( 화면이 움직이는 문제가 있다... ) -> a태그에서 button 태크로 변경해 문제 해결
    @RequestMapping(value = "/movePage", method = RequestMethod.GET)
    public String movePage(Model model,
                           @RequestParam(required = true) Long noticeBoardId,
                           @PageableDefault(size = 2) Pageable pageable) {

        NoticeBoardDto noticeBoardDto = noticeBoardService.findById(noticeBoardId);
        model.addAttribute("noticeBoardDto", noticeBoardDto);

        Page<NoticeBoardDto> noticeBoardDtoList = noticeBoardService.detail_list(noticeBoardId, pageable);

        int startPage = Math.max(1, noticeBoardDtoList.getPageable().getPageNumber() - 3);
        int endPage = Math.min(noticeBoardDtoList.getTotalPages(), noticeBoardDtoList.getPageable().getPageNumber() + 3);
        if (endPage == 0) {
            startPage = 0;
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("noticeBoardDtoList", noticeBoardDtoList);

        return "/noticeBoard/detail :: #noticeBoardDetailList";
    }


    // 페이지 Ajax POST
    @RequestMapping(value = "/pageMove", method = RequestMethod.POST)
    public String pageMove(Model model,
                           NoticeBoardMovePageDto dto) {

        PageRequest pageRequest = PageRequest.of(dto.getPageNumber(), 2);
        Page<NoticeBoardDto> noticeBoardDtoList = noticeBoardService.detail_list(dto.getNoticeBoardId(), pageRequest);

        int startPage = Math.max(1, noticeBoardDtoList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(noticeBoardDtoList.getTotalPages(), noticeBoardDtoList.getPageable().getPageNumber() + 4);
        if (endPage == 0) {
            startPage = 0;
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("noticeBoardDtoList", noticeBoardDtoList);

        return "/noticeBoard/detail :: #noticeBoardDetailList";
    }






}
