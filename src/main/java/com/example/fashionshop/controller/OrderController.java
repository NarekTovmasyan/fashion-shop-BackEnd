package com.example.fashionshop.controller;

import com.example.fashionshop.model.Order;
import com.example.fashionshop.model.commons.enums.OrderStatus;
import com.example.fashionshop.model.dto.requestDto.OrderUpdateReqDto;
import com.example.fashionshop.service.OrderService;
import com.example.fashionshop.validation.OrderValidator;
import com.example.fashionshop.validation.UserValidator;
import com.example.fashionshop.validation.dto.OrderDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/***
 * TODO need to order validation for orderDetails
 */

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/user-order")
    ResponseEntity<List<Order>> getOrdersByUserId(@RequestHeader("user_id") String userId) {

        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, plz SignUp at first"
            );
        }
        return ResponseEntity.ok(orderService.getAllById(userId));

    }

    @GetMapping("/order-status")
    ResponseEntity<List<Order>> getOrderByStatus(@RequestHeader("user_id") String userId, @RequestHeader("status") OrderStatus orderStatus){
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, plz SignUp at first"
            );
        }

        return ResponseEntity.ok(orderService.getOrderByStatus(userId, orderStatus));
    }

    @PostMapping
    ResponseEntity<Order> create(@RequestBody Order order) {
        if (!OrderValidator.validateOrder(order)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid order Structure for accepting Order"
            );
        }

        return ResponseEntity.ok(orderService.create(order));

    }


    @PutMapping("/change-status/{order_id}/{status}")
    ResponseEntity<Void> changeStatus(@RequestHeader("user_id") String userId,
                                      @PathVariable("order_id") Long orderId,
                                      @PathVariable("status") OrderStatus orderStatus){
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, plz SignUp at first"
            );
        }
        orderService.changeStatus(orderId, orderStatus);
        return ResponseEntity.ok().build();
    }


}