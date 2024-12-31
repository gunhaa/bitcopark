package bitcopark.library.entity.Book;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;
    private String author;
    private String title;
    private String publisher;
    private String publicationDate;
    private String isbn;
    private String thumbnail;

    // A(available), B(borrowed), R(reserved)
    @Enumerated(EnumType.STRING)
    private BookState state;

    @Enumerated(EnumType.STRING)
    private BookSupple supple;

    @OneToMany(mappedBy = "book")
    private List<BookFavorite> bookFavoriteList = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookBorrow> bookBorrowList = new ArrayList<>();

    public static Book createBook(String author, String title, String publisher, String publicationDate, String isbn, String thumbnail){
        Book book = new Book();
        book.author = author;
        book.title = title;
        book.publisher = publisher;
        book.publicationDate = publicationDate;
        book.isbn = isbn;
        book.thumbnail = thumbnail;
        book.state = BookState.A;
        book.supple = BookSupple.N;
        return book;
    }

    public void changeBookState(BookState bookState){
        this.state = bookState;
    }

    public void changeBookSupple(BookSupple bookSupple){
        this.supple = bookSupple;
    }



}
