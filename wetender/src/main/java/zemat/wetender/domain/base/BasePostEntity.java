package zemat.wetender.domain.base;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class BasePostEntity extends BaseEntity{

    //  조회수, 좋아요, 댓글수

    @Column(columnDefinition = "integer default 0")
    private long views;

    @Column(columnDefinition = "integer default 0")
    private long likes;

    @Column(columnDefinition = "integer default 0")
    private long replies;

    public void viewsAdd() {this.views += 1L;}

    public void likesAdd() {
        this.likes += 1L;
    }

    public void likesRemove() {
        this.likes -= 1L;
    }

    public void repliesAdd() {
        this.replies += 1L;
    }

    public void repliesRemove() {
        this.replies -= 1L;
    }




}
