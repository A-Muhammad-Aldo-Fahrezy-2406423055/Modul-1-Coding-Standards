package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/detail")
    public String paymentDetailFormPage() { return "payment/detail_form"; }

    @GetMapping("/detail/{paymentId}")
    public String paymentDetailPage(@PathVariable String paymentId, Model model) {
        model.addAttribute("payment", paymentService.getPayment(paymentId));
        return "payment/detail";
    }

    @GetMapping("/admin/list")
    public String paymentAdminListPage(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        return "payment/admin_list";
    }

    @GetMapping("/admin/detail/{paymentId}")
    public String paymentAdminDetailPage(@PathVariable String paymentId, Model model) {
        model.addAttribute("payment", paymentService.getPayment(paymentId));
        return "payment/admin_detail";
    }

    @PostMapping("/admin/set-status/{paymentId}")
    public String paymentAdminSetStatusPost(@PathVariable String paymentId, @RequestParam String status) {
        Payment payment = paymentService.getPayment(paymentId);
        if (payment != null) paymentService.setStatus(payment, status);
        return "redirect:/payment/admin/list";
    }
}