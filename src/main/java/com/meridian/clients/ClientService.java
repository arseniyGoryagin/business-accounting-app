package com.meridian.clients;


import com.meridian.clients.domain.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Page<Client> searchClientByAddress(String query, int page, int size){
        return clientRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(query,query,  PageRequest.of(page, size));
    }


    public Client getClient(Long clientId){
        return clientRepository.findById(clientId).orElseThrow(() -> {throw new NoSuchElementException("No such client");});
    }


    public void deleteClient(Long clientId){
        clientRepository.deleteById(clientId);
    }


    public Client addClient(Client client){
        return clientRepository.save(client);
    }

    public Client editClient(Client newClient){

        Client client = clientRepository.findById(newClient.getId()).orElseThrow(() -> {throw new NoSuchElementException("No such client");});

        client.setAddress(newClient.getAddress());
        client.setName(newClient.getName());
        client.setComment(newClient.getComment());
        client.setFloor(newClient.getFloor());
        client.setPhoneNumber(newClient.getPhoneNumber());

        return clientRepository.save(client);

    }


}
