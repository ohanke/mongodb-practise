package com.oscarhanke.mongodbpractise.service;

import com.oscarhanke.mongodbpractise.model.Gender;
import com.oscarhanke.mongodbpractise.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentQueries {

    private final MongoTemplate mongoTemplate;

    public List<Student> findAll(String field, int pageNb, int pageSize){
        Query allPagedAndOrdered = new Query()
                .with(Sort.by(Sort.Direction.ASC, field))
                .with(PageRequest.of(pageNb, pageSize));

        return this.mongoTemplate.find(allPagedAndOrdered ,Student.class);
    }

    public Student findSingleById(String id){
        return this.mongoTemplate.findById(id, Student.class);
    }

    public long countMales(){
        Query males = Query.query(Criteria.where("gender")
                .in(Gender.MALE));

        return this.mongoTemplate.count(males, Student.class);
    }

    public List<Student> findByFavouriteSubjects(String subject){
        Query subjects = Query.query(Criteria.where("subjects").in(subject));

        return this.mongoTemplate.find(subjects, Student.class);
    }

    public List<Student> findByFreeText(String text){
        TextCriteria textCriteria = TextCriteria
                .forDefaultLanguage()
                .matching(text);

        Query byFreeText = TextQuery.queryText(textCriteria)
                .sortByScore()
                .with(PageRequest.of(0, 3));

        return this.mongoTemplate.find(byFreeText, Student.class);
    }
}
