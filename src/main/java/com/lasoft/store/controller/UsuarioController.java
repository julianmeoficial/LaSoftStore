package com.lasoft.store.controller;

import com.lasoft.store.dto.UsuarioDTO;
import com.lasoft.store.model.Usuario;
import com.lasoft.store.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Obtenemos todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    //Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> createUsuario(@PathVariable long id) {
        return ResponseEntity.ok(usuarioService.getUsuario(id));
    }

    //Crear nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> createUsuario(@Valid @RequestBody Usuario usuario) {
        UsuarioDTO nuevoUsuario = usuarioService.createUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    //Actualizar usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(
            @PathVariable Long id,
            @Valid @RequestBody Usuario usuario){

        return ResponseEntity.ok(usuarioService.updateUsuario(id, usuario));
    }

    //Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    //Agregamos endpoints adicionales específicos (con posibilidad de actualización)

    //Obtener vendedores
    @GetMapping("/vendedores")
    public ResponseEntity<List<UsuarioDTO>> getAllVendedores() {
        return ResponseEntity.ok(usuarioService.getAllVendedoresByTipo(Usuario.TipoUsuario.VENDEDOR));
    }
}
