package com.codingstrain.springcloud.sample.libraryapp.books;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(
  stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = "springcloud.sample:springcloud.sample.libraryapp.testing.contract.authors:+:stubs:8091")
public class ConsumerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testConsumer()
      throws Exception {
 
        mockMvc.perform(MockMvcRequestBuilders.get("/library/findAuthor?name=Goethe")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
            .andExpect(content().string("{\"name\":\"Goethe\",\"biography\":\"Bio info\"}"));
    }
}