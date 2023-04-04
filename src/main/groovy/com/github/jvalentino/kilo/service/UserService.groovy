package com.github.jvalentino.kilo.service

import com.github.jvalentino.kilo.dto.UserDto
import com.github.jvalentino.kilo.entity.AuthUser
import com.github.jvalentino.kilo.repo.AuthUserRepo
import groovy.transform.CompileDynamic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Services used for dealing with the auth_user table based on events
 */
@CompileDynamic
@Slf4j
@Service
class UserService {

    @Autowired
    AuthUserRepo authUserRepo

    AuthUser process(UserDto input) {
        Optional<AuthUser> optional = authUserRepo.findById(UUID.fromString(input.uuid))
        AuthUser user = null

        if (optional.present) {
            log.info("User ${input.uuid} already exists, updating it")
            user = optional.get()
        } else {
            log.info("User ${input.uuid} does NOT exist, creating it")
            user = new AuthUser()
            user.authUserId = UUID.fromString(input.uuid)
        }

        user.with {
            firstName = input.firstName
            lastName = input.lastName
            salt = input.salt
            password = input.password
            email = input.email
        }

        authUserRepo.save(user)

        user
    }

}
