package ma.toudertcolis.delivery_service.controller;

import ma.toudertcolis.delivery_service.dto.DeliverySummary;
import ma.toudertcolis.delivery_service.entity.Delivery;
import ma.toudertcolis.delivery_service.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/deliveries")
@CrossOrigin(origins = "http://localhost:4200")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    // Get all deliveries
    @GetMapping
    public Flux<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries()
                .onErrorResume(e -> {
                    // Log the error (optional)
                    return Flux.error(new RuntimeException("Error fetching deliveries"));
                });
    }

    // Get all deliveries by UID
    @GetMapping("/uid/{uid}")
    public Flux<Delivery> getAllDeliveriesByUID(@PathVariable String uid) {
        return deliveryService.getAllDeliveriesByUID(uid)
                .onErrorResume(e -> {
                    // Log the error (optional)
                    return Flux.error(new RuntimeException("Error fetching deliveries for UID: " + uid));
                });
    }

    @GetMapping("/tracking/{trackingNumber}")
    public Flux<Delivery> getAllDeliveriesByTrackingNumber(@PathVariable String trackingNumber) {
        return deliveryService.getAllDeliveriesByTrackingNumber(trackingNumber)
                .onErrorResume(e -> {
                    // Log the error (optional)
                    return Flux.error(new RuntimeException("Error fetching deliveries for tracking number: " + trackingNumber));
                });
    }
    // Get a specific delivery by ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Delivery>> getDeliveryById(@PathVariable String id) {
        return deliveryService.getDeliveryById(id)
                .map(delivery -> ResponseEntity.ok(delivery))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> {
                    // Log the error (optional)
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(null));
                });
    }

    // Create a new delivery
    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        Delivery createdDelivery = deliveryService.createDelivery(delivery);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDelivery);
    }

    // Delete a delivery by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable String id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
    @PatchMapping("/liv/{id}")
    public Mono<Delivery> updateDeliveryStatus(@PathVariable String id, @RequestBody Delivery updatedDelivery) {
        return deliveryService.getDeliveryById(id)
                .flatMap(existingDelivery -> {
                    existingDelivery.setStatus(updatedDelivery.getStatus());
                    // Wrap the return value of createDelivery inside Mono.just()
                    return Mono.just(deliveryService.createDelivery(existingDelivery));
                });
    }




    @GetMapping("/summary/{uid}")
    public Mono<DeliverySummary> getDeliverySummary(@PathVariable String uid) {
        return deliveryService.getDeliverySummary(uid)
                .onErrorResume(e -> {
                    // Log the error (optional)
                    return Mono.error(new RuntimeException("Error fetching delivery summary for UID: " + uid));
                });
    }
}
