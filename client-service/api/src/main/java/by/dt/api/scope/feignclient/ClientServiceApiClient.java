package by.dt.api.scope.feignclient;

import by.dt.api.scope.ClientServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "client-service", url = "localhost:8081")
public interface ClientServiceApiClient extends ClientServiceApi {
}
