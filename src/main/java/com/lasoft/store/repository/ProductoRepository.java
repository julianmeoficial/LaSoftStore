package com.lasoft.store.repository;

import com.lasoft.store.model.Categoria;
import com.lasoft.store.model.Producto;
import com.lasoft.store.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Page<Producto> findByVendedor(Usuario vendedor, Pageable pageable);
    Page<Producto> findByCategoria(Categoria categoria, Pageable pageable);
    Page<Producto> findByNombreContainingIgnoreCase(String keyword, Pageable pageable);
}
