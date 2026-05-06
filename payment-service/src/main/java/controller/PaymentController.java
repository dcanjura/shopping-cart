package controller;

import dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.PaymentService;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public String processPayment(@RequestBody @Validated PaymentRequest request){
        return service.processPayment(
                request.paymentType(),
                request.amount()
        ) ? "SUCCESS" : "FAILED";
    }
}
