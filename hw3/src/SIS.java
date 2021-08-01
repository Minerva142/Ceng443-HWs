import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SIS {
    private static String fileSep = File.separator;
    private static String lineSep = System.lineSeparator();
    private static String space   = " ";

    private List<Student> studentList = new ArrayList<>();

    public SIS(){ processOptics(); }

    private void processOptics(){
        //get all paths
        try (Stream<Path> paths = Files.list(Paths.get("input"))) {
            paths.forEach( filepath -> {
                    try {
                        // read each line separately
                        List<String> info = Files.lines(filepath).collect(Collectors.toList());
                        List<String> line1 = Arrays.asList(info.get(0).split(space).clone());
                        List<String> line2 = Arrays.asList(info.get(1).split(space).clone());
                        List<String> line3 = Arrays.asList(info.get(2).split(space).clone());
                        List<String> line4 = Arrays.asList(info.get(3).split(space).clone());
                        // assign numbers to studentsList::studendIDs
                        List<Integer> numbers = studentList.stream()
                                                       .map(Student::getStudentID)
                                                       .collect(Collectors.toList());
                        //line 1 calculations
                        int len1 = line1.size();
                        String Surname = line1.get(len1-2);
                        Integer ID = Integer.parseInt(line1.get(len1-1));
                        String[] name = info.stream().limit(len1-2).toArray(String[]::new);
                        //line 2 calculations
                        Integer course_year = Integer.parseInt(line2.get(0));
                        Integer course_code = Integer.parseInt(line2.get(1));
                        Integer course_credit = Integer.parseInt(line2.get(2));
                        //line 3 calculations
                        String exam_type = line3.get(0);
                        // for calculating grade ** line 4 calculations
                        List<Character> notlar = line4.get(0).chars().mapToObj(e -> (char) e).collect(Collectors.toList());
                        // total number of questions
                        int len4 = notlar.size();
                        // points of one question
                        double point_perq = 100/len4;
                        //check number of correct questions
                        List<Character> points = notlar.stream().filter(character -> Character.valueOf(character).compareTo(Character.valueOf('T'))==0).collect(Collectors.toList());
                        int len_correct = points.size();
                        //total grade
                        double grade = len_correct*point_perq;
                        // add new Student or new Course to Student
                        if(numbers.contains(ID) == false){
                            Student new_s = new Student(name,Surname,ID);
                            Course new_c = new Course(course_code,course_year,exam_type,course_credit,grade);
                            new_s.getTakenCourses().add(new_c);
                            studentList.add(new_s);
                        }
                        else{
                            studentList.stream().forEach(student -> {
                                if (student.getStudentID()==ID){
                                    Course new_c = new Course(course_code,course_year,exam_type,course_credit,grade);
                                    student.getTakenCourses().add(new_c);
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            });
        } catch (IOException e) {
            //catch message
            System.out.println("beceremedik");
            e.printStackTrace();
        }
    }

    public double getGrade(int studentID, int courseCode, int year){
        //firstly find the correct Student with given studenID
        Student wanted= studentList.stream().filter(Student -> Student.getStudentID()==studentID).findFirst().orElse(null);
        // Filter the taken courses with courseCode and year
        List<Course> notlar = wanted.getTakenCourses().stream().filter(course -> course.getCourseCode()==courseCode).filter(course -> course.getYear()==year).collect(Collectors.toList());
        //then calculate the total course grade
        double total_grade = notlar.get(0).getGrade()*0.25+notlar.get(1).getGrade()*0.25+notlar.get(2).getGrade()*0.5;
        return total_grade;
    }

    public void updateExam(int studentID, int courseCode, String examType, double newGrade){
        //Find the Student with given ID
        Student wanted = studentList.stream().filter(Student -> Student.getStudentID()==studentID).findFirst().orElse(null);
        //filter the taken courses with given courseCode and exampType
        List<Course> crs = wanted.getTakenCourses().stream().filter(course ->course.getCourseCode()==courseCode).filter(course -> course.getExamType().equals(examType)).collect(Collectors.toList());
        //find the latest one, then change it's grade
        Course newest_crs = crs.stream().max(Comparator.comparing(Course::getYear)).get();
        newest_crs.setGrade(newGrade);
    }

    public void createTranscript(int studentID){
        //Find the Student with given studentID
        List<Student> wanted = studentList.stream().filter(student ->
            student.getStudentID()==studentID
        ).collect(Collectors.toList());
        Student tmp = wanted.get(0);
        //List the years which are the Student at least take one course
        List<Integer> years = new ArrayList<>();
        tmp.getTakenCourses().stream()
                .forEach(Course ->{
                    if(!years.contains(Course.getYear())){
                        years.add(Course.getYear());
                    }
                });
        //sorting it
        List<Integer> s_years = years.stream().sorted().collect(Collectors.toList());
        // letter and point grades
        String[] grades = {"AA","BA","BB","CB","CC","DC","DD","FD","FF"};
        double[] cpas = {4.0,3.5,3.0,2.5,2.0,1.5,1.0,0.5,0.0};
        List<Integer> tam_liste = new ArrayList<>();
        //alınan total kredi(tekrar alınan dersler 1 kere sayılan hali)
        int total_credit=tmp.getTakenCourses().stream()
                .mapToInt(course -> {
                    if(!tam_liste.contains(course.getCourseCode())){
                        tam_liste.add(course.getCourseCode());
                        return course.getCredit();
                    }
                    else{
                       return 0;
                    }
                }).sum();
        //for each year, find the taken coures then calculate the letter grades and print them
        s_years.stream()
                .forEach(year ->{
                    System.out.println(year);
                    List<Integer> kurslar = new ArrayList<>();
                    tmp.getTakenCourses()
                            .stream()
                            .forEach(Course ->{
                                if(Course.getYear()==year){
                                    if(!kurslar.contains(Course.getCourseCode())){
                                        kurslar.add(Course.getCourseCode());
                                    }
                                }
                            });
                    List<Integer> s_kurslar = kurslar.stream().sorted().collect(Collectors.toList());
                    //for all course print letter grades, according to total grade
                    s_kurslar.stream()
                            .forEach(courseID ->{
                                double grade = this.getGrade(studentID,courseID,year);
                                if(grade >= 89.5)
                                    System.out.println(courseID + space + grades[0]);
                                if(grade < 89.5 && grade >= 84.5)
                                    System.out.println(courseID + space + grades[1]);
                                if(grade >= 79.5 && grade <84.5)
                                    System.out.println(courseID + space + grades[2]);
                                if(grade >= 74.5 && grade <79.5)
                                    System.out.println(courseID + space + grades[3]);
                                if(grade >= 69.5 && grade <74.5)
                                    System.out.println(courseID + space + grades[4]);
                                if(grade >= 64.5 && grade <69.5)
                                    System.out.println(courseID + space + grades[5]);
                                if(grade >= 59.5 && grade <64.5)
                                    System.out.println(courseID + space + grades[6]);
                                if(grade >= 49.5 && grade <59.5)
                                    System.out.println(courseID + space + grades[7]);
                                if( grade <49.5)
                                    System.out.println(courseID + space + grades[8]);
                            });
                });
        //reverse the years list for calculating the cpga
        Collections.reverse(s_years);
        //hash map for all taken courses, key is coursecode, value is course credit
        HashMap<Integer,Integer> credits = new HashMap<>();
        tmp.getTakenCourses().stream()
                .forEach(course -> {
                    if(!credits.containsKey(course.getCourseCode())){
                        credits.put(course.getCourseCode(),course.getCredit());
                    }
                });
        // For storing all courses and this list helps us to decide it is in the cpga or not
        List<Integer> tam_liste2 = new ArrayList<>();
        // for store the each courses effect on the total cpga
        List<Double> cpga = new ArrayList<>();

        //for calculating and storing the each courses effect on the cpga(store it in cpga)
        s_years.stream()
                .forEach(year ->{
                    List<Integer> kurslar = new ArrayList<>();
                    tmp.getTakenCourses()
                            .stream()
                            .forEach(Course ->{
                                if(Course.getYear()==year){
                                    //check if it is already in the list of courses then add
                                    if(!tam_liste2.contains(Course.getCourseCode())){
                                        kurslar.add(Course.getCourseCode());
                                        tam_liste2.add(Course.getCourseCode());
                                    }
                                }
                            });
                    List<Integer> s_kurslar = kurslar.stream().sorted().collect(Collectors.toList());
                    //calculate the effects on total course each course separately
                    s_kurslar.stream()
                            .forEach(courseID -> {
                                double grade = this.getGrade(studentID,courseID,year);
                                if(grade >= 89.5)
                                    cpga.add((credits.get(courseID)*4.0)/total_credit);
                                if(grade < 89.5 && grade >= 84.5)
                                    cpga.add((credits.get(courseID)*3.5)/total_credit);
                                if(grade >= 79.5 && grade <84.5)
                                    cpga.add((credits.get(courseID)*3.0)/total_credit);
                                if(grade >= 74.5 && grade <79.5)
                                    cpga.add((credits.get(courseID)*2.5)/total_credit);
                                if(grade >= 69.5 && grade <74.5)
                                    cpga.add((credits.get(courseID)*2.0)/total_credit);
                                if(grade >= 64.5 && grade <69.5)
                                    cpga.add((credits.get(courseID)*1.5)/total_credit);
                                if(grade >= 59.5 && grade <64.5)
                                    cpga.add((credits.get(courseID)*1.0)/total_credit);
                                if(grade >= 49.5 && grade <59.5)
                                    cpga.add((credits.get(courseID)*0.5)/total_credit);
                                if( grade <49.5)
                                    cpga.add((credits.get(courseID)*0.0)/total_credit);
                            });
                });
        //calculate the total cpga
        double total_cpga = cpga.stream().mapToDouble(db ->db).sum();
        //print it
        System.out.println("CGPA:" + space + total_cpga );
    }

    public void findCourse(int courseCode){

        //List of all years
        List<Integer> year_of_course = new ArrayList<>();
        //List of List of Course for all students in the studentList
        List<List<Course>> wanted = studentList.stream().map(Student -> Student.getTakenCourses()).collect(Collectors.toList());
        // List the given years of the Course
        wanted.stream().forEach(Course -> Course.stream().forEach(course ->{
            if(course.getCourseCode()==courseCode){
                if(!year_of_course.contains(course.getYear())){
                    year_of_course.add(course.getYear());
                }
            }
        }));
        //sort the list
        List<Integer> year_of_course1 = year_of_course.stream().sorted().collect(Collectors.toList());
        //For each year calculate the total number of the student for given year,the print it
        year_of_course1.stream().forEach(year ->
        {
            List<Course> tmp = new ArrayList<>();
            wanted.stream().forEach(Courses ->{
                 Courses.stream().filter(course -> course.getYear()==year)
                        .filter(course -> course.getCourseCode()==courseCode)
                        .forEach(course -> {
                            tmp.add(course);
                        });
            });
            System.out.println(year + space + tmp.size()/3);
        });

    }

    public void createHistogram(int courseCode, int year){
        //filter the studentList with courseCode and year(find the Students which took given Course at given year)
        List<Student> getters = studentList.stream().filter( Student -> !Student.getTakenCourses().stream().filter(course -> course.getCourseCode() == courseCode && course.getYear() == year).collect(Collectors.toList()).isEmpty()).collect(Collectors.toList());
        List<Double> notlar = getters.stream().map(Student -> getGrade(Student.getStudentID(),courseCode,year)).collect(Collectors.toList());
        List<Integer> numbers = new ArrayList<>();
        // I know ıts ugly but correct
        // filter according to all conditions and count the number of Students then add it to the numbers list
        numbers.add((int) notlar.stream().filter(x -> x >=0 && x<10).count());
        numbers.add((int) notlar.stream().filter(x -> x >=10 && x<20).count());
        numbers.add((int) notlar.stream().filter(x -> x >=20 && x<30).count());
        numbers.add((int) notlar.stream().filter(x -> x >=30 && x<40).count());
        numbers.add((int) notlar.stream().filter(x -> x >=40 && x<50).count());
        numbers.add((int) notlar.stream().filter(x -> x >=50 && x<60).count());
        numbers.add((int) notlar.stream().filter(x -> x >=60 && x<70).count());
        numbers.add((int) notlar.stream().filter(x -> x >=70 && x<80).count());
        numbers.add((int) notlar.stream().filter(x -> x >=80 && x<90).count());
        numbers.add((int) notlar.stream().filter(x -> x >=90 && x<=100).count());
        //print the numbers in ascending order
        System.out.println("0-10" + space + numbers.get(0));
        System.out.println("10-20" + space + numbers.get(1));
        System.out.println("20-30" + space + numbers.get(2));
        System.out.println("30-40" + space + numbers.get(3));
        System.out.println("40-50" + space + numbers.get(4));
        System.out.println("50-60" + space + numbers.get(5));
        System.out.println("60-70" + space + numbers.get(6));
        System.out.println("70-80" + space + numbers.get(7));
        System.out.println("80-90" + space + numbers.get(8));
        System.out.println("90-100" + space + numbers.get(9));
    }
}

