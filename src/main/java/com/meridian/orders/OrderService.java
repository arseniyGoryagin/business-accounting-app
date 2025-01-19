package com.meridian.orders;


import com.meridian.clients.ClientRepository;
import com.meridian.clients.domain.Client;
import com.meridian.clients.exceptions.NoSuchClientException;
import com.meridian.orders.domain.Order;
import com.meridian.orders.domain.OrderRequest;
import com.meridian.orders.exceptions.NoSuchOrderException;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;


    public Order addOrder(OrderRequest orderRequest){


        Client client = clientRepository.findById(orderRequest.getClientId()).orElseThrow(() -> new NoSuchClientException("No such client"));

        Order order = Order.builder()
                .address(orderRequest.getAddress())
                .comment(orderRequest.getComment())
                .client(client)
                .product(orderRequest.getProduct())
                .build();

        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId){
        orderRepository.deleteById(orderId);
    }

    public Order editOrder(OrderRequest orderRequest){

        Client client = clientRepository.findById(orderRequest.getClientId()).orElseThrow(() -> new NoSuchClientException("No such client"));

        Order order = orderRepository.findById(orderRequest.getId()).orElseThrow(() -> new NoSuchOrderException("No such order"));

        order.setAddress(orderRequest.getAddress());
        order.setComment(orderRequest.getComment());
        order.setProduct(orderRequest.getProduct());
        order.setClient(client);

        return orderRepository.save(order);
    }

    public Order getOrder(Long orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new NoSuchOrderException("No such order"));
    }

    public Page<Order> getAllClientsOrders(Long clientId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return orderRepository.findByClientId(clientId, pageable);
    }


}
