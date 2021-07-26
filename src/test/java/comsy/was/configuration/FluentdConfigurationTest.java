package comsy.was.configuration;

import org.junit.jupiter.api.Test;
import org.komamitsu.fluency.Fluency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FluentdConfigurationTest {

    @Autowired
    private Fluency fluency;

    @Autowired
    private Fluency dwFluency;

    @Test
    public void fluencyTest() throws Exception {
        //given

        //when
        System.out.println(fluency.toString());
        System.out.println(dwFluency.toString());

        //then
        assertNotEquals(fluency, dwFluency);
    }
}