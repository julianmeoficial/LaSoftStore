package com.lasoft.store.repository;

import com.lasoft.store.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByCategoriaPadreIsNull();
    List<Categoria> findByCategoriaPadreId(Long categoriaPadreId);
}

