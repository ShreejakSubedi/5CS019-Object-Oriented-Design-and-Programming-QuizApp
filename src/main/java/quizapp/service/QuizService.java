package quizapp.service;

import quizapp.model.Level;
import quizapp.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuizService {

    public List<Question> getFiveQuestions(Level level) {
        return switch (level) {
            case BEGINNER -> beginnerQuestions();
            case INTERMEDIATE -> intermediateQuestions();
            case ADVANCED -> advancedQuestions();
        };
    }

    private List<Question> beginnerQuestions() {
        List<Question> qs = new ArrayList<>();

        qs.add(new Question("1) Which keyword creates an object in Java?",
                new String[]{"make", "new", "create", "object"}, 1));

        qs.add(new Question("2) Which type stores whole numbers?",
                new String[]{"String", "int", "double", "boolean"}, 1));

        qs.add(new Question("3) Which symbol ends a Java statement?",
                new String[]{".", ":", ";", ","}, 2));

        qs.add(new Question("4) Which is a loop in Java?",
                new String[]{"if", "switch", "for", "case"}, 2));

        qs.add(new Question("5) Which access modifier means only inside class?",
                new String[]{"public", "private", "protected", "default"}, 1));

        return qs;
    }

    private List<Question> intermediateQuestions() {
        List<Question> qs = new ArrayList<>();

        qs.add(new Question("1) Which OOP concept allows using one interface for many forms?",
                new String[]{"Encapsulation", "Polymorphism", "Inheritance", "Compilation"}, 1));

        qs.add(new Question("2) Which collection does NOT allow duplicates?",
                new String[]{"List", "Set", "ArrayList", "LinkedList"}, 1));

        qs.add(new Question("3) JDBC is mainly used for:",
                new String[]{"GUI design", "Database connectivity", "Game graphics", "File compression"}, 1));

        qs.add(new Question("4) What does 'private' support most directly?",
                new String[]{"Data hiding", "Looping", "Casting", "Sorting"}, 0));

        qs.add(new Question("5) Which is correct to override a method?",
                new String[]{"Same name + same parameters", "Different name", "Same name + different return only", "Same name + different class only"}, 0));

        return qs;
    }

    private List<Question> advancedQuestions() {
        List<Question> qs = new ArrayList<>();

        qs.add(new Question("1) Which statement about abstract classes is true?",
                new String[]{"Cannot have constructors", "Can have both abstract and non-abstract methods", "Cannot have fields", "Cannot be extended"}, 1));

        qs.add(new Question("2) Which JDBC object is used to execute parameterized SQL?",
                new String[]{"Statement", "PreparedStatement", "ResultSet", "DriverManager"}, 1));

        qs.add(new Question("3) Which best describes encapsulation?",
                new String[]{"Multiple inheritance", "Hiding data with controlled access", "Running code in parallel", "Sorting arrays"}, 1));

        qs.add(new Question("4) In Maven, dependencies are declared in:",
                new String[]{"build.gradle", "pom.xml", "settings.json", "manifest.mf"}, 1));

        qs.add(new Question("5) Which is a checked exception?",
                new String[]{"NullPointerException", "ArithmeticException", "SQLException", "IndexOutOfBoundsException"}, 2));

        return qs;
    }
}