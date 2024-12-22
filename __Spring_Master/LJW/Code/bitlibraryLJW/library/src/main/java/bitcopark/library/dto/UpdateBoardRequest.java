package bitcopark.library.dto;

import lombok.Getter;

@Getter
public class UpdateBoardRequest {

    private String title;
    private String content;
    private boolean isSecret;
}
