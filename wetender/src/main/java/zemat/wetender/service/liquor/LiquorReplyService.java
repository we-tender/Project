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
    public LiquorDto insert(LiquorReplyInsertDto dto) {

        Liquor liquor = liquorRepository.getById(dto.getLiquorId());
        LiquorReply liquorReply = new LiquorReply(liquor, dto);
        liquorReplyRepository.save(liquorReply);
        liquor.repliesAdd();

        return new LiquorDto(liquor);
    }

    // 댓글 삭제
    public LiquorDto delete(LiquorReplyDeleteDto dto) {
        LiquorReply liquorReply = liquorReplyRepository.getById(dto.getLiquorReplyId());
        liquorReplyRepository.delete(liquorReply);
        Liquor liquor = liquorRepository.getById(dto.getLiquorId());
        liquor.repliesRemove();

        return new LiquorDto(liquor);
    }

    // 댓글 수정
    public LiquorDto edit(LiquorReplyEditDto dto) {
        LiquorReply liquorReply = liquorReplyRepository.getById(dto.getLiquorReplyId());
        liquorReply.setLiquorReplyContent(dto.getLiquorReplyContent());
        Liquor liquor = liquorRepository.getById(dto.getLiquorId());

        return new LiquorDto(liquor);
    }

}
