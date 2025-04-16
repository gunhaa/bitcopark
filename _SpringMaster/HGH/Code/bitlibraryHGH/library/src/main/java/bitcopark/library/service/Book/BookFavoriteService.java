package bitcopark.library.service.Book;

import bitcopark.library.entity.Book.Book;
import bitcopark.library.entity.Book.BookFavorite;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.Book.BookFavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookFavoriteService {

    private final BookFavoriteRepository bookFavoriteRepository;

    @Transactional
    public BookFavorite addBookFavorite(Book book, Member member){
        BookFavorite bookFavorite = BookFavorite.builder()
                .book(book)
                .member(member)
                .build();
        return bookFavoriteRepository.save(bookFavorite);
    }

}
