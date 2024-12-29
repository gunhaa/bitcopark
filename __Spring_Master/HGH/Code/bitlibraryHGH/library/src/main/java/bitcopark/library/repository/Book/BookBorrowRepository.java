package bitcopark.library.repository.Book;

import bitcopark.library.entity.Book.BookBorrow;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookBorrowRepository extends JpaRepository<BookBorrow, Long> {

    List<BookBorrow> findByMember(Member member);

}
