package bitcopark.library.service;

import bitcopark.library.entity.Book.Book;
import bitcopark.library.exception.BookTitleNotFoundException;
import bitcopark.library.repository.BookRepository;
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
                .orElseThrow(() -> new BookTitleNotFoundException("책 제목을 찾을 수 없습니다: " + title));
    }

}
