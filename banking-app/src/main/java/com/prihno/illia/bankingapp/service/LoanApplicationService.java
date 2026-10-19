package com.prihno.illia.bankingapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prihno.illia.bankingapp.dto.LoanApplicationRequestDTO;
import com.prihno.illia.bankingapp.dto.LoanApplicationResponseDTO;
import com.prihno.illia.bankingapp.entity.loan.LoanApplication;
import com.prihno.illia.bankingapp.entity.loan.LoanStatus;
import com.prihno.illia.bankingapp.repository.LoanApplicationRepository;
import com.prihno.illia.bankingapp.repository.TransactionRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LoanApplicationService {
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    public LoanApplication save(LoanApplication loanApplication) {
        return loanApplicationRepository.save(loanApplication);
    }

    public List<LoanApplication> findAllByUserEmail(String userEmail) {
//        return loanApplicationRepository.findByToAccountUserEmail(userEmail);
        return loanApplicationRepository.findAll();
    }

    public LoanStatus sendLoanApplicationRequest(LoanApplicationRequestDTO loanApplicationRequestDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String json = mapper.writeValueAsString(loanApplicationRequestDTO);
        System.out.println(json);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://loan-processor:8888/loan_application";
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

        LoanApplicationResponseDTO statusResponse = mapper.readValue(response.getBody(), LoanApplicationResponseDTO.class);
        return statusResponse.getLoanStatus();
    }
}
