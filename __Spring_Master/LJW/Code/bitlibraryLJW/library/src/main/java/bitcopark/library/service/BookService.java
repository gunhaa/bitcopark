package bitcopark.library.service;

import bitcopark.library.domain.book.Book;
import bitcopark.library.domain.member.Member;
import bitcopark.library.domain.member.MemberRole;
import bitcopark.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book save(Book book, Member member) {
        validateAdmin(member);
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void validateAdmin(Member member) {
        if (member.getRole() != MemberRole.ADMIN) {
            throw new IllegalArgumentException("not admin");
        }
    }
}
