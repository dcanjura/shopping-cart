package domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "order_items")
@Entity
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String productId;

    @Positive
    @NotNull
    private Integer quantity;

    @NotNull
    @PositiveOrZero
    private Double price;


    @ManyToOne @NotNull
    @JoinColumn(name = "order_id")
    private Order order;
}
