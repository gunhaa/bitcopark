package bitcopark.library.dto;

import bitcopark.library.domain.board.Board;
import bitcopark.library.domain.board.Category;
import bitcopark.library.domain.member.Member;
import lombok.Getter;

@Getter
public class AddBoardRequest {

    private String title;
    private String content;

    private boolean isSecret;

    public Board toEntity(Member member, Category category) {
        Board board = Board.builder()
                .title(title)
                .content(content)
                .isSecret(isSecret)
                .member(member)
                .category(category)
                .build();
        return board;
    }
}
