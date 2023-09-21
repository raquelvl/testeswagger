package sp3.com.swagger.docswagger.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class LoginDeUsuarioDTO {
    private String email;
    private String senha;
}
