package com.gustavonascimento.stock.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "TB_RAW_MATERIAL")
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(
            name = "product_seq",
            sequenceName = "SEQ_PRODUCT",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "rm_code", nullable = false, length = 50, unique = true)
    private String code;

    @Column(name = "rm_name", nullable = false, length = 255)
    private String name;

    @Column(name = "stock_quantity", nullable = false)
    private Double stockQuantity;

    public RawMaterial() {
        /* Empty Constructor */
    }

    public RawMaterial(String code, String name, Double stockQuantity) {
        this.code = code;
        this.name = name;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Double stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        RawMaterial that = (RawMaterial) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}
