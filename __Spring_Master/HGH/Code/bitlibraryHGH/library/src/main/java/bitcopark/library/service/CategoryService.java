package bitcopark.library.service;

import bitcopark.library.entity.board.Category;
import bitcopark.library.exception.CategoryException;
import bitcopark.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // ADMIN만 사용가능한 메서드
    public Category createNewCategory(String categoryName){
        Category category = new Category(categoryName);

        validateDuplicateCategoryName(categoryName);

        categoryRepository.save(category);
        return category;
    }

    public Category createCategoryObject(String categoryName){
        return categoryRepository.findByCategoryName(categoryName).orElseThrow(()-> new CategoryException("존재하지 않는 카테고리 입니다 : " + categoryName));
    }

    private void validateDuplicateCategoryName(String categoryName) {
        if(categoryRepository.existsByCategoryName(categoryName)){
            throw new CategoryException("중복된 카테고리 입니다 : " + categoryName);
        }
    }

}
