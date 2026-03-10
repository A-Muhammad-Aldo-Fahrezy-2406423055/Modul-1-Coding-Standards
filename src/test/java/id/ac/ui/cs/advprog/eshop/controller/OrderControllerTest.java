package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockitoBean private OrderService orderService;

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create")).andExpect(status().isOk()).andExpect(view().name("order/create"));
    }
    @Test
    void testHistoryOrderPage() throws Exception {
        mockMvc.perform(get("/order/history")).andExpect(status().isOk()).andExpect(view().name("order/history"));
    }
    @Test
    void testHistoryOrderPost() throws Exception {
        when(orderService.findAllByAuthor("Author")).thenReturn(new ArrayList<>());
        mockMvc.perform(post("/order/history").param("author", "Author"))
                .andExpect(status().isOk()).andExpect(view().name("order/history")).andExpect(model().attributeExists("orders"));
    }
    @Test
    void testPayOrderPage() throws Exception {
        mockMvc.perform(get("/order/pay/123")).andExpect(status().isOk()).andExpect(view().name("order/pay"));
    }
    @Test
    void testPayOrderPost() throws Exception {
        mockMvc.perform(post("/order/pay/123")).andExpect(status().isOk()).andExpect(view().name("order/payment_result"));
    }
}