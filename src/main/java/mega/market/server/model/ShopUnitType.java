package mega.market.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Тип элемента - категория или товар
 */
public enum ShopUnitType {
    OFFER("OFFER"),
    CATEGORY("CATEGORY");

    private String value;

    ShopUnitType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ShopUnitType fromValue(String text) {
        for (ShopUnitType b : ShopUnitType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
