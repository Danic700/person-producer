package com.person.controller;
import com.person.model.Person;
import com.person.service.ProduceMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class ProduceMessageController {

    private final ProduceMessageService produceMessageService;


    @PostMapping("/produce")
    public ResponseEntity<String> produceMessage(@RequestBody Person person) {
        try {
            String message = produceMessageService.produceMessage(person);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
