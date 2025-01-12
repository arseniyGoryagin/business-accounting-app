package com.meridian.clients;


import com.meridian.clients.domain.Client;
import com.meridian.clients.domain.ClientRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.generic.InstructionConstants;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/clients")
@RequiredArgsConstructor
public class ClientController {


    private final ClientService clientService;


    @GetMapping()
    ResponseEntity<Page<Client>> searchClients(@RequestParam("query") String query,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size
                                               ){
        return new ResponseEntity<Page<Client>>(clientService.searchClientByAddress(query, page, size), HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    ResponseEntity<Client> searchClients(@PathVariable Long clientId){
        return new ResponseEntity<Client>(clientService.getClient(clientId), HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<Client> addClient(@RequestBody ClientRequest clientRequest){

        Client client = Client.builder()
                .name(clientRequest.getName())
                .address(clientRequest.getAddress())
                .floor(clientRequest.getFloor())
                .comment(clientRequest.getFloor())
                .phoneNumber(clientRequest.getPhoneNumber())
                .build();
        return new ResponseEntity<Client>(clientService.addClient(client), HttpStatus.OK);
    }


    @DeleteMapping()
    ResponseEntity<String> removeClient(@RequestParam("clientId") Long clientId){
        clientService.deleteClient(clientId);
        return new ResponseEntity<String>("Клиент удалён", HttpStatus.OK);
    }


    @PutMapping("/{clientId}")
    ResponseEntity<Client> editClient(@RequestBody ClientRequest clientRequest, @PathVariable("clientId") Long clientId){

        Client client = Client.builder()
                .id(clientId)
                .name(clientRequest.getName())
                .address(clientRequest.getAddress())
                .floor(clientRequest.getFloor())
                .comment(clientRequest.getFloor())
                .phoneNumber(clientRequest.getPhoneNumber())
                .build();
        return new ResponseEntity<Client>(clientService.editClient(client), HttpStatus.OK);
    }

}
