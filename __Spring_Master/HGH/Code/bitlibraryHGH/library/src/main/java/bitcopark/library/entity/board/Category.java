package bitcopark.library.entity.board;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @GeneratedValue
    @Id
    @Column(name = "category_id")
    private Long id;
    private String categoryName;


    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Board> boardList = new ArrayList<>();


}
