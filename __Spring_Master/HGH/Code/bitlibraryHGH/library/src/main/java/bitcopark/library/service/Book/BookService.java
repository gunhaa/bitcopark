package bitcopark.library.service.Book;

import bitcopark.library.entity.Book.Book;
import bitcopark.library.exception.BookTitleNotFoundException;
import bitcopark.library.repository.Book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book registerNewBook(String author, String title, String publisher, String publicationDate, String isbn, String thumbnail){

        Book book = Book.createBook(author, title, publisher, publicationDate, isbn, thumbnail);
        bookRepository.save(book);

        return book;
    }

    public Book findBookByTitleOrThrow(String title) {
        return bookRepository.findByTitle(title)
                // client exception 발생시 로그를 남겨, 그 파일을 남기는 방식을 찾을 것
                // 이건 찾아봐야할듯
                .orElseThrow(() -> new BookTitleNotFoundException("책 제목을 찾을 수 없습니다: " + title));
    }

}
