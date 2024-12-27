package ma.toudertcolis.delivery_service.repository;

import ma.toudertcolis.delivery_service.entity.Delivery;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface DeliveryRepository extends ReactiveMongoRepository<Delivery, String> {
    Flux<Delivery> findAllByUid(String uid);
    Flux<Delivery> findAllByTrackingNumber(String trackingNumber);
}
