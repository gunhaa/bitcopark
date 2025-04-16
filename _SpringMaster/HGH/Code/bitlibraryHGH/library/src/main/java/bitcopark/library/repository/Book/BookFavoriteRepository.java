package bitcopark.library.repository.Book;

import bitcopark.library.entity.Book.BookFavorite;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookFavoriteRepository extends JpaRepository<BookFavorite, Long> {

    List<BookFavorite> findByMember(Member member);

}
