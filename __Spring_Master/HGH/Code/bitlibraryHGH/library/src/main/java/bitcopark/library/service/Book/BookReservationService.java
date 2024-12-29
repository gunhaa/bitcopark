package bitcopark.library.service.Book;

import bitcopark.library.entity.Book.Book;
import bitcopark.library.entity.Book.BookReservation;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.Book.BookReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookReservationService {

    private final BookReservationRepository bookReservationRepository;

    @Transactional
    public BookReservation registerBookReservation(Member member , Book book){
        BookReservation bookReservation = BookReservation.builder()
                .member(member)
                .book(book)
                .build();
        return bookReservationRepository.save(bookReservation);
    }


}
