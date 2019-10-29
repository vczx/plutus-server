package com.vince.plutus.repository;

import com.vince.plutus.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
