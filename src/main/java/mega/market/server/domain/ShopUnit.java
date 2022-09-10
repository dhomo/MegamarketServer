package mega.market.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import mega.market.server.DTO.ShopUnitType;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Audited
public class ShopUnit {

    @NotNull
    @Id
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private Instant date;

    @JsonIgnore
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private ShopUnit parent;

    @Column(name = "parent_id")
    private UUID parentId;

    @NotNull
    private ShopUnitType type;

    private Long price;

    @Valid
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<ShopUnit> children = new LinkedHashSet<>();

    public Set<ShopUnit> getChildren() {
        if (type == ShopUnitType.OFFER)
            return null;
        else
            return children;
    }

    public ShopUnit addChildrenItem(ShopUnit childrenItem) {
        if (this.children == null) {
            this.children = new LinkedHashSet<ShopUnit>();
        }
        this.children.add(childrenItem);
        childrenItem.setParent(this);
        return this;
    }

    public ShopUnit removeChildrenItem(ShopUnit childrenItem) {
        this.children.remove(childrenItem);
        childrenItem.setParent(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ShopUnit shopUnit = (ShopUnit) o;
        return id != null && Objects.equals(id, shopUnit.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
