package minssogi.study.rate_limiting.redis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("race condition 문제가 있는 rate limiting 테스트")
    void wrong_rate_limiting_test() throws Exception {
        //give
        String id = "test";
        String password = "test";

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/redis/reteLimiting/wrong/race_condition/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", id)
                        .param("password", password))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(id + " Login success"));

        mockMvc.perform(MockMvcRequestBuilders.post("/redis/reteLimiting/wrong/race_condition/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", id)
                        .param("password", password))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(id + " Login success"));

        Thread.sleep(100);

        mockMvc.perform(MockMvcRequestBuilders.post("/redis/reteLimiting/wrong/race_condition/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", id)
                        .param("password", password))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    @DisplayName("race condition 문제가 없는 rate limiting 테스트")
    void correct_rate_limiting_test() throws Exception {
        //give
        String id = "test";
        String password = "test";

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/redis/reteLimiting/correct/race_condition/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", id)
                        .param("password", password))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(id + " Login success"));

        mockMvc.perform(MockMvcRequestBuilders.post("/redis/reteLimiting/correct/race_condition/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", id)
                        .param("password", password))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(id + " Login success"));

        Thread.sleep(100);

        mockMvc.perform(MockMvcRequestBuilders.post("/redis/reteLimiting/correct/race_condition/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", id)
                        .param("password", password))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}