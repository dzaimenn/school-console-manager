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
    private static final int NUM_UNIQUE_FIRST_NAMES = 20;
    private static final int NUM_UNIQUE_LAST_NAMES = 20;
    private static final int NUM_UNIQUE_STUDENTS = 200;
    private static final int NUM_UNIQUE_GROUPS = 10;
    private static final int NUM_UNIQUE_COURSES = 10;

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
        List<Group> groups = generateGroups();
        List<Student> students = generateStudents(groups);
        List<Course> courses = generateCourses();
        generateStudentsToCourses(students, courses);

        System.out.println("Database filled successfully");
    }

    private List<Group> generateGroups() {
        List<Group> groups = new ArrayList<>();

        for (int i = 0; i < NUM_UNIQUE_GROUPS; i++) {
            String groupName = SchoolData.groupsNames[i];

            Group group = new Group();
            group.setName(groupName);

            groupDao.create(group);
            groups.add(group);
        }
        return groups;
    }

    private Set<String> generateUniqueStudentsSet() {
        Set<String> uniqueStudents = new HashSet<>();

        while (uniqueStudents.size() < NUM_UNIQUE_STUDENTS) {
            String firstName = SchoolData.firstNamesArray[random.nextInt(NUM_UNIQUE_FIRST_NAMES)];
            String lastName = SchoolData.lastNamesArray[random.nextInt(NUM_UNIQUE_LAST_NAMES)];

            String uniqueKey = firstName + " " + lastName;

            uniqueStudents.add(uniqueKey);
        }

        return uniqueStudents;
    }

    private List<Student> generateStudents(List<Group> groups) {
        List<Student> students = new ArrayList<>();
        Set<String> uniqueStudents = generateUniqueStudentsSet();

        for (String uniqueKey : uniqueStudents) {
            Integer groupId = groups.get(random.nextInt(NUM_UNIQUE_GROUPS)).getId();

            String[] nameParts = uniqueKey.split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts[1];

            Student student = new Student();
            student.setGroupId(groupId);
            student.setFirstName(firstName);
            student.setLastName(lastName);

            studentDao.create(student);
            students.add(student);
        }
        return students;
    }

    private List<Course> generateCourses() {
        List<Course> courses = new ArrayList<>();

        for (int i = 0; i < NUM_UNIQUE_COURSES; i++) {
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

    private void generateStudentsToCourses(List<Student> students, List<Course> courses) {
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