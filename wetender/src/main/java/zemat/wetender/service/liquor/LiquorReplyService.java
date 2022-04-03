package zemat.wetender.service.liquor;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorReply;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyDeleteDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyEditDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyInsertDto;
import zemat.wetender.repository.liquor.LiquorReplyRepository;
import zemat.wetender.repository.liquor.LiquorRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class LiquorReplyService {

    private final LiquorReplyRepository liquorReplyRepository;
    private final LiquorRepository liquorRepository;

    // 댓글 등록
    public LiquorDto insert(LiquorReplyInsertDto Dto) {

        Liquor liquor = liquorRepository.getById(Dto.getLiquorId());
        LiquorReply liquorReply = new LiquorReply(liquor, Dto);
        liquorReplyRepository.save(liquorReply);
        Liquor result = liquorRepository.getById(Dto.getLiquorId());
        result.repliesAdd();

        return new LiquorDto(result);
    }

    // 댓글 삭제
    public LiquorDto delete(LiquorReplyDeleteDto Dto) {
        LiquorReply liquorReply = liquorReplyRepository.getById(Dto.getLiquorReplyId());
        liquorReplyRepository.delete(liquorReply);
        Liquor liquor = liquorRepository.getById(Dto.getLiquorId());
        liquor.repliesRemove();

        return new LiquorDto(liquor);
    }

    // 댓글 수정
    public LiquorDto edit(LiquorReplyEditDto Dto) {
        LiquorReply liquorReply = liquorReplyRepository.getById(Dto.getLiquorReplyId());
        liquorReply.setLiquorReplyContent(Dto.getLiquorReplyContent());
        Liquor liquor = liquorRepository.getById(Dto.getLiquorId());

        return new LiquorDto(liquor);
    }

}
