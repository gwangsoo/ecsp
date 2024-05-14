package com.example.orders.domain.entity;

import com.example.ecsp.common.jpa.AbstractAuditingEntity;
import com.example.orders.domain.dto.OrdersDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.TenantId;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_orders")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Orders extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", length = 16, nullable = false)
//    @Comment("id")
    private String id;

    @Column(name = "product_id", length = 256)
//    @Comment("데이타")
    private String productId;

    @Column(name = "product_name", length = 256)
//    @Comment("데이타")
    private String productName;

    @Column(name = "size")
//    @Comment("사이즈")
    private Long size;

    @Column(name = "status", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
//    @Comment("상태 (OPEN/CLOSE)")
//    @ColumnDefault("OPEN")
    private OrdersDTO.OrdersStatus status;

    @TenantId
    @Column(name = "tenant_id", length = 50, updatable = false)
    private String tenantId;
}
