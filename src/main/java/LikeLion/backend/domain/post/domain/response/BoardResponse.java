package LikeLion.backend.domain.post.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {
    private Integer id;
    private String title;
    private String content;
    private String writer;
    private Integer viewCnt;
    private Integer likeCnt;
}
