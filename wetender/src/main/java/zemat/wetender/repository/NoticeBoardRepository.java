package zemat.wetender.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.noticeBoard.NoticeBoard;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    Page<NoticeBoard> findByNoticeBoardTitleOrNoticeBoardContentContaining(String noticeBoardTitle, String noticeBoardContent, Pageable pageable);


}
