package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.ERole;
import com.javatechie.aws.Model.Role;
import com.javatechie.aws.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByName(ERole name);
}
