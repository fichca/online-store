package by.dt.api.scope;

import by.dt.api.scope.dto.requst.CreateRequest;
import by.dt.api.scope.dto.response.ClientResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface ClientServiceApi {

    @GetMapping("scope/v1/client")
    Optional<ClientResponse> getByEmail(@RequestParam @NotNull String email);

    @GetMapping("scope/v1/client/{id}")
    ClientResponse get(@PathVariable long id);

    @GetMapping("scope/v1/client/exist/{id}")
    boolean exist(@PathVariable long id);

    @PostMapping("scope/v1/client")
    ClientResponse create(@RequestBody @Valid CreateRequest request);
}
