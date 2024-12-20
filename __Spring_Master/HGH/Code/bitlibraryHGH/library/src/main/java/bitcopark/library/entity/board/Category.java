package bitcopark.library.entity.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @GeneratedValue
    @Id
    @Column(name = "category_id")
    private Long id;
    private String categoryName;

    @OneToOne(mappedBy = "category", fetch = FetchType.LAZY)
    private Board board;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(categoryName, category.categoryName) && Objects.equals(board, category.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName, board);
    }
}
