package com.lasoft.store.controller;

import com.lasoft.store.dto.ProductoDTO;
import com.lasoft.store.model.Producto;
import com.lasoft.store.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    //Obtenemos todos los productos
    @GetMapping
    public ResponseEntity<Page<ProductoDTO>> getAllProductos(
            @RequestParam(defaultValue = "8") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productoService.getAllProductos(pageable));
    }

    //Obtenemos producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    //Crear nuevo producto
    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(
            @Valid @RequestBody Producto producto,
            @RequestParam Long vendedorId,
            @RequestParam(required = false) Long categoriaId) {

        ProductoDTO nuevoProducto = productoService.createProducto(producto, vendedorId, categoriaId);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    //Actualizar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto,
            @RequestParam(required = false) Long categoriaId) {

        return ResponseEntity.ok(productoService.updateProducto(id, producto, categoriaId));
    }

    //Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

    //Búsqueda de productos por palabra clave
    @GetMapping("/search")
    public ResponseEntity<Page<ProductoDTO>> searchProductos(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "8") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productoService.searchProductos(keyword, pageable));
    }

    //Obtener productos por vendedor
    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<Page<ProductoDTO>> getProductosByVendedor(
            @PathVariable Long vendedorId,
            @RequestParam(defaultValue = "8") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return
                ResponseEntity.ok(productoService.getProductosByVendedor(vendedorId, pageable));
    }

    //Obtener productos por categoría
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<Page<ProductoDTO>> getProductosByCategoria(
            @PathVariable Long categoriaId,
            @RequestParam(defaultValue = "8") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return
                ResponseEntity.ok(productoService.getProductosByCategoria(categoriaId, pageable));
    }
}
