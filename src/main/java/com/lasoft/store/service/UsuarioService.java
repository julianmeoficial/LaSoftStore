package com.lasoft.store.service;

import com.lasoft.store.dto.UsuarioDTO;
import com.lasoft.store.exception.ResourceNotFoundException;
import com.lasoft.store.model.Usuario;
import com.lasoft.store.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> getAllUsuarios() {
        log.info("Obteniendo todos los usuarios");
        return usuarioRepository.findAll().stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }

    public UsuarioDTO getUsuario(Long id) {
        log.info("Buscando usuarios con ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return new UsuarioDTO(usuario);
    }

    public UsuarioDTO createUsuario(Usuario usuario) {
        log.info("Creando nuevo usuario con email: {}", usuario.getEmail());

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya se encuentra registrado");
        }

        //Proceso para encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return new UsuarioDTO(savedUsuario);
    }

    public UsuarioDTO updateUsuario(Long id, Usuario usuarioDetails) {
        log.info("Actualizando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setNombre(usuarioDetails.getNombre());

        //Por si cambia de email, verificar que no esté en uso
        if (!usuario.getEmail().equals(usuarioDetails.getEmail())) {
            if (usuarioRepository.existsByEmail(usuarioDetails.getEmail())){
                throw new IllegalArgumentException("El email ya se encuentra registrado");
            }
            usuario.setEmail(usuarioDetails.getEmail());
        }

        //Si se proporciona una nueva contraseña, encriptarla
        if (usuarioDetails.getPassword() != null && !usuarioDetails.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioDetails.getPassword()));
        }

        usuario.setTipo(usuarioDetails.getTipo());

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return new UsuarioDTO(updatedUsuario);
    }

    public void deleteUsuario(Long id) {
        log.info("Eliminando usuario con ID: {}", id);

        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public List<UsuarioDTO> getAllVendedoresByTipo(Usuario.TipoUsuario tipo){
        log.info("Obteniendo usuarios por tipo: {}", tipo);
        return usuarioRepository.findByTipo(tipo).stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }
}
