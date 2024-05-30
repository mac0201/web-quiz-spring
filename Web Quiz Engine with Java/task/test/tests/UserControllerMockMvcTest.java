package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import engine.controller.UserController;
import engine.model.dto.UserRegistrationDTO;
import engine.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class})
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Test
    public void test() throws Exception {
        var dto = new UserRegistrationDTO("user1@gmail.com", "password1");


        String dtoString = jacksonObjectMapper.writeValueAsString(dto);

        System.out.println(dtoString);

        Mockito.doNothing().when(userService).register(dto);

        var request = post("/api/register", dtoString);

        mockMvc.perform(request);
    }

}
