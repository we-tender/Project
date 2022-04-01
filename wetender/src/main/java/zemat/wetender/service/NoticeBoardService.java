package zemat.wetender.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.member.Member;
import zemat.wetender.domain.noticeBoard.NoticeBoard;
import zemat.wetender.domain.noticeBoard.NoticeBoardLikes;
import zemat.wetender.domain.noticeBoard.NoticeBoardReply;
import zemat.wetender.dto.noticeBoardDto.*;
import zemat.wetender.dto.noticeBoardDto.reply.NoticeBoardReplyInsertDto;
import zemat.wetender.repository.MemberRepository;
import zemat.wetender.repository.noticeBoard.NoticeBoardLikesRepository;
import zemat.wetender.repository.noticeBoard.NoticeBoardReplyRepository;
import zemat.wetender.repository.noticeBoard.NoticeBoardRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final NoticeBoardReplyRepository noticeBoardReplyRepository;
    private final NoticeBoardLikesRepository noticeBoardLikesRepository;
    private final MemberRepository memberRepository;

    // 공지사항 등록, 수정
    public Long insert(NoticeBoardInsertDto Dto) {
        NoticeBoard noticeBoard = new NoticeBoard(Dto);
        NoticeBoard save = noticeBoardRepository.save(noticeBoard);
        return save.getId();
    }

    public Long update(NoticeBoardUpdateDto Dto) {
        NoticeBoard noticeBoard = noticeBoardRepository.getById(Dto.getId());

        noticeBoard.setStatus(Dto.getStatus());
        noticeBoard.setNoticeBoardTitle(Dto.getNoticeBoardTitle());
        noticeBoard.setNoticeBoardContent(Dto.getNoticeBoardContent());

        return noticeBoard.getId();
    }

    // 공지사항 조회
    public Page<NoticeBoard> keywordFindPage(String keyword, Pageable pageable) {
        return noticeBoardRepository.findByNoticeBoardTitleOrNoticeBoardContentContaining(keyword, keyword, pageable);
    }

    // 공지사항 상태로 조회
    public Page<NoticeBoardDto> statusFindPage(String noticeStatus, Pageable pageable) {
        Page<NoticeBoard> byStatus = noticeBoardRepository.findByStatusContaining(noticeStatus, pageable);
        return byStatus.map(noticeBoard -> new NoticeBoardDto(noticeBoard));
    }

    // 공지사항 삭제
    public void delete(Long noticeBoardId)
    {
        NoticeBoard noticeBoard = noticeBoardRepository.getById(noticeBoardId);
        noticeBoardRepository.delete(noticeBoard);
    }

    // 아이디로 찾기
    public NoticeBoardDto findById(Long noticeBoardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).get();
        return new NoticeBoardDto(noticeBoard);
    }

    // 조회수 올리기
    public void viewsUp(Long noticeBoardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.getById(noticeBoardId);
        noticeBoard.viewsAdd();
    }

    // 건의사항 ID를 기준 5개 조회하기
    public Page<NoticeBoardDto> detail_list(Long noticeBoardID, Pageable pageable)
    {
//        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
//        int size = 3;
//        PageRequest pageRequest = PageRequest.of(page, size);
        Page<NoticeBoard> noticeBoards = noticeBoardRepository.findAll(pageable);
        Page<NoticeBoardDto> noticeBoardDtoPage = noticeBoards.map(noticeBoard -> new NoticeBoardDto(noticeBoard));




        return noticeBoardDtoPage;
    }

    // 공지사항 댓글 저장하기
    public Long replyInsert(NoticeBoardReplyInsertDto noticeBoardReplyInsertDto) {
        Long noticeBoardId = noticeBoardReplyInsertDto.getNoticeBoardId();
        NoticeBoard noticeBoard = noticeBoardRepository.getById(noticeBoardId);
        NoticeBoardReply noticeBoardReply = new NoticeBoardReply(noticeBoard, noticeBoardReplyInsertDto);
        noticeBoardReplyRepository.save(noticeBoardReply);
        noticeBoard.repliesAdd();
        return noticeBoardId;
    }

    // 공지사항 댓글 변경하기
    public void replyEdit(NoticeBoardReplyInsertDto Dto) {
        Long noticeBoardReplyId = Dto.getNoticeBoardReplyId();
        NoticeBoardReply byId = noticeBoardReplyRepository.getById(noticeBoardReplyId);

        System.out.println("================================");
        System.out.println("Dto = " + Dto);
        
        
        byId.setNoticeBoardReplyContent(Dto.getNoticeBoardReplyContent());
    }

    // 공지사항 댓글 삭제하기
    public void replyDelete(Long noticeBoardReplyId) {
        NoticeBoardReply noticeBoardReply = noticeBoardReplyRepository.getById(noticeBoardReplyId);
        noticeBoardReplyRepository.delete(noticeBoardReply);
    }


    // 공지사항 좋아요 저장하기, 삭제하기
    public Long likes(NoticeBoardLikesInsertDto noticeBoardLikesInsertDto) {
        String memberName = noticeBoardLikesInsertDto.getMemberNameLikes();
        Member member = memberRepository.findByMemberIdName(memberName).get();

        Long noticeBoardId = noticeBoardLikesInsertDto.getNoticeBoardIdLikes();
        NoticeBoard noticeBoard = noticeBoardRepository.getById(noticeBoardId);

        NoticeBoardLikes noticeBoardLikes = new NoticeBoardLikes(member, noticeBoard);

        Optional<NoticeBoardLikes> byMemberAndNoticeBoard = noticeBoardLikesRepository.findByMemberAndNoticeBoard(member, noticeBoard);

        if ( byMemberAndNoticeBoard.isEmpty() ) {
            noticeBoardLikesRepository.save(noticeBoardLikes);
            noticeBoard.likesAdd();
        }
        else {
            noticeBoardLikesRepository.delete(byMemberAndNoticeBoard.get());
            noticeBoard.likesRemove();
        }

        return noticeBoardId;
    }

    // 공지사항 좋아요 확인
    public boolean likesCheck(Long noticeBoardId, String username) {

        Member member = memberRepository.findByMemberIdName(username).get();
        NoticeBoard noticeBoard = noticeBoardRepository.getById(noticeBoardId);

        Optional<NoticeBoardLikes> byMemberAndNoticeBoard = noticeBoardLikesRepository.findByMemberAndNoticeBoard(member, noticeBoard);

        if ( byMemberAndNoticeBoard.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }


}
