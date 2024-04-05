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
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_xyz")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Xyz extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum XyzStatus {
        STANDBY,
        ACTIVE,
        DEACTIVE
    }

    @Id
    @Column(name = "id", length = 16, nullable = false)
    @Comment("id")
    private String id;

    @Column(name = "name", length = 256)
    @Comment("이름")
    private String name;

    @Column(name = "age")
    @Comment("나이")
    private Long age;

    @Column(name = "status", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("상태 (OPEN/CLOSE)")
    private XyzStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "xyz" }, allowSetters = true)
    private Set<XyzDetail> xyzDetails;
}
