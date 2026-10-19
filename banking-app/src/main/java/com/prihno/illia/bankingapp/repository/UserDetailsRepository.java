package com.prihno.illia.bankingapp.repository;

import com.prihno.illia.bankingapp.entity.user.User;
import com.prihno.illia.bankingapp.entity.user.details.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository  extends JpaRepository<UserDetails, Integer> {
}
