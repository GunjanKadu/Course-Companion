# CourseCompanion

Course Companion is an application for collecting peer reviews from the groups of students. Students can be grouped and each student in the group can review other students that belongs to the same group.

Students can see only the reviews they have given. The teacher who owns the group can see all reviews from the group. Superuser can add new teachers.

Application contains some demousers
Students: user1-5/user
Teacher: admin/admin
Superuser: super/super

### Technologies

- Spring Boot
- MariaDB
- Thymeleaf
- Jquery Datatables

### Installation

Install MariaDB and create database called 'CourseCompanion'

Clone repository and run the application (Note! Change MySql password in application.properties file).

    mvnw spring-boot:run

### Screenshots

##### Courses & student groups

![Screenshot](https://github.com/GunjanKadu/Course-Companion/blob/master/img/1.png)

##### Create questions for peer-review

![Screenshot](https://github.com/GunjanKadu/Course-Companion/blob/master/img/2.png)

##### Review entry (Student)

![Screenshot](https://github.com/GunjanKadu/Course-Companion/blob/master/img/3.png)


##### View reviews and export to excel or pdf (Teacher)

![Screenshot](https://github.com/GunjanKadu/Course-Companion/blob/master/img/4.png)

