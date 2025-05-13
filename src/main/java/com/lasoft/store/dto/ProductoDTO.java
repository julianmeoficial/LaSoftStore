package com.lasoft.store.dto;

import com.lasoft.store.model.Producto;
import com.lasoft.store.model.Producto.EstadoProducto;
import com.lasoft.store.model.Producto.TipoProducto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer stock;
    private BigDecimal precio;
    private Long vendedorId;
    private String vendedorNombre;
    private Long categoriaId;
    private String categoriaNombre;
    private TipoProducto tipo;
    private EstadoProducto estado;
    private Long eventoId;
    private String eventoNombre;

    public ProductoDTO() {}

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.stock = producto.getStock();
        this.precio = producto.getPrecio();

        if (producto.getVendedor() != null) {
            this.vendedorId = producto.getVendedor().getId();
            this.vendedorNombre = producto.getVendedor().getNombre();
        }

        if (producto.getCategoria() != null) {
            this.categoriaId = producto.getCategoria().getId();
            this.categoriaNombre = producto.getCategoria().getNombre();
        }

        this.tipo = producto.getTipo();
        this.estado = producto.getEstado();

        if (producto.getEvento() != null) {
            this.eventoId = producto.getEvento().getId();
            this.eventoNombre = producto.getEvento().getNombre();
        }
    }
}

