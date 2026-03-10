package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;

    @Setter
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;

        if ("VOUCHER".equals(method)) {
            String voucherCode = paymentData.get("voucherCode");
            if (voucherCode != null && voucherCode.length() == 16 && voucherCode.startsWith("ESHOP")) {
                int numCount = 0;
                for (char c : voucherCode.toCharArray()) {
                    if (Character.isDigit(c)) numCount++;
                }
                this.status = (numCount == 8) ? "SUCCESS" : "REJECTED";
            } else {
                this.status = "REJECTED";
            }
        } else if ("CASH_ON_DELIVERY".equals(method)) {
            String address = paymentData.get("address");
            String deliveryFee = paymentData.get("deliveryFee");
            if (address != null && !address.trim().isEmpty() && deliveryFee != null && !deliveryFee.trim().isEmpty()) {
                this.status = "SUCCESS";
            } else {
                this.status = "REJECTED";
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}