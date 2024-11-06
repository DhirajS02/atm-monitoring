package com.atm.atm_monitoring.model;

import com.atm.atm_monitoring.enums.ERole;
import jakarta.validation.constraints.NotNull;

public class Role {
    private ERole name;
    public Role(@NotNull String name) {
        this.name = ERole.valueOf(name.toUpperCase());
    }
    public ERole getName() {
        return name;
    }
}
