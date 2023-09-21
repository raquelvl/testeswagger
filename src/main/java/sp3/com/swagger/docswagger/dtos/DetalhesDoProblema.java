package sp3.com.swagger.docswagger.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Builder
@Getter
@Setter
public class DetalhesDoProblema {
    private int status;
    private String type;
    private String title;
    private String detail;

    public DetalhesDoProblema(int status, String type, String title, String detail) {
        this.status = status;
        this.type = type;
        this.title = title;
        this.detail = detail;
    }
}
