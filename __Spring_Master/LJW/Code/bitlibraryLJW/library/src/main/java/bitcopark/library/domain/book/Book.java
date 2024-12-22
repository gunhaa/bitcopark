package bitcopark.library.domain.book;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String title;
    private String author;
    private String publisher;
    private LocalDate publicationDate;
    private String isbn;
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private boolean hasSupple;

    @Builder
    public Book(String title, String author, String publisher, LocalDate publicationDate, String isbn, String thumbnail, BookStatus status, boolean hasSupple) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.thumbnail = thumbnail;
        this.status = status;
        this.hasSupple = hasSupple;
    }
}
