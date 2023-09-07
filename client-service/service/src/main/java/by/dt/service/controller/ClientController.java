package by.dt.service.controller;

import by.dt.api.scope.ClientServiceApi;
import by.dt.api.scope.dto.requst.CreateRequest;
import by.dt.api.scope.dto.response.ClientResponse;
import by.dt.service.converter.ClientConverter;
import by.dt.service.service.iface.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ClientController implements ClientServiceApi, BaseController {

    private final ClientService clientService;

    @Override
    public Optional<ClientResponse> getByEmail(String email) {
        return ClientConverter
                .toClientResponse(clientService.get(email));
    }

    @Override
    public ClientResponse get(long id) {
        return ClientConverter
                .toClientResponse(clientService.get(id));
    }

    @Override
    public boolean exist(long id) {
        return clientService.exist(id);
    }

    @Override
    public ClientResponse create(CreateRequest request) {
        return ClientConverter
                .toClientResponse(clientService.create(request.getEmail()));
    }
}
