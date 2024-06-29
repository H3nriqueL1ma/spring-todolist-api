package com.onrender.todolistjeed.todolistapi.Repositories;

import com.onrender.todolistjeed.todolistapi.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iUserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);
    UserModel findByUsername(String username);
}
