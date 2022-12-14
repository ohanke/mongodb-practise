package com.oscarhanke.mongodbpractise.controller;

import com.oscarhanke.mongodbpractise.model.Student;
import com.oscarhanke.mongodbpractise.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getAll(){
        return studentService.findAll();
    }

    @GetMapping("{email}")
    public Student getByEmail(@PathVariable String email){
        return studentService.findByEmail(email);
    }

    @PostMapping
    public Student create(@RequestBody Student student){
        return studentService.create(student);
    }

    @PutMapping("{id}")
    public Student update(@RequestBody Student student){
        return studentService.update(student);
    }

    @DeleteMapping("{email}")
    public void delete(@PathVariable String email){
        studentService.deleteByEmail(email);
    }

    @DeleteMapping
    public void delete(){
        studentService.deleteAll();
    }
}
