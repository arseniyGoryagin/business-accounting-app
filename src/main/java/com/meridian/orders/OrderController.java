package com.meridian.orders;


import com.meridian.orders.domain.Order;
import com.meridian.orders.domain.OrderRequest;
import com.meridian.util.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable("orderId") Long orderId){
        return new ResponseEntity<>(orderService.getOrder(orderId), HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<MessageResponse> deleteOrder(@PathVariable("orderId") Long orderId){
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(new MessageResponse("Order deleted"), HttpStatus.OK);
    }

    @GetMapping("client/{client}")
    public ResponseEntity<Page<Order>> getOrder(@PathVariable("clientId") Long clientId, @RequestParam("page") int page, @RequestParam("size") int size){
        return new ResponseEntity<>(orderService.getAllClientsOrders(clientId, page, size), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Order> editOrder(@RequestBody OrderRequest orderRequest){
        return new ResponseEntity<>(orderService.editOrder(orderRequest), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Order> makeOrder(@RequestBody OrderRequest orderRequest){
        return new ResponseEntity<>(orderService.addOrder(orderRequest), HttpStatus.OK);
    }

}
