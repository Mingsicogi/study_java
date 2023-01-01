package minssogi.study.resilience4j.bulkHead;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class BulkheadTestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void bulkHead() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    ResultActions resultActions = mockMvc.perform(get("/bulkHead"));
                    MockHttpServletResponse response = resultActions.andReturn().getResponse();
                    System.out.println(response.getStatus() + " : " + response.getContentAsString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.awaitTermination(2, TimeUnit.SECONDS);
    }
}