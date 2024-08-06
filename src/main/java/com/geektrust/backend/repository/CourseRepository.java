package com.geektrust.backend.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entity.Course;

public class CourseRepository implements ICourseRepository{
    private final Map<String,Course> courseMap;
    
    public CourseRepository(Map<String,Course> courseMap){
        this.courseMap = courseMap;
    }
    public CourseRepository(){
        courseMap = new HashMap<>();
    }
    
    @Override
    public String save(Course newCourse) {
        String courseName = newCourse.getCourseName();
        String instructorName = newCourse.getIntructorName();
        String courseOfferingId = buildCourseOfferingId(courseName,instructorName);
        newCourse.setCourseOfferingId(courseOfferingId);
        courseMap.put(courseOfferingId,newCourse);
        return courseOfferingId;
    }

    @Override
    public Optional<Course> findById(String courseOfferingId){
        return Optional.of(courseMap.get(courseOfferingId));
    }

    @Override
    public List<Course> findAll() {
        return courseMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public void delete(String courseOfferingId) {
        courseMap.remove(courseOfferingId);
    }

    private String buildCourseOfferingId(String courseName, String instructorName) {
        return "OFFERING"+"-"+courseName.toUpperCase()+"-"+instructorName.toUpperCase();
    }
}
