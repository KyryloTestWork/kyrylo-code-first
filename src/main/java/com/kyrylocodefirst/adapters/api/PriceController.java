package com.kyrylocodefirst.adapters.api;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kyrylocodefirst.adapters.api.dto.PriceDTO;
import com.kyrylocodefirst.adapters.api.dto.PricePvpDTO;
import com.kyrylocodefirst.adapters.api.mapper.PriceToDTOMapper;
import com.kyrylocodefirst.adapters.api.mapper.PriceToPricePvpDTOMapper;
import com.kyrylocodefirst.domain.ports.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Price controller", description = "This controller allows to client to access to Prices")
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService service;
    private final PriceToDTOMapper priceMapper;
    private final PriceToPricePvpDTOMapper priceToPricePvpDTOMapper;

    @Operation(summary = "Get all prices")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping()
    public ResponseEntity<List<PriceDTO>> getPrices() {
        return new ResponseEntity<>(priceMapper.toDtoList(service.getPrices()), HttpStatus.OK);
    }

    @Operation(summary = "Create a new price")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping()
    public ResponseEntity<PriceDTO> createPrice(@Valid @RequestBody final PriceDTO prices) {
        return new ResponseEntity<>(priceMapper.toDto(service.savePrice(priceMapper.toModel(prices))), HttpStatus.CREATED);
    }

    @Operation(summary = "Get price by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Not Found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PriceDTO> getPrice(@PathVariable final Integer id) {
        return new ResponseEntity<>(priceMapper.toDto(service.getPriceById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Update price by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Not Found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PriceDTO> updatePrice(@PathVariable final Integer id, @Valid @RequestBody final PriceDTO prices) {
        return new ResponseEntity<>(priceMapper.toDto((service.updatePrice(id, priceMapper.toModel(prices)))), HttpStatus.OK);
    }

    @Operation(summary = "Delete price by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Not Found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable final Integer id) {
        service.deletePrice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get PVP price")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Not Found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/getPvpPrice")
    public ResponseEntity<PricePvpDTO> getPvpPrice(
        @RequestParam final Integer brandId,
        @RequestParam final Integer productId,
        @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime applicationDate) {
        return new ResponseEntity<>(priceToPricePvpDTOMapper.toDto(service.getPvpPrice(brandId, productId, applicationDate), applicationDate), HttpStatus.OK);
    }
}
