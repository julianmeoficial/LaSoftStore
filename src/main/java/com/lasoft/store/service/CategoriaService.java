package com.lasoft.store.service;

import com.lasoft.store.dto.CategoriaDTO;
import com.lasoft.store.exception.ResourceNotFoundException;
import com.lasoft.store.model.Categoria;
import com.lasoft.store.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> getAllCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaDTO::new)
                .collect(Collectors.toList());
    }

    public CategoriaDTO createCategoria(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        if (dto.getCategoriaPadreId() != null) {
            Optional<Categoria> padre = categoriaRepository.findById(dto.getCategoriaPadreId());
            padre.ifPresent(categoria::setCategoriaPadre);
        }
        Categoria saved = categoriaRepository.save(categoria);
        return new CategoriaDTO(saved);
    }

    public CategoriaDTO getCategoriaById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return new CategoriaDTO(categoria);
    }

    public CategoriaDTO updateCategoria(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + id));

        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        if (categoriaDTO.getCategoriaPadreId() != null) {
            if (!categoriaDTO.getCategoriaPadreId().equals(id)) {

                Categoria categoriaPadre= categoriaRepository.findById(categoriaDTO.getCategoriaPadreId())
                        .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + categoriaDTO.getCategoriaPadreId()));
                categoria.setCategoriaPadre(categoriaPadre);
            } else {
                throw new IllegalArgumentException("Una categoría no puede ser su propia categoría padre");
            }
        } else {
            categoria.setCategoriaPadre(null);
        }

        Categoria updateCategoria = categoriaRepository.save(categoria);
        return new CategoriaDTO(updateCategoria);
    }

    public void deleteCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
