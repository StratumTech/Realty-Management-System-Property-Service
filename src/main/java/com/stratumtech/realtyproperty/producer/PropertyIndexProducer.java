package com.stratumtech.realtyproperty.producer;

import java.util.Map;
import java.util.UUID;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;

import com.stratumtech.realtyproperty.dto.PropertyDTO;
import com.stratumtech.realtyproperty.dto.mapper.PropertyMapper;
import com.stratumtech.realtyproperty.dto.request.PropertyIndexRequest;
import com.stratumtech.realtyproperty.dto.request.PropertyUpdateRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropertyIndexProducer {

    @Builder
    private record KafkaRequest(
            String operation,
            UUID entityId,
            Object data
    ) {
    }

    @Value("${kafka.topic.indexing-property-topic}")
    private String topic;

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    private final PropertyMapper propertyMapper;

    public void sendPropertyIndex(PropertyDTO propertyDTO) {
        PropertyIndexRequest propertyIndexRequest = propertyMapper.toPropertyIndexRequest(propertyDTO);

        final var request = KafkaRequest.builder()
                                .operation("index")
                                .entityId(propertyDTO.getId())
                                .data(propertyIndexRequest)
                                .build();

        kafkaTemplate.send(topic, request);
        log.debug("Published property '{}' index request to topic: {}", propertyDTO.getId(), topic);
    }

    public void sendPropertyUpdate(UUID propertyUuid, Map<String, Object> changes) {
        final var propertyUpdateRequest = new PropertyUpdateRequest(changes);

        final var request = KafkaRequest.builder()
                .operation("update")
                .entityId(propertyUuid)
                .data(propertyUpdateRequest)
                .build();

        kafkaTemplate.send(topic, request);
        log.debug("Published property '{}' update request to topic: {}", propertyUuid, topic);
    }

    public void sendPropertyDelete(UUID propertyUuid) {
        final var request = KafkaRequest.builder()
                .operation("delete")
                .entityId(propertyUuid)
                .data(null)
                .build();

        kafkaTemplate.send(topic, request);
        log.debug("Published property '{}' delete request to topic: {}", propertyUuid, topic);
    }
}
