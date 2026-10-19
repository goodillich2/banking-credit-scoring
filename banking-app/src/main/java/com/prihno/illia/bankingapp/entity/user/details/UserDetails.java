package com.prihno.illia.bankingapp.entity.user.details;

import com.prihno.illia.bankingapp.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "age")
    private int age;

    @Column(name = "month_income")
    private double monthIncome;

    @Column(name = "employment_length")
    private int employmentLength;

    @Enumerated(EnumType.STRING)
    @Column(name = "home_ownership")
    private HomeOwnership homeOwnership;

    @Column(name = "previous_loans")
    private int previousLoans;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_default")
    private PreviousDefault previousDefault;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
