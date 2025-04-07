package pt.ua.tqs.mealbooking.controller;

import pt.ua.tqs.mealbooking.model.Restaurant;
import pt.ua.tqs.mealbooking.repository.RestaurantRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @Test
    public void testGetAllRestaurants() throws Exception {
        Restaurant r1 = new Restaurant("Cantina Central", "Campus Norte");
        Restaurant r2 = new Restaurant("Cantina Sul", "Campus Sul");

        when(restaurantRepository.findAll()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/api/restaurants"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Cantina Central"))
            .andExpect(jsonPath("$[1].location").value("Campus Sul"));
    }
}
