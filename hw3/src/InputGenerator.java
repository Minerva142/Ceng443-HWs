import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

public class InputGenerator {
    private static String fileSep = File.separator;
    private static String lineSep = System.lineSeparator();
    private static String space   = " ";

    private Random random = new Random(1234);

    private List<String> studentList = new ArrayList<>();
    private List<String> courseList = new ArrayList<>();

    private String[] yearArray = IntStream.rangeClosed(2010, 2013).mapToObj(String::valueOf).toArray(String[]::new);
    private String[] answerArray = new String[]{"T", "T", "T", "T", "T", "T", "F", "E"};

    public InputGenerator() throws IOException {
        List<String> tmpNames = Files.readAllLines(Paths.get("generator" + fileSep + "names.txt"));
        List<String> tmpSurnames = Files.readAllLines(Paths.get("generator" + fileSep + "surnames.txt"));

        String name, surname, id;
        Map<String, String> idFirstLine = new HashMap<>();

        int numStudents = 50;
        for(int i = 0; i < numStudents; i++) {
            id = createID();
            if (idFirstLine.containsKey(id)) { i--; continue; }

            name = tmpNames.get(random.nextInt(tmpNames.size()));
            surname = tmpSurnames.get(random.nextInt(tmpSurnames.size()));
            idFirstLine.put(id, name + space + surname + space + id);
            studentList.add(idFirstLine.get(id));
        }

        String courseName;
        courseName = "2300105";     courseList.add(courseName + space + 4);
        courseName = "2380109";     courseList.add(courseName + space + 3);
        courseName = "2360119";     courseList.add(courseName + space + 5);
        courseName = "2300106";     courseList.add(courseName + space + 4);
        courseName = "2360120";     courseList.add(courseName + space + 5);
        courseName = "2360260";     courseList.add(courseName + space + 3);
        courseName = "2360219";     courseList.add(courseName + space + 4);
        courseName = "5670281";     courseList.add(courseName + space + 3);
        courseName = "5710213";     courseList.add(courseName + space + 4);
        courseName = "5710222";     courseList.add(courseName + space + 3);
        courseName = "5710232";     courseList.add(courseName + space + 4);
        courseName = "5710242";     courseList.add(courseName + space + 4);
        courseName = "5710315";     courseList.add(courseName + space + 3);
        courseName = "5710331";     courseList.add(courseName + space + 3);
        courseName = "5710334";     courseList.add(courseName + space + 3);
        courseName = "5710336";     courseList.add(courseName + space + 3);
        courseName = "5710350";     courseList.add(courseName + space + 3);
        courseName = "5710435";     courseList.add(courseName + space + 3);
        courseName = "5710477";     courseList.add(courseName + space + 3);
        courseName = "5710492";     courseList.add(courseName + space + 4);
    }

    public static void main(String[] args) throws IOException {
        File inputFolder = new File("input");
        if (inputFolder.exists()) for (File f : inputFolder.listFiles()) f.delete();
        else inputFolder.mkdir();

        InputGenerator obj = new InputGenerator();
        File file;
        FileWriter writer;

        String firstLine, secondLine, combined;
        Map<String, Boolean> repeatMap = new HashMap<>();
        String[] examTypeArray = new String[]{"Midterm1", "Midterm2", "Final"};

        int currentForm = 1, numGrades = 500;
        for(int i = 1; i <= numGrades; i++) {
            firstLine = obj.createFirstLine();
            secondLine = obj.createYear() + space + obj.createCourse();
            combined = firstLine + space + secondLine;
            if (repeatMap.containsKey(combined)) { i--; continue; }
            repeatMap.put(combined, true);

            for(int k = 0; k < 3; k++) {
                file = new File("input" + fileSep + currentForm + ".txt");
                file.createNewFile();
                writer = new FileWriter(file);

                writer.write(firstLine + lineSep);
                writer.write(secondLine + lineSep);
                writer.write(examTypeArray[k] + lineSep);
                writer.write(obj.createAnswers());

                writer.close();
                currentForm++;
            }
        }
    }

    public String createFirstLine() { return studentList.get(random.nextInt(studentList.size())); }
    public String createCourse() { return courseList.get(random.nextInt(courseList.size())); }
    public String createID() {
        StringBuilder id = new StringBuilder();
        id.append(random.nextInt(9) + 1);
        for(int i = 0; i < 6; i++) id.append(random.nextInt(10));
        return id.toString();
    }
    public String createYear() { return yearArray[random.nextInt(yearArray.length)] + (random.nextInt(2) + 1); }
    public String createAnswers() {
        StringBuilder answers = new StringBuilder();
        int numQuestions = 25;
        for(int i = 0; i < numQuestions; i++) answers.append(answerArray[random.nextInt(answerArray.length)]);
        return answers.toString();
    }
}
