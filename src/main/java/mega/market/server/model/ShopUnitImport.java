package mega.market.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;

/**
 * ShopUnitImport
 */
@Validated
public class ShopUnitImport {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("name")
    @NotNull
    private String name = "";

    @JsonProperty("parentId")
    private UUID parentId = null;

    @JsonProperty("type")
    private ShopUnitType type = null;

    @JsonProperty("price")
    private Long price = null;

    public ShopUnitImport id(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Уникальный идентфикатор
     *
     * @return id
     **/
    @NotNull
    @Valid
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ShopUnitImport name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Имя элемента.
     *
     * @return name
     **/
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShopUnitImport parentId(UUID parentId) {
        this.parentId = parentId;
        return this;
    }

    /**
     * UUID родительской категории
     *
     * @return parentId
     **/
    @Valid
    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public ShopUnitImport type(ShopUnitType type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     *
     * @return type
     **/
    @NotNull

    @Valid
    public ShopUnitType getType() {
        return type;
    }

    public void setType(ShopUnitType type) {
        this.type = type;
    }

    public ShopUnitImport price(Long price) {
        this.price = price;
        return this;
    }

    /**
     * Целое число, для категорий поле должно содержать null.
     *
     * @return price
     **/
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShopUnitImport shopUnitImport = (ShopUnitImport) o;
        return Objects.equals(this.id, shopUnitImport.id) &&
                Objects.equals(this.name, shopUnitImport.name) &&
                Objects.equals(this.parentId, shopUnitImport.parentId) &&
                Objects.equals(this.type, shopUnitImport.type) &&
                Objects.equals(this.price, shopUnitImport.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentId, type, price);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ShopUnitImport {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
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
