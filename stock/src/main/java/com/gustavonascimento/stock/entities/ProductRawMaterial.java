package com.gustavonascimento.stock.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_product_raw_materials")
public class ProductRawMaterial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raw_material_id", nullable = false)
    private RawMaterial rawMaterial;

    @Column(name = "required_quantity", nullable = false)
    private Double requiredQuantity;

    public ProductRawMaterial(){
        /* Empty Constructor */
    }

    public ProductRawMaterial(Long id, Product product, RawMaterial rawMaterial, Double requiredQuantity) {
        this.id = id;
        this.product = product;
        this.rawMaterial = rawMaterial;
        this.requiredQuantity = requiredQuantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        ProductRawMaterial that = (ProductRawMaterial) o;
        return Objects.equals(rawMaterial, that.rawMaterial) && Objects.equals(requiredQuantity, that.requiredQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawMaterial, requiredQuantity);
    }
}
