package org.user.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.user.userservice.entity.User;
import org.user.userservice.exception.ProductOutOfStockException;
import org.user.userservice.exception.UserNotAdminException;
import org.user.userservice.exception.UserNotExistException;
import org.user.userservice.model.*;
import org.user.userservice.repository.LogRepository;
import org.user.userservice.repository.UserRepository;
import org.user.userservice.security.BCrypt;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${apps.product.api}")
    private String PRODUCT_SERVICE_API;

    @Value("${apps.order.api}")
    private String ORDER_SERVICE_API;

    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);



        if (userRepository.existsById(request.getUsername())) {
            saveLog("register", "UserService", null,"Error, Username is already in use");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already in use");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setRole(request.getRole());

        userRepository.save(user);

        saveLog("register", "UserService", user,"User registered successfully");
    }

    public ProductResponse createProduct(User user, CreateProductRequest createProductRequest) {
        validationService.validate(createProductRequest);
        log.info(user.getRole());
        // implementasi saving log to mongoDB
        if("admin".equals(user.getRole())) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CreateProductRequest> request = new HttpEntity<>(createProductRequest, headers);

            ResponseEntity<ProductResponse> response = restTemplate.postForEntity(PRODUCT_SERVICE_API, request, ProductResponse.class);

            saveLog("createProduct", "UserService", response.getBody(), "Product created successfully");
            return response.getBody();
        }else{
            saveLog("createProduct", "UserService", null,"Access Denied: User is not admin");
            throw new UserNotAdminException("Access Denied, User is not admin");
        }

    }

    public OrderResponse createOrder(User user, CreateOrderRequest createOrderRequest) {
        validationService.validate(createOrderRequest);
        if(!userRepository.existsById(user.getUsername())) {
            saveLog("createOrder", "UserService", null, "Error, User not found, cannot make order");
            throw new UserNotExistException("User not Exist, cannot make order");
        }
        // solved. salah PORT
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateOrderRequest> request = new HttpEntity<>(createOrderRequest, headers);

        ResponseEntity<OrderResponse> response = restTemplate.postForEntity(ORDER_SERVICE_API, request, OrderResponse.class);
        saveLog("createOrder", "UserService", response.getBody(),"Order created successfully");
        return response.getBody();
    }

    public ProductResponse addStock(User user, UpdateProductQuantity updateProductQuantity) {

        if(!"admin".equals(user.getRole())) {
            saveLog("addStock", "UserService", null,"Error, Access Denied, User is not admin");
            throw new UserNotAdminException("Access Denied, User is not admin");
        }

        String productServiceURL = PRODUCT_SERVICE_API + "/" + updateProductQuantity.getProductId();

        ProductResponse productResponse = restTemplate.getForObject(productServiceURL, ProductResponse.class);
        if (productResponse != null) {
            restTemplate.put(productServiceURL + "/add-stock", updateProductQuantity, ProductResponse.class);
            ProductResponse updateProductResponse = restTemplate.getForObject(productServiceURL, ProductResponse.class);
            saveLog("addStock", "UserService", updateProductResponse,"Stock added successfully");
            return updateProductResponse;
        } else {
            saveLog("addStock", "UserService", null,"Error, Product Not Found");
            throw new ProductOutOfStockException("Product Not Found");
        }
    }

    public void saveLog(String methodName, String serviceName,Object data, String message) {
        LogRequest logRequest = new LogRequest();
        logRequest.setMethodName(methodName);
        logRequest.setServiceName(serviceName);
        logRequest.setTimeStamp(LocalDateTime.now());

        if(data != null){
            try{
                logRequest.setData(objectMapper.writeValueAsString(data));
            }catch (JsonProcessingException e){
                logRequest.setData("Error converting data to JSON");
            }
        }else{
            logRequest.setData(null);
        }

        logRequest.setMessage(message);

        logRepository.save(logRequest);
    }

}
