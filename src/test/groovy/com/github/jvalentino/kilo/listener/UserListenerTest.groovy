package com.github.jvalentino.kilo.listener

import com.github.jvalentino.kilo.entity.AuthUser
import com.github.jvalentino.kilo.repo.AuthUserRepo
import com.github.jvalentino.kilo.util.BaseIntg
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean

class UserListenerTest extends BaseIntg {

    @MockBean
    AuthUserRepo authUserRepo

    @Autowired
    UserListener subject

    void "test new user"() {
        given:
        String json = new File('src/test/resources/event-1.json').text
        ConsumerRecord<String, String> payload = GroovyMock()
        1 * payload.value() >> json

        and:
        Optional<AuthUser> optional = GroovyMock()
        org.mockito.Mockito.when(
                authUserRepo.findById(UUID.fromString('f2b52578-4346-41e1-a9ca-03c91b787ecc')))
                .thenReturn(optional)
        1 * optional.present >> false

        when:
        AuthUser result = subject.consume(payload)

        then:
        result.authUserId.toString() == 'f2b52578-4346-41e1-a9ca-03c91b787ecc'
        result.email == 'test'
        result.password == '$1$Mf9yuNJZ$SUZCTwjjVh2i8UVb.EppP.'
        result.salt == '$1$Mf9yuNJZ'
        result.firstName == 'John'
        result.lastName == 'Valentino'
    }

    void "test update user"() {
        given:
        String json = new File('src/test/resources/event-1.json').text
        ConsumerRecord<String, String> payload = GroovyMock()
        1 * payload.value() >> json

        and:
        Optional<AuthUser> optional = GroovyMock()
        org.mockito.Mockito.when(
                authUserRepo.findById(UUID.fromString('f2b52578-4346-41e1-a9ca-03c91b787ecc')))
                .thenReturn(optional)
        1 * optional.present >> true

        and:
        AuthUser user = new AuthUser()
        user.authUserId = UUID.fromString('f2b52578-4346-41e1-a9ca-03c91b787ecc')
        1 * optional.get() >> user

        when:
        AuthUser result = subject.consume(payload)

        then:
        result.authUserId.toString() == 'f2b52578-4346-41e1-a9ca-03c91b787ecc'
        result.email == 'test'
        result.password == '$1$Mf9yuNJZ$SUZCTwjjVh2i8UVb.EppP.'
        result.salt == '$1$Mf9yuNJZ'
        result.firstName == 'John'
        result.lastName == 'Valentino'
    }

}
