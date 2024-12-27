package ma.toudertcolis.delivery_service.client;

import ma.toudertcolis.delivery_service.dto.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flask-service", url = "http://flask-service:8081")
public interface PersonFeignClient {
    @GetMapping("/users/{id}")
    Person getUserById(@PathVariable("id") Long personID);
}
