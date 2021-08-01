import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Runner {
    public static void main(String[] args) throws IOException {
        Baseline baseline = new Baseline();
        SIS student = new SIS();

        int homeworkGrade = 0;
        double baseGrade1, baseGrade2, studentGrade1, studentGrade2;
        String baseString, studentString;

        // getGrade()
        baseGrade1 = baseline.getGrade(8537473, 5710492, 20112);
        studentGrade1 = student.getGrade(8537473, 5710492, 20112);
        if(baseGrade1 == studentGrade1) homeworkGrade += 15;
        else System.err.println("getGrade(): " + baseGrade1 + "\t" + studentGrade1);

        // updateExam()
        baseline.updateExam(8537473, 5710492, "Final", 100);
        student.updateExam(8537473, 5710492, "Final", 100);

        baseGrade1 = baseline.getGrade(8537473, 5710492, 20112);
        studentGrade1 = student.getGrade(8537473, 5710492, 20112);

        baseGrade2 = baseline.getGrade(8537473, 5710492, 20122);
        studentGrade2 = student.getGrade(8537473, 5710492, 20122);

        if (baseGrade1 == studentGrade1 && baseGrade2 == studentGrade2) homeworkGrade += 15;
        else System.err.println("updateExam() previous/recent: " + baseGrade1 + "/" + baseGrade2 + "\t\t" + studentGrade1 + "/" + studentGrade2);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // createTranscript()
        baseline.createTranscript(8537473);
        baseString = baos.toString().trim();
        baos.reset();

        student.createTranscript(8537473);
        studentString = baos.toString().trim();
        baos.reset();

        if (studentString.equals(baseString)) homeworkGrade += 30;
        else {
            System.err.println("***createTranscript()******");
            System.err.println(baseString);
            System.err.println("***************************");
            System.err.println(studentString);
            System.err.println("***createTranscript()******");
        }

        // findCourse()
        baseline.findCourse(5710492);
        baseString = baos.toString().trim();
        baos.reset();

        student.findCourse(5710492);
        studentString = baos.toString().trim();
        baos.reset();

        if (studentString.equals(baseString)) homeworkGrade += 15;
        else {
            System.err.println("***findCourse()*******");
            System.err.println(baseString);
            System.err.println("**********************");
            System.err.println(studentString);
            System.err.println("***findCourse()*******");
        }

        // createHistogram()
        baseline.createHistogram(5710492, 20131);
        baseString = baos.toString().trim();
        baos.reset();

        student.createHistogram(5710492, 20131);
        studentString = baos.toString().trim();
        baos.reset();

        if (studentString.equals(baseString)) homeworkGrade += 15;
        else {
            System.err.println("***createHistogram()******");
            System.err.println(baseString);
            System.err.println("**************************");
            System.err.println(studentString);
            System.err.println("***createHistogram()******");
        }

        int[] commentLoopCounter = {0, 0};
        List<String> lines = Files.readAllLines(Paths.get("src" + File.separator + "SIS.java"));
        lines.forEach(s -> {
            if (s.contains("//")) commentLoopCounter[0]++;
            if (s.contains("for(") || s.contains("while(")) commentLoopCounter[1]++;
        });
        if (commentLoopCounter[0] > 0) homeworkGrade += 10;
        if (commentLoopCounter[1] > 0) homeworkGrade -= 50;

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.out.println("# of Comments/Loops = " + commentLoopCounter[0] + "/" + commentLoopCounter[1]);
        System.out.println("Homework Grade = " + homeworkGrade);
    }
}