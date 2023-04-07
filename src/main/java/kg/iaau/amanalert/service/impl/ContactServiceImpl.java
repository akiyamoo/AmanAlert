package kg.iaau.amanalert.service.impl;

import kg.iaau.amanalert.repo.ContactRepository;
import kg.iaau.amanalert.service.ContactService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ContactServiceImpl implements ContactService {
    ContactRepository repository;

    // TODO
}