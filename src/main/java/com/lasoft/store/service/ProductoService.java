package com.lasoft.store.service;

import com.lasoft.store.dto.ProductoDTO;
import com.lasoft.store.exception.ResourceNotFoundException;
import com.lasoft.store.model.Categoria;
import com.lasoft.store.model.Producto;
import com.lasoft.store.model.Usuario;
import com.lasoft.store.repository.CategoriaRepository;
import com.lasoft.store.repository.ProductoRepository;
import com.lasoft.store.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public Page<ProductoDTO> getAllProductos(Pageable pageable) {
        log.info("Obteniendo productos paginados");
        return productoRepository.findAll(pageable)
                .map(ProductoDTO::new);
    }

    public ProductoDTO getProductoById(Long id) {
        log.info("Buscando producto con ID: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return new ProductoDTO(producto);
    }

    public Page<ProductoDTO> getProductosByVendedor(Long vendedorId, Pageable pageable) {
        log.info("Buscando productos del vendedor con ID: {}", vendedorId);
        Usuario vendedor = usuarioRepository.findById(vendedorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado con ID: " + vendedorId));

        return productoRepository.findByVendedor(vendedor, pageable)
                .map(ProductoDTO::new);
    }

    public Page<ProductoDTO> getProductosByCategoria(Long categoriaId, Pageable pageable) {
        log.info("Buscando productos de la categoría con ID: {}", categoriaId);
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + categoriaId));

        return productoRepository.findByCategoria(categoria, pageable)
                .map(ProductoDTO::new);
    }

    public Page<ProductoDTO> searchProductos(String keyword, Pageable pageable) {
        log.info("Buscando productos que contengan: {}", keyword);
        return productoRepository.findByNombreContainingIgnoreCase(keyword, pageable)
                .map(ProductoDTO::new);
    }

    public ProductoDTO createProducto(Producto producto, Long vendedorId, Long categoriaId) {
        log.info("Creando nuevo producto para vendedor ID: {}", vendedorId);

        Usuario vendedor = usuarioRepository.findById(vendedorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado con ID: " + vendedorId));

        if (vendedor.getTipo() != Usuario.TipoUsuario.VENDEDOR) {
            throw new IllegalArgumentException("El usuario no es un vendedor");
        }

        producto.setVendedor(vendedor);

        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + categoriaId));
            producto.setCategoria(categoria);
        }

        Producto savedProducto = productoRepository.save(producto);
        return new ProductoDTO(savedProducto);
    }

    public ProductoDTO updateProducto(Long id, Producto productoDetails, Long categoriaId) {
        log.info("Actualizando producto con ID: {}", id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // Actualizar solo los campos permitidos
        producto.setNombre(productoDetails.getNombre());
        producto.setDescripcion(productoDetails.getDescripcion());
        producto.setPrecio(productoDetails.getPrecio());
        producto.setStock(productoDetails.getStock());
        producto.setTipo(productoDetails.getTipo());
        producto.setEstado(productoDetails.getEstado());

        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + categoriaId));
        }

        Producto updatedProducto = productoRepository.save(producto);
        return new ProductoDTO(updatedProducto);
    }

    public void deleteProducto(Long id) {
        log.info("Eliminando producto con ID: {}", id);

        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }

        productoRepository.deleteById(id);
    }
}