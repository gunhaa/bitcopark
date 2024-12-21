package bitcopark.library.service;

import bitcopark.library.entity.Book.Book;
import bitcopark.library.exception.BookTitleNotFoundException;
import bitcopark.library.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;
    
    @Test
    public void 책등록(){
        //given
        String author = "저자";
        String title = "책제목";
        String publisher = "출판사";
        String publicationDate = "20121013";
        String isbn = "11111";
        String thumbnail = "썸네일1";
        Book book = bookService.registerNewBook(author, title, publisher, publicationDate, isbn, thumbnail);

        //when
        Optional<Book> findBook = bookRepository.findByTitle(title);

        //then
        assertThat(book).isEqualTo(findBook.get());
    }

    @Test
    public void 없는_책_검색_예외발생(){
        //given,when,then
        assertThrows(BookTitleNotFoundException.class, ()->{
            bookService.findBookByTitleOrThrow("제목");
        });
    }

}