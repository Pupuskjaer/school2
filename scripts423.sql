select student.name,student.age, student.faculty_id from student
left join faculty on faculty.id = student.faculty_id;

select * from student
right join  avatar on avatar.student_id = student.id;