//package com.example.orders.service;
//
//import com.xrfriends.careeasy.careeasybackend.service.dto.KeycloakDTO;
//import feign.FeignException;
//import org.keycloak.representations.AccessTokenResponse;
//import org.keycloak.representations.idm.CredentialRepresentation;
//
//import java.util.List;
//
//public interface KeycloakService {
//
//    AccessTokenResponse token(String grantType, String clientId, String clientSceret, String userName, String password);
//
//    String getValidToken();
//
//    void validCheckAndToken();
//
//    String getIdByUsername(String userName);
//
//    String createUser(KeycloakDTO keycloakDTO);
//
//    String updateUser(String id, KeycloakDTO keycloakDTO);
//    String updateUserByUsername(String userName, KeycloakDTO keycloakDTO);
//
//    String deleteUser(String id);
//    String deleteUserByUsername(String userName);
//
//    KeycloakDTO getUser(String id);
//    KeycloakDTO getUserByUsername(String userName);
//    List<KeycloakDTO> getUsers();
//
//    /**
//     * Remove all user sessions associated with the user Also send notification to all clients that have an admin URL to invalidate the sessions for the particular user.
//     * @param id
//     * @return null=error, id
//     */
//    String logoutUser(String id);
//
//    /**
//     * Clear any user login failures for all users This can release temporary disabled users
//     * @return true=success, false=error
//     */
//    boolean deleteBruteForceAll();
//
//    /**
//     * Get status of a username in brute force detection
//     * @param id
//     * @return null=error, true=brute force, false=normal
//     */
//    Boolean getBruteForceUser(String id);
//
//    /**
//     * Clear any user login failures for the user This can release temporary disabled user
//     * @param id
//     * @return null=error, id
//     */
//    String deleteBruteForceUser(String id);
//
//    String resetPasswordByUsername(String userName, CredentialRepresentation representation) throws FeignException;
//
//
//    KeycloakDTO getUserByEmail(String email) throws FeignException;
//}
