package com.kyrylocodefirst.e2etest.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyrylocodefirst.adapters.api.dto.PriceDTO;
import com.kyrylocodefirst.adapters.api.dto.PricePvpDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest()
public class PricesSteps {

    @Autowired
    private ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<String> response;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Given("the following prices exist")
    public void the_following_prices_exist(DataTable dataTable) {
        List<Map<String, String>> pricesList = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> priceData : pricesList) {
            PriceDTO priceDTO = new PriceDTO();
            priceDTO.setBrandId(Integer.valueOf(priceData.get("brandId")));
            priceDTO.setStartDate(LocalDateTime.parse(priceData.get("startDate")));
            priceDTO.setEndDate(LocalDateTime.parse(priceData.get("endDate")));
            priceDTO.setPriceList(Integer.parseInt(priceData.get("priceList")));
            priceDTO.setProductId(Integer.parseInt(priceData.get("productId")));
            priceDTO.setPriority(Integer.parseInt(priceData.get("priority")));
            priceDTO.setPrice(new BigDecimal(priceData.get("price")));
            priceDTO.setCurrency(priceData.get("currency"));
            priceDTO.setCreationDate(LocalDateTime.now());
            priceDTO.setCreationUser(priceData.get("creationUser"));
            priceDTO.setLastUpdateDate(LocalDateTime.now());
            priceDTO.setLastUpdateUser(priceData.get("lastUpdateUser"));
            restTemplate.postForEntity("http://localhost:8080/prices", priceDTO, PriceDTO.class);
        }
    }

    @When("I add a new price with the following details")
    public void i_add_a_new_price_with_the_following_details(DataTable dataTable) throws Exception {

        response = restTemplate.postForEntity("http://localhost:8080/prices", getRequest(dataTable), String.class);
    }

    @When("I update the price with ID {int} with the following details")
    public void i_update_the_price_with_id_with_the_following_details(int id, DataTable dataTable) throws Exception {

        response = restTemplate.exchange("http://localhost:8080/prices/" + id, HttpMethod.PUT, getRequest(dataTable), String.class);
    }

    @When("I get the price by ID {int}")
    public void i_get_the_price_by_id(int id) {
        response = restTemplate.getForEntity("http://localhost:8080/prices/" + id, String.class);
    }

    @When("I delete the price with ID {int}")
    public void i_delete_the_price_with_id(int id) {
        restTemplate.delete("http://localhost:8080/prices/" + id);
        response = ResponseEntity.ok().build();
    }

    @When("I request the PVP price for brandId {long}, productId {long} and applicationDate {string}")
    public void i_request_the_pvp_price_06_14_10_00(long brandId, long productId, String applicationDate) {
        response = restTemplate.getForEntity(
            "http://localhost:8080/prices/getPvpPrice?brandId=" + brandId + "&productId=" + productId + "&applicationDate=" + applicationDate,
            String.class);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }

    @Then("the response should contain the new price with the following details")
    @Then("the response should contain the price with the following details")
    @Then("the response should contain the updated price with the following details")
    public void the_response_should_contain_the_price_with_the_following_details(DataTable dataTable) throws Exception {
        Map<String, String> expectedPriceData = dataTable.asMaps(String.class, String.class).get(0);
        PriceDTO expectedPriceDTO = new PriceDTO();
        expectedPriceDTO.setBrandId(Integer.parseInt(expectedPriceData.get("brandId")));
        expectedPriceDTO.setStartDate(LocalDateTime.parse(expectedPriceData.get("startDate")));
        expectedPriceDTO.setEndDate(LocalDateTime.parse(expectedPriceData.get("endDate")));
        expectedPriceDTO.setPriceList(Integer.parseInt(expectedPriceData.get("priceList")));
        expectedPriceDTO.setProductId(Integer.parseInt(expectedPriceData.get("productId")));
        expectedPriceDTO.setPriority(Integer.parseInt(expectedPriceData.get("priority")));
        expectedPriceDTO.setPrice(new BigDecimal(expectedPriceData.get("price")));
        expectedPriceDTO.setCurrency(expectedPriceData.get("currency"));
        expectedPriceDTO.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        expectedPriceDTO.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        expectedPriceDTO.setCreationUser(expectedPriceData.get("creationUser"));
        expectedPriceDTO.setLastUpdateUser(expectedPriceData.get("lastUpdateUser"));
        PriceDTO actualPriceDTO = objectMapper.readValue(response.getBody(), PriceDTO.class);

        assertThat(actualPriceDTO).isEqualToIgnoringGivenFields(expectedPriceDTO, "creationDate", "lastUpdateDate");
    }

    @Then("the response should contain the pvp price: 35:50 and following details")
    public void the_response_should_contain_the_pvp_price_35_50(DataTable dataTable) throws Exception {
        PricePvpDTO expectedPriceDTO = getPricePvpDTO(dataTable);
        PricePvpDTO actualPriceDTO = objectMapper.readValue(response.getBody(), PricePvpDTO.class);

        assertThat(actualPriceDTO).isEqualToComparingFieldByField(expectedPriceDTO);
    }

    @Then("the response should contain the pvp price: 25:45 and following details")
    public void the_response_should_contain_the_pvp_price_25_45(DataTable dataTable) throws Exception {
        PricePvpDTO expectedPriceDTO = getPricePvpDTO(dataTable);
        PricePvpDTO actualPriceDTO = objectMapper.readValue(response.getBody(), PricePvpDTO.class);

        assertThat(actualPriceDTO).isEqualToComparingFieldByField(expectedPriceDTO);
    }

    @Then("the response should contain the pvp price: 30:50 and following details")
    public void the_response_should_contain_the_pvp_price_30_50(DataTable dataTable) throws Exception {
        PricePvpDTO expectedPriceDTO = getPricePvpDTO(dataTable);
        PricePvpDTO actualPriceDTO = objectMapper.readValue(response.getBody(), PricePvpDTO.class);

        assertThat(actualPriceDTO).isEqualToComparingFieldByField(expectedPriceDTO);
    }

    @Then("the response should contain the pvp price: 38:95 and following details")
    public void the_response_should_contain_the_pvp_price_38_95(DataTable dataTable) throws Exception {
        PricePvpDTO expectedPriceDTO = getPricePvpDTO(dataTable);
        PricePvpDTO actualPriceDTO = objectMapper.readValue(response.getBody(), PricePvpDTO.class);

        assertThat(actualPriceDTO).isEqualToComparingFieldByField(expectedPriceDTO);
    }

    private HttpEntity<String> getRequest(DataTable dataTable) throws JsonProcessingException {
        Map<String, String> priceData = dataTable.asMaps(String.class, String.class).get(0);
        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setBrandId(Integer.valueOf(priceData.get("brandId")));
        priceDTO.setStartDate(LocalDateTime.parse(priceData.get("startDate")));
        priceDTO.setEndDate(LocalDateTime.parse(priceData.get("endDate")));
        priceDTO.setPriceList(Integer.parseInt(priceData.get("priceList")));
        priceDTO.setProductId(Integer.parseInt(priceData.get("productId")));
        priceDTO.setPriority(Integer.parseInt(priceData.get("priority")));
        priceDTO.setPrice(new BigDecimal(priceData.get("price")));
        priceDTO.setCurrency(priceData.get("currency"));
        priceDTO.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        priceDTO.setCreationUser(priceData.get("creationUser"));
        priceDTO.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        priceDTO.setLastUpdateUser(priceData.get("lastUpdateUser"));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return new HttpEntity<>(objectMapper.writeValueAsString(priceDTO), headers);
    }

    private static PricePvpDTO getPricePvpDTO(DataTable dataTable) {
        Map<String, String> expectedPricePvpData = dataTable.asMaps(String.class, String.class).get(0);
        PricePvpDTO expectedPriceDTO = new PricePvpDTO();
        expectedPriceDTO.setBrandId(Integer.parseInt(expectedPricePvpData.get("brandId")));
        expectedPriceDTO.setPriceList(Integer.parseInt(expectedPricePvpData.get("priceList")));
        expectedPriceDTO.setProductId(Integer.parseInt(expectedPricePvpData.get("productId")));
        expectedPriceDTO.setPrice(new BigDecimal(expectedPricePvpData.get("price")));
        expectedPriceDTO.setCurrency(expectedPricePvpData.get("currency"));
        expectedPriceDTO.setApplicationDate(LocalDateTime.parse(expectedPricePvpData.get("applicationDate")));
        return expectedPriceDTO;
    }
}

