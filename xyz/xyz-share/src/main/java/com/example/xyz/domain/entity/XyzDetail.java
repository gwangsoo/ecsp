package com.example.xyz.domain.entity;

import com.example.common.jpa.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_xyz_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class XyzDetail extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", length = 16, nullable = false)
    @Comment("id")
    private String id;

    @Column(name = "attr_name", length = 32)
    @Comment("속성명")
    private String attrName;

    @Column(name = "attr_value", length = 256)
    @Comment("속성값")
    private String attrValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
            value = { "xyzDetails", "name", "age" },
            allowSetters = true
    )
    private Xyz xyz;
}
