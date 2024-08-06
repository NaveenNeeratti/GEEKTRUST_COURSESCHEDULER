package com.geektrust.backend.repository;

import com.geektrust.backend.entity.Course;

public interface ICourseRepository extends CRUDRepository<Course,String>{
    /**
     * It saves the record in the database.
     * @param course - The record which need to be saved
     * @return - returns courseOfferingId after saving course object.
     */
    public String save(Course course);
}
