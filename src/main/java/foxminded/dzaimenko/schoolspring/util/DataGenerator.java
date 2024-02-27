package foxminded.dzaimenko.schoolspring.util;

import foxminded.dzaimenko.schoolspring.dao.CourseDao;
import foxminded.dzaimenko.schoolspring.dao.GroupDao;
import foxminded.dzaimenko.schoolspring.dao.StudentDao;
import foxminded.dzaimenko.schoolspring.model.Course;
import foxminded.dzaimenko.schoolspring.model.Group;
import foxminded.dzaimenko.schoolspring.model.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class DataGenerator {
    private final Random random = new Random();
    private final CourseDao courseDao;
    private final GroupDao groupDao;
    private final StudentDao studentDao;

    public DataGenerator(CourseDao courseDao, GroupDao groupDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.groupDao = groupDao;
        this.studentDao = studentDao;
    }

    public void fillTables() {
        generateGroups();
        generateStudents();
        generateCourses();
        generateStudentsCourses();
        System.out.println("Database filled successfully");
    }

    private List<Group> generateGroups() {
        List<Group> groups = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String groupName = SchoolData.groupsNames[i];
            Group group = new Group();
            group.setName(groupName);


        }
    }

    private Set<String> generateUniqueStudentsSet() {
        Set<String> uniqueStudents = new HashSet<>();

        while (uniqueStudents.size() < 200) {
            String firstName = SchoolData.firstNamesArray[random.nextInt(20)];
            String lastName = SchoolData.lastNamesArray[random.nextInt(20)];

            String uniqueKey = firstName + " " + lastName;

            uniqueStudents.add(uniqueKey);
        }

        return uniqueStudents;
    }

    private void generateStudents() {
        Set<String> uniqueStudents = generateUniqueStudentsSet();
        List<Group> groups = groupDao.getAll();

        for (String uniqueKey : uniqueStudents) {
            Integer groupId = groups.get(random.nextInt(10)).getId();

            String[] nameParts = uniqueKey.split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts[1];

            Student student = new Student();
            student.setId(groupId);
            student.setFirstName(firstName);
            student.setLastName(lastName);

            student.setId(studentDao.create(student));
        }
    }

    private List<Course> generateCourses() {
        List<Course> courses = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String courseName = SchoolData.coursesNames[i];
            String courseDescription = SchoolData.coursesDescriptions[i];

            Course course = new Course();
            course.setName(courseName);
            course.setDescription(courseDescription);

            courseDao.create(course);

            courses.add(course);
        }
        return courses;
    }

    private void generateStudentsCourses() {
        List<Student> students = studentDao.getAll();
        List<Course> courses = generateCourses();

        for (Student student : students) {
            assignRandomCourses(student, courses);
        }
    }

    private void assignRandomCourses(Student student, List<Course> courses) {
        Set<Integer> assignedCourseIds = new HashSet<>();
        int numberOfCourses = random.nextInt(3) + 1;

        while (assignedCourseIds.size() < numberOfCourses) {
            int randomIndex = random.nextInt(courses.size());

            Course course = courses.get(randomIndex);

            if (assignedCourseIds.add(course.getId())) {
                studentDao.addToCourse(student.getId(), course.getId());
            }
        }
    }

}