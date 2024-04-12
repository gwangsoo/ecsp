//package com.example.orders.client;
//
//import feign.Headers;
//import feign.Param;
//import feign.RequestLine;
//import org.keycloak.representations.AccessTokenResponse;
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//
//import java.util.List;
//import java.util.Map;
//
//@Headers({"Authorization: Bearer {access_token}"})
//public interface KeycloakClient {
//
//    @RequestLine("POST /realms/{realm}/protocol/openid-connect/token")
//    @Headers("Content-Type: application/x-www-form-urlencoded")
//    AccessTokenResponse token(@Param("realm") String realm, String credential);
//
//    @RequestLine("POST /admin/realms/{realm}/users")
//    @Headers("Content-Type: application/json")
//    void createUser(@Param("access_token") String accessToken, @Param("realm") String realm, UserRepresentation userRepresentation);
//
//    @RequestLine("GET /admin/realms/{realm}/users/{id}")
//    @Headers("Content-Type: application/json")
//    UserRepresentation getUser(@Param("access_token") String accessToken, @Param("realm") String realm, @Param("id") String id);
//
//    @RequestLine("GET /admin/realms/{realm}/users?briefRepresentation=true&username={username}")
//    @Headers("Content-Type: application/json")
//    List<UserRepresentation> getUserByName(@Param("access_token") String accessToken, @Param("realm") String realm, @Param("username") String username);
//
//    @RequestLine("GET /admin/realms/{realm}/users?briefRepresentation=true")
//    @Headers("Content-Type: application/json")
//    List<UserRepresentation> getUsers(@Param("access_token") String accessToken, @Param("realm") String realm);
//
//    @RequestLine("PUT /admin/realms/{realm}/users/{id}")
//    @Headers("Content-Type: application/json")
//    void updateUser(@Param("access_token") String accessToken, @Param("realm") String realm, @Param("id") String id, UserRepresentation userRepresentation);
//
//    @RequestLine("DELETE /admin/realms/{realm}/users/{id}")
//    @Headers("Content-Type: application/json")
//    void deleteUser(@Param("access_token") String accessToken, @Param("realm") String realm, @Param("id") String id);
//
//    @RequestLine("POST /admin/realms/{realm}/users/{id}/logout")
//    @Headers("Content-Type: application/json")
//    void logoutUser(@Param("access_token") String accessToken, @Param("realm") String realm, @Param("id") String id);
//
//    @RequestLine("DELETE /admin/realms/{realm}/attack-detection/brute-force/users")
//    @Headers("Content-Type: application/json")
//    void deleteBruteForceAll(@Param("access_token") String accessToken, @Param("realm") String realm);
//
//    @RequestLine("GET /admin/realms/{realm}/attack-detection/brute-force/users/{id}")
//    @Headers("Content-Type: application/json")
//    Map getBruteForceUser(@Param("access_token") String accessToken, @Param("realm") String realm, @Param("id") String id);
//
//    @RequestLine("DELETE /admin/realms/{realm}/attack-detection/brute-force/users/{id}")
//    @Headers("Content-Type: application/json")
//    void deleteBruteForceUser(@Param("access_token") String accessToken, @Param("realm") String realm, @Param("id") String id);
//
//    @RequestLine("PUT /admin/realms/{realm}/users/{id}/reset-password")
//    @Headers("Content-Type: application/json")
//    void resetPassword(@Param("access_token") String accessToken, @Param("realm") String realm, @Param("id") String id, CredentialRepresentation representation);
//
//    @RequestLine("GET /admin/realms/{realm}/users?briefRepresentation=true&email={email}")
//    @Headers("Content-Type: application/json")
//    List<UserRepresentation> getUserByEmail(@Param("access_token") String accessToken, @Param("realm") String realm, @Param("email") String email);
//}
