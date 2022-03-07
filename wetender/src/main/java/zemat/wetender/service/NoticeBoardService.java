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
import zemat.wetender.dto.noticeBoardDto.NoticeBoardDto;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardInsertDto;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardLikesInsertDto;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardReplyInsertDto;
import zemat.wetender.repository.MemberRepository;
import zemat.wetender.repository.noticeBoard.NoticeBoardLikesRepository;
import zemat.wetender.repository.noticeBoard.NoticeBoardReplyRepository;
import zemat.wetender.repository.noticeBoard.NoticeBoardRepository;

import java.util.ArrayList;
import java.util.List;
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
    public Long insert(NoticeBoardInsertDto noticeBoardInsertDto) {

        NoticeBoard noticeBoard = new NoticeBoard(noticeBoardInsertDto);
        NoticeBoard save = noticeBoardRepository.save(noticeBoard);
        return save.getId();

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
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).get();
        long views = noticeBoard.getViews() + 1L;
        noticeBoard.setViews(views);
    }

    // 건의사항 ID를 기준 5개 조회하기
    public List<NoticeBoardDto> detail_list(Long noticeBoardID)
    {
        List<NoticeBoardDto> noticeBoardDtos = new ArrayList<>();
        Long start = 0L;
        int size = noticeBoardRepository.findAll().size();
        if(size <= 5) {
            start = 1L;
        }
        else
        {
            if(noticeBoardID == 1L || noticeBoardID == 2L) {
                start = 1L;
            }
            else if(noticeBoardID == size || noticeBoardID == size -1L) {
                start = size - 4L;
            }
            else {
                start = noticeBoardID - 2L;
            }
        }
        for(int i = 0; i < 5; i++)
        {
            if(start > size) {
                break;
            }
            if(noticeBoardRepository.existsById(start)) {
                noticeBoardDtos.add(new NoticeBoardDto(noticeBoardRepository.findById(start).get()));
            }
            start += 1;
        }
        return noticeBoardDtos;
    }

    // 공지사항 댓글 저장하기
    public Long replyInsert(NoticeBoardReplyInsertDto noticeBoardReplyInsertDto) {
        Long noticeBoardId = noticeBoardReplyInsertDto.getNoticeBoardId();
        NoticeBoard noticeBoard = noticeBoardRepository.getById(noticeBoardId);
        NoticeBoardReply noticeBoardReply = new NoticeBoardReply(noticeBoard, noticeBoardReplyInsertDto);
        noticeBoardReplyRepository.save(noticeBoardReply);
        return noticeBoardId;
    }

    // 공지사항 좋아요 저장하기, 삭제하기
    public Long likes(NoticeBoardLikesInsertDto noticeBoardLikesInsertDto) {
        String memberName = noticeBoardLikesInsertDto.getMemberNameLikes();
        Member member = memberRepository.findByMemberIdName(memberName).get();
        Long noticeBoardId = noticeBoardLikesInsertDto.getNoticeBoardIdLikes();
        NoticeBoard noticeBoard = noticeBoardRepository.getById(noticeBoardId);
        NoticeBoardLikes noticeBoardLikes = new NoticeBoardLikes(member, noticeBoard);

        Optional<NoticeBoardLikes> byMemberAndNoticeBoard = noticeBoardLikesRepository.findByMemberAndNoticeBoard(member, noticeBoard);

        if(byMemberAndNoticeBoard.isEmpty()) {
            noticeBoardLikesRepository.save(noticeBoardLikes);
        }
        else {
            noticeBoardLikesRepository.delete(byMemberAndNoticeBoard.get());
        }




        return noticeBoardId;
    }


}
