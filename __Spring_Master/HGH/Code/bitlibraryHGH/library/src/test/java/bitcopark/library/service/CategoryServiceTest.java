package bitcopark.library.service;

import bitcopark.library.entity.board.Category;
import bitcopark.library.exception.CategoryException;
import bitcopark.library.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EntityManager em;


    @Test
    public void 관리자_카테고리생성(){

        //given
        Category category = categoryService.createNewCategory("역사");

        //when
        Category findCategory = categoryRepository.findByCategoryName("역사").get();

        //then
        assertThat(category).isEqualTo(findCategory);
    }

    @Test
    public void 관리자_중복카테고리생성_오류발생(){

        //given
        Category category = categoryService.createNewCategory("역사");

        //when , then
        assertThrows(CategoryException.class, ()->{
            categoryService.createNewCategory("역사");
        });
    }

    @Test
    public void 카테고리_오브젝트_생성(){
        //given
        Category category = categoryService.createNewCategory("역사");
        
        //when
        Category findCategory = categoryService.createCategoryObject("역사");

        //then
        assertThat(category).isEqualTo(findCategory);
    }

    @Test
    public void 카테고리_오브젝트_생성실패_존재하지않는_카테고리(){


        //given , when , then

        Assertions.assertThrows(CategoryException.class, ()->{
            Category findCategory = categoryService.createCategoryObject("역사");
        });

    }


}