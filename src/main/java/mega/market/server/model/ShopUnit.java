package mega.market.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * ShopUnit
 */
@Getter
@Setter
@Validated
@Entity
//@Table(name = "shop_unit")
public class ShopUnit {
    /**
     * Уникальный идентфикатор
     *
     * @return id
     **/
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66a333", required = true, description = "Уникальный идентфикатор")
    @Valid
    @NotNull
    @Id
    private UUID id;

    /**
     * Имя категории
     *
     * @return name
     **/
    @Schema(required = true, description = "Имя категории")
    @NotNull
    private String name = "";

    /**
     * Время последнего обновления элемента.
     *
     * @return date
     **/
//    @Schema(example = "2022-05-28T21:12:01.000Z", required = true, description = "Время последнего обновления элемента.")
    @NotNull
    @Valid
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime date;

    /**
     * UUID родительской категории
     *
     * @return parentId
     **/
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66a333", description = "UUID родительской категории")
    @Valid
    private UUID parentId;

    /**
     * Get type
     *
     * @return type
     **/
    @Schema(required = true, description = "")
    @NotNull
    @Valid
    private ShopUnitType type = null;

    /**
     * Целое число, для категории - это средняя цена всех дочерних товаров(включая товары подкатегорий). Если цена является не целым числом, округляется в меньшую сторону до целого числа. Если категория не содержит товаров цена равна null.
     *
     * @return price
     **/
    @Schema(description = "Целое число, для категории - это средняя цена всех дочерних товаров(включая товары подкатегорий). Если цена является не целым числом, округляется в меньшую сторону до целого числа. Если категория не содержит товаров цена равна null.")
    private Long price = null;

    /**
     * Список всех дочерних товаров\\категорий. Для товаров поле равно null.
     *
     * @return children
     **/
    @Schema(description = "Список всех дочерних товаров\\категорий. Для товаров поле равно null.")
    @Valid
    @OneToMany(fetch = FetchType.LAZY)
    private Set<ShopUnit> children = null;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ShopUnit {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    date: ").append(toIndentedString(date)).append("\n");
        sb.append("    parent: ").append(toIndentedString(parentId)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("    children: ").append(toIndentedString(children)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
