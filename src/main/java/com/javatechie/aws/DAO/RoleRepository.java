package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.ERole;
import com.javatechie.aws.Model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(ERole name);
}
