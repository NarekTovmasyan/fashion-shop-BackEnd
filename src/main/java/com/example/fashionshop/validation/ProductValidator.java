package com.example.fashionshop.validation;

import com.example.fashionshop.model.Product;
import com.example.fashionshop.validation.commons.DescriptionValidator;
import com.example.fashionshop.validation.commons.ImageValidator;
import com.example.fashionshop.validation.commons.StockValidator;

public final class ProductValidator {
    public static boolean validateUpdateProduct(Product product, String userId) {
//        ImageValidator.checkDefaultImage(product);
        System.out.println(product.getName().length() == 0);
        System.out.println(product.getPrice() < 0);
        System.out.println(!StockValidator.validateStock(product.getStock()));
        System.out.println(!DescriptionValidator.validateDescription(product.getDescription()));
        System.out.println(!UserValidator.checkUserAuthorized(userId));
        if (product.getName().length() == 0 ||
                product.getPrice() < 0 ||
                !StockValidator.validateStock(product.getStock()) ||
                !DescriptionValidator.validateDescription(product.getDescription()) ||
                !UserValidator.checkUserAuthorized(userId)) {
            return false;
        }

        return true;
    }
    public static boolean validateCreateProduct(Product product, String userId){
        System.out.println(product.getStock());
        System.out.println(product.getDescription());
        return validateUpdateProduct(product, userId);
    }

    public static boolean validateDeleteProduct(String userId){
        return UserValidator.checkUserAuthorized(userId);
    }
}
