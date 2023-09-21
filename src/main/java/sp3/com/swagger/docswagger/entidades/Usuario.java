package sp3.com.swagger.docswagger.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
public class Usuario {
        @Id
        private String email;

        private String nomeCompleto;

        private String senha;

        private Papel papel = Papel.REGULAR;

        public Usuario() {
            super();
            // TODO Auto-generated constructor stub
        }

        public Usuario(String email, String nomeCompleto, String senha, Papel papel) {
            super();
            this.email = email;
            this.nomeCompleto = nomeCompleto;
            this.senha = senha;
            this.papel = papel;
        }

        @Override
        public String toString() {
            return "Usuario [email=" + email + ", nomeCompleto=" + nomeCompleto + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Usuario usuario = (Usuario) o;

            return Objects.equals(email, usuario.email);
        }

        @Override
        public int hashCode() {
            return email != null ? email.hashCode() : 0;
        }
    }
