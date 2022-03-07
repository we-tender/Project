package zemat.wetender.domain.likes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class noticeBoardLikes {

    @Id @GeneratedValue
    @Column(name = "notice_board_likes_id")
    private Long id;



}
