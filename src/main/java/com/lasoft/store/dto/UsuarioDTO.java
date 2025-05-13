package com.lasoft.store.dto;

import com.lasoft.store.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private Usuario.TipoUsuario tipo;
    private LocalDateTime fechaRegistro;

    //Debo dejar este código aquí debajo, porque es el contructor para convertir de Entity a DTO
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.tipo = usuario.getTipo();
        this.fechaRegistro = usuario.getFechaRegistro();
    }
}
