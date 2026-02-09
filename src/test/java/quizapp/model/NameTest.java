package quizapp.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NameTest {

    @Test
    void initials_withoutMiddleName() {
        Name n = new Name("Shreejak", "", "Subedi");
        assertEquals("SS", n.getInitials());
    }

    @Test
    void initials_withMiddleName() {
        Name n = new Name("Keith", "John", "Talbot");
        assertEquals("KJT", n.getInitials());
    }
}