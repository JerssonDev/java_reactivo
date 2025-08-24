package co.com.bancolombia.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table("users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    private Long id;

    @NotBlank(message = "First name cannot be empty")
    @Column("first_name")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Column("last_name")
    private String lastName;

    @Column("birth_date")
    private LocalDate birthDate;

    @Column("address")
    private String address;

    @Column("phone")
    private String phone;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Column("email")
    private String email;

    @NotNull(message = "Base salary is required")
    @Min(value = 0, message = "Base salary must be greater or equal to 0")
    @Max(value = 15000000, message = "Base salary must not exceed 15,000,000")
    @Column("base_salary")
    private Double baseSalary;
}
