import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BodyMassIndexTest {
    private final BodyMassIndex BMI = new BodyMassIndex(1,1);
    //First batch verifies the index method.
    @Test
    void testIndex() {
        assertEquals(27.1, Math.round(BMI.FindIndex(72, 200)*10.0)/10.0);
    }
    //Second batch tests each category, and verifies the category method.
    @Test
    void testUnderWeight(){
        assertEquals("underweight.", BMI.FindCategory(13.6));
    }
    @Test
    void testNormalWeight() {
        assertEquals("normal.", BMI.FindCategory(20.3));
    }
    @Test
    void testOverWeight() {
        assertEquals("overweight.", BMI.FindCategory(27.1));
    }
    @Test
    void testObeseWeight() {
        assertEquals("obese.", BMI.FindCategory(40.3));
    }
    //Third batch tests the boss method.
    @Test
    void testBoss(){
        BodyMassIndex bmi = new BodyMassIndex(72, 200);
        assertEquals(27.1,Math.round(bmi.index*10.0)/10.0);
        assertEquals("overweight.",bmi.category);
    }
}