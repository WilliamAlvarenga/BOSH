package com.bosh.challenger.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.bosh.challenger.entity.Hook;

@Repository
public interface HookRepository extends R2dbcRepository<Hook,Long>{


}
