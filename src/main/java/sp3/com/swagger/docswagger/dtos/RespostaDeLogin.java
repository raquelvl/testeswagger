package sp3.com.swagger.docswagger.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RespostaDeLogin {
    private String token;

    public RespostaDeLogin(String token) {
        this.token = token;
    }
}
