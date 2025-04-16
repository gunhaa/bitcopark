package bitcopark.library.repository.Class;

import bitcopark.library.entity.Class.ClassApplicant;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassApplicantRepository extends JpaRepository<ClassApplicant,Long> {

    List<ClassApplicant> findByMember(Member member);

}
