package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.OrderCreationRequest;
import com.devteria.identityservice.dto.request.OrderUpdateStatusRequest;
import com.devteria.identityservice.dto.response.OrderHistoryResponse;
import com.devteria.identityservice.dto.response.OrderResponse;
import com.devteria.identityservice.entity.*;
import com.devteria.identityservice.enums.OrderStatus;
import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import com.devteria.identityservice.mapper.OrderMapper;
import com.devteria.identityservice.repository.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    OrderDetailsRepository orderDetailsRepository;
    UserRepository userRepository;
    ShoppingCartRepository shoppingCartRepository;
    ShoppingCartItemRepository shoppingCartItemRepository;
    ProductRepository productRepository;
    AddressRepository addressRepository;
    OrderMapper orderMapper;


    //  /api.myservice.com/v1/user/shopping-cart/{shoppingCartID}/checkout
    //  method=POST
    public OrderResponse getOrder(Long shoppingCartId, OrderCreationRequest request){
        ShoppingCartEntity shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(
                () -> new AppException(ErrorCode.SHOPPING_CART_NOT_EXISTED));

        //check if the user is the owner of the shopping cart
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!shoppingCart.getUserId().equals(user.getUserId())) {
            throw new AppException(ErrorCode.USER_NOT_OWNER_OF_SHOPPING_CART);
        }
        //get all items in the shopping cart and create an order details for each item
        List<ShoppingCartItemEntity> shoppingCartItems = shoppingCartItemRepository.findAllByShoppingCartId(shoppingCartId);

        //get product price and name for each item
//        BigDecimal totalPrice = BigDecimal.ZERO;
//        for (ShoppingCartItemEntity item : shoppingCartItems) {
//            OrderDetailsEntity orderDetails = new OrderDetailsEntity();
//            orderDetails.setProductId(item.getProductId());
//            orderDetails.setName(productRepository.findById(item.getProductId()).get().getProductName());
//            orderDetails.setUnitPrice(productRepository.findById(item.getProductId()).get().getUnitPrice());
//            orderDetails.setOrderQuantity(item.getOrderQuantity());
//            orderDetailsRepository.save(orderDetails);
//            BigDecimal itemTotalPrice = orderDetails.getUnitPrice().multiply(BigDecimal.valueOf(orderDetails.getOrderQuantity()));
//            totalPrice = totalPrice.add(itemTotalPrice);
//        }
        List<OrderDetailsEntity> orderDetailsList = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ShoppingCartItemEntity item : shoppingCartItems) {
            OrderDetailsEntity orderDetails = new OrderDetailsEntity();
            orderDetails.setProductId(item.getProductId());
            orderDetails.setName(productRepository.findById(item.getProductId()).get().getProductName());
            orderDetails.setUnitPrice(productRepository.findById(item.getProductId()).get().getUnitPrice());
            orderDetails.setOrderQuantity(item.getOrderQuantity());
            orderDetailsList.add(orderDetails);
            BigDecimal itemTotalPrice = orderDetails.getUnitPrice().multiply(BigDecimal.valueOf(orderDetails.getOrderQuantity()));
            totalPrice = totalPrice.add(itemTotalPrice);
        }

        long addressId = request.getAddressId();
        AddressEntity address = addressRepository.findById(addressId).orElseThrow(
                () -> new AppException(ErrorCode.ADDRESS_NOT_EXISTED));
        Date currentDate = new Date();



        OrdersEntity order = OrdersEntity.builder()
                .serialNumber(UUID.randomUUID().toString())
                .userId(user.getUserId())
                .totalPrice(totalPrice)
                .status(OrderStatus.WAITING)
                .note("Order created")
                .receiveName(address.getReceiveName())
                .receiveAddress(address.getFullAddress())
                .receivePhone(address.getPhone())
                .createdAt(new Date())
                .receivedAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3)))  //plus 3 days
                .build();

        orderRepository.save(order);
        for (OrderDetailsEntity orderDetails : orderDetailsList) {
            orderDetails.setOrderId(order.getOrderId()); // Thiết lập liên kết với đơn hàng mới tạo
            orderDetailsRepository.save(orderDetails);
        }

        return orderMapper.toOrderResponse(order);

    }

    public List<OrderHistoryResponse> getHistoryOrders() {
        //get id user from authentication
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        long id = user.getUserId();
        List<OrdersEntity> orders = orderRepository.findByUserId(id);
        return orders
                .stream()
                .map(orderMapper::toOrderHistoryResponse)
                .toList();
    }

    public List<OrderHistoryResponse> getHistoryOrdersByKey(String key) {
        //get id user from authentication
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        long id = user.getUserId();
        Specification<OrdersEntity> specification =findByStatus(key); ;
        return orderRepository.findAll(specification)
                .stream()
                .map(orderMapper::toOrderHistoryResponse)
                .toList();
    }

    public List<OrdersEntity> findByKey(String key) {
        return orderRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (key != null) {

                predicates.add(criteriaBuilder.like(root.get("serialNumber"), "%"+key+"%"));
                predicates.add(criteriaBuilder.equal(root.get("status"), OrderStatus.valueOf(key)));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        });
    }
    public Specification<OrdersEntity> findBySerialNumber(String key) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("serialNumber"), key);
    }
    public Specification<OrdersEntity> findByStatus(String key) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), OrderStatus.valueOf(key));
    }
    public  Specification<OrdersEntity> ordersBetween(Date from, Date to) {
        return (Root<OrdersEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate fromDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), from);
            Predicate toDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), to);
            return criteriaBuilder.and(fromDatePredicate, toDatePredicate);
        };
    }
    public static Specification<OrdersEntity> ordersWithStatus(OrderStatus status) {
        return (Root<OrdersEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }
    public   BigDecimal calculateRevenue(Date from, Date to) {
        Specification<OrdersEntity> spec = Specification
                .where(ordersBetween(from, to))
                .and(ordersWithStatus(OrderStatus.SUCCESS));

        return orderRepository.findAll(spec)
                .stream()
                .map(OrdersEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<OrdersEntity> getHistoryOrdersBySerialNumber(String serialNumber) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        long id = user.getUserId();
        Specification<OrdersEntity> specification = findBySerialNumber(serialNumber);
        return orderRepository.findAll(specification);
    }

    public String cancelOrder(Long orderId) {
        OrdersEntity order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        if (order.getStatus() != OrderStatus.WAITING) {
            throw new AppException(ErrorCode.ORDER_CANNOT_BE_CANCELED);
        }
        order.setStatus(OrderStatus.CANCEL);
        orderRepository.save(order);
        return "Order has been canceled";
    }

    public List<OrdersEntity> getOrders() {
        return orderRepository.findAll();
    }

    public List<OrdersEntity> getOrdersByStatus(String key) {
        Specification<OrdersEntity> specification =findByStatus(key);
        return orderRepository.findAll(specification);

    }

    public OrdersEntity getOrderById(Long orderID) {
        return orderRepository.findById(orderID).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
    }

    public OrdersEntity updateOrderStatus(Long orderID, OrderUpdateStatusRequest status) {
        OrdersEntity order = orderRepository.findById(orderID).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        String sta = status.getStatus();
       if(isValidOrderStatus(sta))
       {
           order.setStatus(OrderStatus.valueOf(status.getStatus()));
           orderRepository.save(order);
           return order;
       }else{
           throw new AppException(ErrorCode.INVALID_ORDER_STATUS);}
    }
    public  boolean isValidOrderStatus(String input) {
        try {
            OrderStatus.valueOf(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
