package zemat.wetender.dto.memberDto;


import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.cocktail.CocktailLikes;
import zemat.wetender.domain.liquor.LiquorLikes;
import zemat.wetender.domain.member.Member;
import zemat.wetender.domain.member.Role;
import zemat.wetender.domain.noticeBoard.NoticeBoardLikes;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MemberDto {

    private Long id;
    private String memberIdName;
    private String memberPassword;
    private String memberName;
    private String memberNick;
    private String memberEmail;
    private String memberAddress;
    private String memberPhone;
    private Boolean memberExpiredFlag;
    private LocalDateTime memberExpiredDate;

    private String memberRole;

    private List<NoticeBoardLikes> noticeBoardLikesList = new ArrayList<>();
    private List<LiquorLikes> liquorLikesList = new ArrayList<>();
    private List<CocktailLikes> cocktailLikesList = new ArrayList<>();

    public MemberDto(Member member)
    {
        this.id = member.getId();
        this.memberIdName = member.getMemberIdName();
        this.memberPassword = member.getMemberPassword();
        this.memberName = member.getMemberName();
        this.memberNick = member.getMemberNick();
        this.memberEmail = member.getMemberEmail();
        this.memberAddress = member.getMemberAddress();
        this.memberPhone = member.getMemberPhone();
        this.memberExpiredFlag = member.getMemberExpiredFlag();
        this.memberExpiredDate = member.getMemberExpiredDate();

        this.memberRole = member.getMemberRole();

        this.noticeBoardLikesList = member.getNoticeBoardLikesList();
        this.liquorLikesList = member.getLiquorLikesList();
        this.cocktailLikesList = member.getCocktailLikesList();

    }


}
