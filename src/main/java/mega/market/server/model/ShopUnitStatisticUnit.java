package mega.market.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * ShopUnitStatisticUnit
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-22T11:11:10.456Z[GMT]")


public class ShopUnitStatisticUnit   {
  @JsonProperty("id")
  private UUID id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("parentId")
  private UUID parentId = null;

  @JsonProperty("type")
  private ShopUnitType type = null;

  @JsonProperty("price")
  private Long price = null;

  @JsonProperty("date")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime date;

  public ShopUnitStatisticUnit id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Уникальный идентфикатор
   * @return id
   **/
  @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66a333", required = true, description = "Уникальный идентфикатор")
      @NotNull

    @Valid
    public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public ShopUnitStatisticUnit name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Имя элемента
   * @return name
   **/
  @Schema(required = true, description = "Имя элемента")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ShopUnitStatisticUnit parentId(UUID parentId) {
    this.parentId = parentId;
    return this;
  }

  /**
   * UUID родительской категории
   * @return parentId
   **/
  @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66a333", description = "UUID родительской категории")
  
    @Valid
    public UUID getParentId() {
    return parentId;
  }

  public void setParentId(UUID parentId) {
    this.parentId = parentId;
  }

  public ShopUnitStatisticUnit type(ShopUnitType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ShopUnitType getType() {
    return type;
  }

  public void setType(ShopUnitType type) {
    this.type = type;
  }

  public ShopUnitStatisticUnit price(Long price) {
    this.price = price;
    return this;
  }

  /**
   * Целое число, для категории - это средняя цена всех дочерних товаров(включая товары подкатегорий). Если цена является не целым числом, округляется в меньшую сторону до целого числа. Если категория не содержит товаров цена равна null.
   * @return price
   **/
  @Schema(description = "Целое число, для категории - это средняя цена всех дочерних товаров(включая товары подкатегорий). Если цена является не целым числом, округляется в меньшую сторону до целого числа. Если категория не содержит товаров цена равна null.")
  
    public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public ShopUnitStatisticUnit date(LocalDateTime date) {
    this.date = date;
    return this;
  }

  /**
   * Время последнего обновления элемента.
   * @return date
   **/
  @Schema(required = true, description = "Время последнего обновления элемента.")
      @NotNull

    @Valid
    public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShopUnitStatisticUnit shopUnitStatisticUnit = (ShopUnitStatisticUnit) o;
    return Objects.equals(this.id, shopUnitStatisticUnit.id) &&
        Objects.equals(this.name, shopUnitStatisticUnit.name) &&
        Objects.equals(this.parentId, shopUnitStatisticUnit.parentId) &&
        Objects.equals(this.type, shopUnitStatisticUnit.type) &&
        Objects.equals(this.price, shopUnitStatisticUnit.price) &&
        Objects.equals(this.date, shopUnitStatisticUnit.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, parentId, type, price, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShopUnitStatisticUnit {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
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
