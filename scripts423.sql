select student.name,student.age, student.faculty_id from student
left join faculty on faculty.id = student.faculty_id