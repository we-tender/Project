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

    //  조회수와 좋아요를 포함함

    @Column(columnDefinition = "integer default 0")
    private long views;




}
