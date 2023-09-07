package by.dt.service.service;

import by.dt.service.entity.Client;
import by.dt.service.exception.ClientNotFoundException;
import by.dt.service.repo.ClientRepo;
import by.dt.service.service.iface.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;

    @Override
    public Optional<Client> get(String email) {
        return clientRepo.findClientByEmail(email);
    }

    @Override
    public Client get(long id) {
        return clientRepo.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found!"));
    }

    @Override
    public Client create(String email) {
        return clientRepo.save(Client.builder()
                .email(email)
                .build());
    }

    @Override
    public boolean exist(long id) {
        return clientRepo.existsById(id);
    }
}
