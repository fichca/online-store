package by.dt.service.service.iface;

import by.dt.service.entity.Client;

import java.util.Optional;

public interface ClientService {

    Optional<Client> get(String email);

    Client get(long id);

    Client create(String email);

    boolean exist(long id);
}
