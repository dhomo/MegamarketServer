package mega.market.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ShopUnitImportRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-22T11:11:10.456Z[GMT]")


public class ShopUnitImportRequest   {
  @JsonProperty("items")
  @Valid
  private List<ShopUnitImport> items = null;

  @JsonProperty("updateDate")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime updateDate = null;

  public ShopUnitImportRequest items(List<ShopUnitImport> items) {
    this.items = items;
    return this;
  }

  public ShopUnitImportRequest addItemsItem(ShopUnitImport itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<ShopUnitImport>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Импортируемые элементы
   * @return items
   **/
  @Schema(description = "Импортируемые элементы")
      @Valid
    public List<ShopUnitImport> getItems() {
    return items;
  }

  public void setItems(List<ShopUnitImport> items) {
    this.items = items;
  }

  public ShopUnitImportRequest updateDate(LocalDateTime updateDate) {
    this.updateDate = updateDate;
    return this;
  }

  /**
   * Время обновления добавляемых товаров/категорий.
   * @return updateDate
   **/
  @Schema(example = "2022-05-28T21:12:01Z", description = "Время обновления добавляемых товаров/категорий.")
  
    @Valid
    public LocalDateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(LocalDateTime updateDate) {
    this.updateDate = updateDate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShopUnitImportRequest shopUnitImportRequest = (ShopUnitImportRequest) o;
    return Objects.equals(this.items, shopUnitImportRequest.items) &&
        Objects.equals(this.updateDate, shopUnitImportRequest.updateDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, updateDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShopUnitImportRequest {\n");
    
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("    updateDate: ").append(toIndentedString(updateDate)).append("\n");
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
