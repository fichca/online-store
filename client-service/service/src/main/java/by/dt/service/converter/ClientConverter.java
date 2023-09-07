package by.dt.service.converter;

import by.dt.api.scope.dto.response.ClientResponse;
import by.dt.service.entity.Client;

import java.util.Optional;

public class ClientConverter {

    public static ClientResponse toClientResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .email(client.getEmail())
                .build();
    }

    public static Optional<ClientResponse> toClientResponse(Optional<Client> client) {
        return client.map(value -> ClientResponse.builder()
                .id(value.getId())
                .email(value.getEmail())
                .build());
    }
}
