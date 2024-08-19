package com.cleverdevsoftware.service;

import com.cleverdevsoftware.entity.CompanyUser;
import com.cleverdevsoftware.repository.CompanyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CompanyUserRepository companyUserRepository;

    public CompanyUser getOrCreateUser(String login) {
        return companyUserRepository.findByLogin(login)
                .orElseGet(() -> {
                    CompanyUser newUser = new CompanyUser();
                    newUser.setLogin(login);
                    return companyUserRepository.save(newUser);
                });
    }

}
