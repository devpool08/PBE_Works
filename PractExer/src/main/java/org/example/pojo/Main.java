package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Student implements Comparable<Student>{
    private int id;
    private String name;
    private int marks;

    @Override
    public int compareTo(Student s){
        return this.marks-s.marks;
    }
}
//Controller
@SuppressWarnings("all")
class StudentController{
    Map<Integer,Student> studentDB = new LinkedHashMap<>();
    public Student addStudent(Student s){
        return studentDB.put(s.getId(),s);
    }
    public Student removeStudent(int id){
        return studentDB.remove(id);
    }
    public List<Student> getAllStudent(){
        List<Student> students = new ArrayList<>(studentDB.values());
        return students;
    }
    public Student updateStudentData(int id, String name,int  marks){
        return studentDB.put(id, Student.builder().id(id).name(name).marks(marks).build());
    }
    public List<Student> sortStudentonMarks(){
        List<Student> students = new ArrayList<>(studentDB.values());
        students.sort(null);
        return students;
    }
    public Map<Integer,List<Student>> getGroupByMarks(){
        Map<Integer, List<Student>> groupedByMarks = new HashMap<>();
        List<Student> students = new ArrayList<>(studentDB.values());
        for(Student s: students){
            groupedByMarks
                    .computeIfAbsent(s.getMarks()
                            ,k->new ArrayList<>())
                    .add(s);
        }
        return groupedByMarks;
    }
    public List<Student> sortByMarksThenName(){
        List<Student> students = new ArrayList<>(studentDB.values());
        students.sort(Comparator.comparing(Student::getMarks).reversed().thenComparing(Student::getName));
        return students;
    }
}


public class Main {
    public static void main(String[] args) {
        StudentController studentController = new StudentController();
        studentController.addStudent(new Student(1,"Alice",85));
        studentController.addStudent(new Student(2,"Bob",90));
        studentController.addStudent(new Student(3,"Charlie",75));
        studentController.addStudent(new Student(4,"Diana",90));
        studentController.addStudent(new Student(5,"Eve",85));

        System.out.println("All Students: " + studentController.getAllStudent());
        System.out.println("Sorted by Marks: " + studentController.sortStudentonMarks());
        System.out.println("Grouped by Marks: " + studentController.getGroupByMarks());
        System.out.println("Sorted by Marks then Name: " + studentController.sortByMarksThenName());
        studentController.updateStudentData(3, "Charlie", 95);
    }
}
