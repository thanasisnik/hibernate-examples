package gr.aueb.cf.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    public void addRegion(){
        this.setRegion(region);
        region.addTeacher(this);
    }

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_more_info_id")
    private TeacherMoreInfo teacherMoreInfo;


    public void addCourse(Course course){
        if (courses == null) courses = new HashSet<>();
        courses.add(course);
        course.getTeachers().add(this);
    }

    public void removeCourse(Course course){
        if (courses == null) return;
        courses.remove(course);
        course.getTeachers().remove(this);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
