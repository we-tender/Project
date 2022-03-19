package zemat.wetender.repository.noticeBoard;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import zemat.wetender.domain.noticeBoard.NoticeBoard;
import zemat.wetender.domain.noticeBoard.NoticeStatus;

import javax.persistence.EntityManager;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    // 검색 조회 ( 제목, 내용 )
    Page<NoticeBoard> findByNoticeBoardTitleOrNoticeBoardContentContaining(String noticeBoardTitle, String noticeBoardContent, Pageable pageable);

    // 전체 공지사항 조회
    Page<NoticeBoard> findByStatusContaining(String noticeStatus, Pageable pageable);


    Page<NoticeBoard> findAll(Pageable pageable);




}
