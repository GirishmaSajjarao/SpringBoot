package com.girishma22bce8271.bfhjava.runner;

import com.girishma22bce8271.bfhjava.dto.GenerateWebhookRequest;
import com.girishma22bce8271.bfhjava.dto.GenerateWebhookResponse;
import com.girishma22bce8271.bfhjava.dto.SubmitQueryRequest;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StartupRunner implements ApplicationRunner {

    private final RestTemplate restTemplate;

    // Your details
    private final String NAME = "Girishma";
    private final String REG_NO = "22BCE8271";
    private final String EMAIL = "your.email@example.com";

    public StartupRunner(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            // Step 1: Generate webhook
            String generateUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
            GenerateWebhookRequest req = new GenerateWebhookRequest(NAME, REG_NO, EMAIL);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GenerateWebhookRequest> entity = new HttpEntity<>(req, headers);

            ResponseEntity<GenerateWebhookResponse> resp =
                    restTemplate.postForEntity(generateUrl, entity, GenerateWebhookResponse.class);

            if (resp.getStatusCode() == HttpStatus.OK && resp.getBody() != null) {
                GenerateWebhookResponse body = resp.getBody();
                String accessToken = body.getAccessToken();

                // Step 2: SQL solution (Question 1 since reg no is odd)
                String finalSql = "SELECT p.amount AS SALARY, CONCAT(e.first_name, ' ', e.last_name) AS NAME, "
                        + "TIMESTAMPDIFF(YEAR, e.dob, DATE(p.payment_time)) AS AGE, d.department_name AS DEPARTMENT_NAME "
                        + "FROM payments p JOIN employee e ON p.emp_id = e.emp_id JOIN department d ON e.department = d.department_id "
                        + "WHERE DAY(p.payment_time) <> 1 "
                        + "AND p.amount = (SELECT MAX(amount) FROM payments WHERE DAY(payment_time) <> 1);";

                // Step 3: Submit SQL
                String submitUrl = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

                HttpHeaders submitHeaders = new HttpHeaders();
                submitHeaders.setContentType(MediaType.APPLICATION_JSON);
                submitHeaders.set("Authorization", accessToken);

                HttpEntity<SubmitQueryRequest> submitEntity =
                        new HttpEntity<>(new SubmitQueryRequest(finalSql), submitHeaders);

                ResponseEntity<String> submitResp =
                        restTemplate.postForEntity(submitUrl, submitEntity, String.class);

                System.out.println("Submission status: " + submitResp.getStatusCode());
                System.out.println("Response: " + submitResp.getBody());
            } else {
                System.err.println("Failed to generate webhook: " + resp.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
