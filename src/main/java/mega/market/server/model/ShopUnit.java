package mega.market.server.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

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
public class ShopUnit {

    @NotNull
    @Id
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant date;

    private UUID parentId;

    @NotNull
    private ShopUnitType type;

    private Long price;

    @Valid
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentId", cascade = CascadeType.ALL, orphanRemoval = true)
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
