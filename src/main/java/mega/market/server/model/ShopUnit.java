package mega.market.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.*;

/**
 * ShopUnit
 */
@Data
@Validated
@Entity
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopUnit {
    @Valid
    @NotNull
    @Id
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @Valid
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant date;

    @Valid
    private UUID parentId;

    @NotNull
    @Valid
    private ShopUnitType type;

    private Long price;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShopUnit> children = new LinkedHashSet<>();

    public Set<ShopUnit> getChildren() {
        if (type == ShopUnitType.OFFER)
            return null;
        else
            return children;
    }

    public ShopUnit addChildrenItem(ShopUnit childrenItem) {
        if (this.children == null) {
            this.children = new HashSet<ShopUnit>();
        }
        this.children.add(childrenItem);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShopUnit shopUnit = (ShopUnit) o;
        return Objects.equals(this.id, shopUnit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
