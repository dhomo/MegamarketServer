package mega.market.server.validators;

import mega.market.server.model.ShopUnitImport;
import mega.market.server.model.ShopUnitType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ShopUnitImportValidator implements ConstraintValidator<ValidShopUnitImport, ShopUnitImport> {

    @Override
    public void initialize(ValidShopUnitImport constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ShopUnitImport p, ConstraintValidatorContext context) {
        // у категорий поле price должно содержать null
        if (p.getType() == ShopUnitType.CATEGORY && p.getPrice() != null) {
            return false;
//      цена товара не может быть null и должна быть больше либо равна нулю.
        } else if (p.getType() == ShopUnitType.OFFER && (p.getPrice() == null || p.getPrice() < 0)) {
            return false;
        }
        return true;
    }
}
