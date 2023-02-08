package com.br.mmcafe.uploadfile.consumer;

import com.br.mmcafe.uploadfile.entity.DadosImagemEntity;
import com.br.mmcafe.uploadfile.repository.DadosImagemRepository;
import com.br.mmcafe.uploadfile.vo.FileVO;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UploadFileConsumer {

    @Value("${topic.name.consumer")
    private String topicName;

    @Autowired
    private DadosImagemRepository repository;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void consume(  ConsumerRecord<String, String> payload){

        FileVO vo = new Gson().fromJson(payload.value(), FileVO.class);

        DadosImagemEntity  entity = new DadosImagemEntity();
        entity.setNome(vo.getNome());
        entity.setTipo(vo.getTipo());
        entity.setUrl(vo.getUrl());

        repository.save(entity);

    }
}
