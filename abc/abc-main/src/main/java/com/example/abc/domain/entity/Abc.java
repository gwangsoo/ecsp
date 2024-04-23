package com.example.abc.domain.entity;

import com.example.abc.domain.dto.AbcDTO;
import com.example.ecsp.common.jpa.AbstractAuditingEntity;
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
@Table(name = "tb_abc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Abc extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", length = 16, nullable = false)
//    @Comment("id")
    private String id;

    @Column(name = "data", length = 256)
//    @Comment("데이타")
    private String data;

    @Column(name = "size")
//    @Comment("사이즈")
    private Long size;

    @Column(name = "status", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
//    @Comment("상태 (OPEN/CLOSE)")
//    @ColumnDefault("OPEN")
    private AbcDTO.AbcStatus status;
}
