package ma.toudertcolis.delivery_service.service;

import ma.toudertcolis.delivery_service.client.PersonFeignClient;
import ma.toudertcolis.delivery_service.dto.DeliverySummary;
import ma.toudertcolis.delivery_service.dto.Livreur;
import ma.toudertcolis.delivery_service.dto.Person;
import ma.toudertcolis.delivery_service.entity.Delivery;
import ma.toudertcolis.delivery_service.repository.DeliveryRepository;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.Comparator;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final PersonFeignClient personFeignClient;
    private final HttpGraphQlClient graphQlClient;

    public DeliveryService(DeliveryRepository deliveryRepository, PersonFeignClient personFeignClient) {
        this.deliveryRepository = deliveryRepository;
        this.personFeignClient = personFeignClient;
        this.graphQlClient = HttpGraphQlClient.builder()
                .url("http://flask-service:8081/graphql")
                .build();
    }

    public Flux<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll()  // Directly use the Flux from the repository
                .flatMap(delivery -> Mono.zip(
                        getLivreurById(delivery.getDeliveryPersonId()),
                        getPersonById(delivery.getCustomerId())
                ).map(tuple -> {
                    delivery.setDeliveryPerson(tuple.getT1());
                    delivery.setCustomer(tuple.getT2());
                    return delivery;
                }));
    }

    public Mono<Delivery> getDeliveryById(String id) {
        return deliveryRepository.findById(id)  // No need to wrap in Mono.justOrEmpty
                .flatMap(delivery -> Mono.zip(
                        getLivreurById(delivery.getDeliveryPersonId()),
                        getPersonById(delivery.getCustomerId())
                ).map(tuple -> {
                    delivery.setDeliveryPerson(tuple.getT1());
                    delivery.setCustomer(tuple.getT2());
                    return delivery;
                }));
    }

    public Delivery createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery).block();
    }

    public void deleteDelivery(String id) {
        deliveryRepository.deleteById(id);
    }

    public Mono<Livreur> getLivreurById(Long id) {
        return graphQlClient.document("""
            query($id: Int!) {
                userById(id: $id) {
                    id
                    name
                    phoneNumber
                    location
                    numberCommands
                    email
                }
            }
        """)
                .variable("id", id)
                .retrieve("userById")
                .toEntity(Livreur.class)
                .onErrorResume(e -> Mono.empty()); // Handle error gracefully
    }

    public Mono<Person> getPersonById(Long id) {
        return graphQlClient.document("""
            query($id: Int!) {
              userById(id: $id) {
                id
                name
                email
                address
                cityCode
              }
            }
        """)
                .variable("id", id)
                .retrieve("userById")
                .toEntity(Person.class)
                .onErrorResume(e -> Mono.empty()); // Handle error gracefully
    }

    public Flux<Delivery> getAllDeliveriesByUID(String uid) {
        return deliveryRepository.findAllByUid(uid);
    }
    public Mono<DeliverySummary> getDeliverySummary(String uid) {
        return deliveryRepository.findAllByUid(uid)
                .collectList()
                .map(deliveries -> {
                    Double totalGains = deliveries.stream()
                            .mapToDouble(delivery -> delivery.getPrix() != null ? delivery.getPrix() : 0.0)
                            .sum();

                    String lastPayment = deliveries.stream()
                            .filter(delivery -> delivery.getDeliveryTime() != null)
                            .max(Comparator.comparing(Delivery::getDeliveryTime))
                            .map(delivery -> delivery.getDeliveryTime().toString())
                            .orElse("---");

                    Long totalParcelsCollected = (long) deliveries.size();

                    Long totalDeliveries = deliveries.stream()
                            .filter(delivery -> {
                                String status = delivery.getStatus();
                                return status != null && (status.equals("IN_TRANSIT") || status.equals("DELIVERED"));
                            })
                            .count();

                    Long totalDeliveredBon = deliveries.stream()
                            .filter(delivery -> "DELIVERED".equals(delivery.getStatus()))
                            .count();

                    Double totalCRBT = deliveries.stream()
                            .filter(delivery -> "DELIVERED".equals(delivery.getStatus()))
                            .mapToDouble(delivery -> delivery.getPrix() != null ? delivery.getPrix() : 0.0)
                            .sum();

                    return new DeliverySummary(
                            totalGains,
                            lastPayment,
                            totalParcelsCollected,
                            totalDeliveries,
                            totalDeliveredBon,
                            totalCRBT
                    );
                });
    }
    public Flux<Delivery> getAllDeliveriesByTrackingNumber(String trackingNumber) {
        return deliveryRepository.findAllByTrackingNumber(trackingNumber)
                .onErrorResume(e -> {
                    // Handle any repository-related error gracefully
                    return Flux.error(new RuntimeException("Error fetching deliveries for tracking number: " + trackingNumber));
                });
    }

}
