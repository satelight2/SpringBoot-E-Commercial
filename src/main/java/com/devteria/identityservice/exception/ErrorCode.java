package com.devteria.identityservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),

    ROLE_NOT_EXISTED(1009, "ROLE NOT EXISTED", HttpStatus.BAD_REQUEST),
    SHOPPING_CART_NOT_EXISTED(1010, "Shopping cart not existed", HttpStatus.BAD_REQUEST),
    USER_NOT_OWNER_OF_SHOPPING_CART(1011, "user not owner of shopping cart", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED(1012, "Product not existed", HttpStatus.BAD_REQUEST),
    SHOPPING_CART_ITEM_NOT_EXISTED(1013, "Shopping cart item not existed", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_EXISTED(1014,"address not existed" ,HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(1015,"password not match" ,HttpStatus.BAD_REQUEST ),
    NEW_PASSWORD_SAME_OLD_PASS(1016,"new password same old password" ,HttpStatus.BAD_REQUEST ),
    NEW_PASSWORD_NOT_SAME_CONFIRM_PASSWORD(1017,"new password not same confirm password" ,HttpStatus.BAD_REQUEST ),
    WRONG_ENTER_KEY(1018,"wrong enter key" ,HttpStatus.BAD_REQUEST ),
    ORDER_NOT_EXISTED(1019,"order not existed" , HttpStatus.BAD_REQUEST),
    ORDER_CANNOT_BE_CANCELED(1020,"order cannot be canceled" , HttpStatus.BAD_REQUEST),
    WISH_LIST_NOT_EXISTED(1021,"wish list not existed" , HttpStatus.BAD_REQUEST),
    WISH_LIST_DETAILS_NOT_EXISTED(1022,"wish list details not existed" ,HttpStatus.BAD_REQUEST ),
    CATEGORY_NOT_EXISTED(1023,"category not existed" ,HttpStatus.BAD_REQUEST
            ),
    INVALID_ORDER_STATUS(1024,"invalid order status" ,HttpStatus.BAD_REQUEST );

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
