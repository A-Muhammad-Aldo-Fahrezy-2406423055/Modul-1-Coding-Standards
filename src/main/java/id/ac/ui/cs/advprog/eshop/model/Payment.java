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
            this.status = validateVoucher() ? "SUCCESS" : "REJECTED";
        } else if ("CASH_ON_DELIVERY".equals(method)) {
            this.status = validateCOD() ? "SUCCESS" : "REJECTED";
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean validateVoucher() {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }
        int numCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) numCount++;
        }
        return numCount == 8;
    }

    private boolean validateCOD() {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");
        return address != null && !address.trim().isEmpty() && deliveryFee != null && !deliveryFee.trim().isEmpty();
    }
}