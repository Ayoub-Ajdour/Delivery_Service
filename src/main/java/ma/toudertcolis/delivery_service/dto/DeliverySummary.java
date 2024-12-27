package ma.toudertcolis.delivery_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliverySummary {
    private Double totalGains;
    private String lastPayment;
    private Long totalParcelsCollected;
    private Long totalDeliveries;
    private Long totalDeliveredBon;
    private Double totalCRBT;

    public Double getTotalGains() {
        return totalGains;
    }

    public void setTotalGains(Double totalGains) {
        this.totalGains = totalGains;
    }

    public String getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(String lastPayment) {
        this.lastPayment = lastPayment;
    }

    public Long getTotalParcelsCollected() {
        return totalParcelsCollected;
    }

    public void setTotalParcelsCollected(Long totalParcelsCollected) {
        this.totalParcelsCollected = totalParcelsCollected;
    }

    public Long getTotalDeliveries() {
        return totalDeliveries;
    }

    public void setTotalDeliveries(Long totalDeliveries) {
        this.totalDeliveries = totalDeliveries;
    }

    public Long getTotalDeliveredBon() {
        return totalDeliveredBon;
    }

    public void setTotalDeliveredBon(Long totalDeliveredBon) {
        this.totalDeliveredBon = totalDeliveredBon;
    }

    public Double getTotalCRBT() {
        return totalCRBT;
    }

    public void setTotalCRBT(Double totalCRBT) {
        this.totalCRBT = totalCRBT;
    }
}
