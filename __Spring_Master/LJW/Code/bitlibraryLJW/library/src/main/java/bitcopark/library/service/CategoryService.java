package bitcopark.library.service;

import bitcopark.library.domain.board.Category;
import bitcopark.library.domain.member.Member;
import bitcopark.library.domain.member.MemberRole;
import bitcopark.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void save(String name, Member member) {
        validateAdmin(member);
        validateDuplicateName(name);
        categoryRepository.save(Category.createCategory(name));
    }

    private void validateDuplicateName(String name) {
        if(categoryRepository.existsByName(name)){
            throw new IllegalArgumentException("exists: " + name);
        }
    }

    public void validateAdmin(Member member) {
        if (member.getRole() != MemberRole.ADMIN) {
            throw new IllegalArgumentException("not admin");
        }
    }
}
