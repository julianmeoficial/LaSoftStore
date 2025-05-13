package com.lasoft.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long CategoriaPadreId;

    //Contructor para mapear identidad
    public CategoriaDTO(com.lasoft.store.model.Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
        this.descripcion = categoria.getDescripcion();
        this.CategoriaPadreId = (categoria.getCategoriaPadre() !=null)
                ? (categoria.getCategoriaPadre().getId()) : null;
    }
}
