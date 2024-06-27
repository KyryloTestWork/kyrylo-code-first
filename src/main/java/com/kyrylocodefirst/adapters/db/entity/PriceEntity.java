package com.kyrylocodefirst.adapters.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.proxy.HibernateProxy;

import com.kyrylocodefirst.adapters.db.entity.shared.AuditedEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "PRICES")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PriceEntity extends AuditedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_list")
    private Integer priceList;

    @NotNull
    @Column(name = "brand_id")
    private Integer brandId;

    @NotNull
    @Column(name = "start_data")
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "end_date")
    @Schema(example = "2021-01-31T23:59:59")
    private LocalDateTime endDate;

    @NotNull
    @Column(name = "product_id")
    private Integer productId;

    @NotNull
    @Column(name = "priority")
    private Integer priority;

    @NotNull
    @Column(name = "price")
    @Digits(fraction = 2, integer = 10)
    private BigDecimal price;

    @NotEmpty
    @Column(name = "currency")
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id", insertable = false, updatable = false,
        foreignKey = @ForeignKey(name = "fk_brand_id"))
    private final BrandEntity brandEntity = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false,
        foreignKey = @ForeignKey(name = "fk_product_id"))
    private final ProductEntity productEntity = null;

    @Override
    public final boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
        Class<?> thisEffectiveClass =
            this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass)
            return false;
        PriceEntity that = (PriceEntity) o;
        return getPriceList() != null && Objects.equals(getPriceList(), that.getPriceList());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
            : getClass().hashCode();
    }
}
