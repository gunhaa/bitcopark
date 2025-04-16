package bitcopark.library.service.Class;

import bitcopark.library.entity.Class.ClassApplicant;
import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.Class.ClassApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClassApplicantService {

    private final ClassApplicantRepository classApplicantRepository;

    @Transactional
    public ClassApplicant registerClassApplicant(Board board, Member member){
        ClassApplicant classApplicant = ClassApplicant.builder()
                .board(board)
                .member(member)
                .build();
        return classApplicantRepository.save(classApplicant);
    }

}
