package com.github.jvalentino.kilo.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jvalentino.kilo.dto.UserDto
import com.github.jvalentino.kilo.entity.AuthUser
import com.github.jvalentino.kilo.service.UserService
import groovy.transform.CompileDynamic
import groovy.util.logging.Slf4j
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

/**
 * Listens on the doc topic
 * @author john.valentino
 */
@CompileDynamic
@Service
@Slf4j
class UserListener {

    @Value('${topic.name.consumer')
    String topicName

    @Value('${spring.kafka.consumer.group-id}')
    String groupId

    @Autowired
    UserService userService

    @KafkaListener(topics = '${topic.name.consumer}', groupId = '${spring.kafka.consumer.group-id}')
    AuthUser consume(ConsumerRecord<String, String> payload) {
        String json = payload.value()
        log.info(json)
        UserDto input = toObject(json, UserDto)
        userService.process(input)
    }

    Object toObject(String json, Class clazz) {
        new ObjectMapper().readValue(json, clazz)
    }

}
