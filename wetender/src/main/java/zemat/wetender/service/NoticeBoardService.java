package zemat.wetender.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.noticeBoard.NoticeBoard;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardInsertDto;
import zemat.wetender.repository.NoticeBoardRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    // 공지사항 저장
    public Long insert(NoticeBoardInsertDto noticeBoardInsertDto) {
        NoticeBoard noticeBoard = new NoticeBoard(noticeBoardInsertDto);
        NoticeBoard save = noticeBoardRepository.save(noticeBoard);
        return save.getId();
    }

    // 공지사항 조회
    public Page<NoticeBoard> keywordFindPage(String keyword, Pageable pageable) {
        return noticeBoardRepository.findByNoticeBoardTitleOrNoticeBoardContentContaining(keyword, keyword, pageable);
    }

}
