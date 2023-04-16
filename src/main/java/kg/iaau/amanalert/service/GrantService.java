package kg.iaau.amanalert.service;

import kg.iaau.amanalert.enums.Role;

public interface GrantService {
    void hasAny(Role... roles);
}
