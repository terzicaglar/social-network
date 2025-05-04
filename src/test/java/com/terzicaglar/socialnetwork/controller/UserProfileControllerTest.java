package com.terzicaglar.socialnetwork.controller;

import com.terzicaglar.socialnetwork.exception.GlobalExceptionHandler;
import com.terzicaglar.socialnetwork.exception.UserProfileNotFoundException;
import com.terzicaglar.socialnetwork.model.UserProfileDto;
import com.terzicaglar.socialnetwork.service.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserProfileControllerTest {

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private UserProfileController userProfileController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getUserProfileById_WhenUserExists_ShouldReturnUserProfile() throws Exception {
        Long userId = 1L;
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(userId);
        userProfileDto.setFirstName("Test");
        userProfileDto.setLastName("User");
        userProfileDto.setEmail("test@example.com");

        when(userProfileService.getUserProfileById(anyLong())).thenReturn(userProfileDto);

        mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getUserProfileById_WhenUserDoesNotExist_ShouldReturnNotFound() throws Exception {
        Long userId = 1L;
        when(userProfileService.getUserProfileById(anyLong())).thenThrow(new UserProfileNotFoundException("User profile not found with id: " + userId));


        mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void batchInsertUsers_WithValidUsers_ShouldReturnSuccess() throws Exception {
        // Given
        String requestBody = """
                [
                  {
                    "firstName": "Klara",
                    "lastName": "Becker",
                    "email": "klara@example.com",
                    "dateOfBirth": "1996-02-10",
                    "userDefinedFields": "{\\"interests\\": [\\"art\\", \\"travel\\"], \\"blog\\": \\"klarawanders.com\\"}"
                  },
                  {
                    "firstName": "Lukas",
                    "lastName": "Brandt",
                    "email": "lukas@example.com",
                    "dateOfBirth": "1989-10-21",
                    "userDefinedFields": "{\\"favorite_movie\\": \\"Inception\\", \\"gaming_handle\\": \\"lukeb\\"}"
                  },
                  {
                    "firstName": "Mira",
                    "lastName": "Neumann",
                    "email": "mira@example.com",
                    "dateOfBirth": "1992-05-05",
                    "userDefinedFields": "{\\"diet\\": \\"vegetarian\\", \\"languages\\": [\\"English\\", \\"German\\"]}"
                  }
                ]
                """;

        mockMvc.perform(post("/user/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Users inserted successfully"));
    }

    @Test
    void batchInsertUsers_WithInvalidUsers_ShouldReturnBadRequest() throws Exception {
        // Given
        String invalidRequestBody = """
                [
                  {
                    "firstName": "Klara",
                    "lastName": "Becker",
                    "email": "invalid email",
                    "dateOfBirth": "1996-02-10",
                    "userDefinedFields": "{\\"interests\\": [\\"art\\", \\"travel\\"], \\"blog\\": \\"klarawanders.com\\"}"
                  }
                ]
                """;

        mockMvc.perform(post("/user/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }
} 