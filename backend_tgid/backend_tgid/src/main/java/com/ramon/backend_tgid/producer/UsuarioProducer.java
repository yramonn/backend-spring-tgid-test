package com.ramon.backend_tgid.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramon.backend_tgid.dtos.EmailDTO;
import com.ramon.backend_tgid.models.Usuario;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UsuarioProducer {

    final RabbitTemplate rabbitTemplate;

    public UsuarioProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;


    public void publishMessageEmail(Usuario usuario) throws JsonProcessingException {
        EmailDTO emailDto = new EmailDTO();
        emailDto.setIdEmail(usuario.getId());
        emailDto.setDestinatario(usuario.getEmail());
        emailDto.setDescricao("Transação efetuada com sucesso!");
        emailDto.setTexto(usuario.getNome() + "Notificação que sua transação foi efetuada com sucesso em nossaplataforma!");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(emailDto);
        rabbitTemplate.convertAndSend("",routingKey, jsonPayload);

    }


}
