package bitcopark.library.entity.Board;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BoardImg {

    @Id
    @GeneratedValue
    @Column(name = "boardImg_id")
    private Long id;

    private String originalImg;

    @Builder.Default
    private String renameImg = createRenameImg();

    private int orderImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private static String createRenameImg(){
        // need rename logic
        return "rename";
    }



}
