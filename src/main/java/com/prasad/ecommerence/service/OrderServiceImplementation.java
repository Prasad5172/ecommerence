package com.prasad.ecommerence.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.prasad.ecommerence.exception.OrderException;
import com.prasad.ecommerence.model.Address;
import com.prasad.ecommerence.model.Cart;
import com.prasad.ecommerence.model.CartItem;
import com.prasad.ecommerence.model.OrderItem;
import com.prasad.ecommerence.model.Order;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.repository.AddressRepository;
import com.prasad.ecommerence.repository.OrderItemRepository;
import com.prasad.ecommerence.repository.OrderRepository;
import com.prasad.ecommerence.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddress().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem item : cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice() );
            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }

        Order createOrder = new Order();
        createOrder.setUser(user);
        createOrder.setOrderItems(orderItems);
        createOrder.setTotalPrice(cart.getTotalPrice());
        createOrder.setDiscount(cart.getDiscount());
        createOrder.setTotalItem(cart.getTotalItem());
        createOrder.setShippingAddress(shippingAddress);
        createOrder.setOrderDate(LocalDateTime.now());
        createOrder.setOrderStatus("PENDING");
        createOrder.getPaymentDetails().setStatus("PENDING");
        createOrder.setCreatedAt(LocalDateTime.now());

         Order savedOrder = orderRepository.save(createOrder);
         for(OrderItem item : orderItems){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
         }
        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> orders = orderRepository.findById(orderId);
        if (orders.isPresent()) {
            return orders.get();
        }
        throw new OrderException("Order not exists with id" + orderId);
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        return orderRepository.getAllUserOrders(userId);
    }

    @Override
    public Order placeOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return orderRepository.save(order);
    }

    @Override
    public Order confirmOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order cancleOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

}
