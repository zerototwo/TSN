package com.social.seed.controller;

import com.alibaba.fastjson.JSON;
import com.social.seed.service.FriendsRelationshipService;
import com.social.seed.util.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to handle operations related to friendships in the application.
 */
@RestController
@Tag(name = "Friends", description = "Friends Module")
@RequestMapping("/api/v0.0.1/friend")
public class FriendsRelationshipController {
    //region Dependencies
    private final FriendsRelationshipService friendsRelationshipService;

    @Autowired
    public FriendsRelationshipController(FriendsRelationshipService friendsRelationshipService) {
        this.friendsRelationshipService = friendsRelationshipService;
    }
    //endregion

    //region CRUD
    /**
     * Creates a friendship request.
     *
     * @param idUserRequest      The ID of the user initiating the request.
     * @param idUserToBeFriend   The ID of the user to whom the request is sent.
     * @return ResponseEntity with a ResponseDTO.
     */
    @PostMapping("/createRequest/{idUserToBeFriend}")
    public ResponseEntity<ResponseDTO> createRequestFriendship(
            @RequestHeader("userId") String idUserRequest,
            @PathVariable String idUserToBeFriend) {
        ResponseEntity<Object> response = friendsRelationshipService.createRequestFriendship(idUserRequest, idUserToBeFriend);
        return ResponseEntity
                .status(response.getStatusCode())
                .body((ResponseDTO) response.getBody());
    }

    /**
     * Cancels a friendship request.
     *
     * @param idUserRequest                  The ID of the user canceling the request.
     * @param idUserToCancelFriendRequest   The ID of the user to whom the request is canceled.
     * @return ResponseEntity with a ResponseDTO.
     */
    @PostMapping("/cancelRequest/{idUserToCancelFriendRequest}")
    public ResponseEntity<ResponseDTO> cancelRequestFriendship(
            @RequestHeader("userId") String idUserRequest,
            @PathVariable String idUserToCancelFriendRequest) {
        ResponseEntity<Object> response = friendsRelationshipService.cancelRequestFriendship(idUserRequest, idUserToCancelFriendRequest);
        return ResponseEntity
                .status(response.getStatusCode())
                .body((ResponseDTO) response.getBody());
    }

    /**
     * Accepts a friendship request.
     *
     * @param idUserRequest                   The ID of the user accepting the request.
     * @param idUserToAcceptedFriendRequest   The ID of the user whose request is accepted.
     * @return ResponseEntity with a ResponseDTO.
     */
    @PostMapping("/acceptedRequest/{idUserToAcceptedFriendRequest}")
    public ResponseEntity<ResponseDTO> acceptedRequestFriendship(
            @RequestHeader("userId") String idUserRequest,
            @PathVariable String idUserToAcceptedFriendRequest) {
        ResponseEntity<Object> response = friendsRelationshipService.acceptedRequestFriendship(idUserRequest, idUserToAcceptedFriendRequest);
        return ResponseEntity
                .status(response.getStatusCode())
                .body((ResponseDTO) response.getBody());
    }

    /**
     * Deletes an existing friendship.
     *
     * @param idUserRequest                   The ID of the user wishing to end the friendship.
     * @param idUserToDeleteFriendship        The ID of the user with whom to stop being friends.
     * @return ResponseEntity with a ResponseDTO.
     */
    @PostMapping("/deleteFriendship/{idUserToDeleteFriendship}")
    public ResponseEntity<ResponseDTO> deleteFriendship(
            @RequestHeader("userId") String idUserRequest,
            @PathVariable String idUserToDeleteFriendship) {
        ResponseEntity<Object> response = friendsRelationshipService.deleteFriendship(idUserRequest, idUserToDeleteFriendship);
        return ResponseEntity
                .status(response.getStatusCode())
                .body((ResponseDTO) response.getBody());
    }
    //endregion

    //region GET
    /**
     * Retrieves Recommendations to the SocialUser of the idUserRequest.
     *
     * @param idUserRequest The ID of the user to recommendation SocialUser to being friends.
     * @return ResponseEntity with a ResponseDTO.
     */
    @GetMapping("/friend-recommendations/")
    public ResponseEntity<ResponseDTO> getFriendRecommendationsForUserById(
            @RequestHeader("userId") String idUserRequest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        ResponseEntity<Object> response = friendsRelationshipService.getFriendRecommendationsForUserById(idUserRequest, page, size);
        ResponseEntity<ResponseDTO> body = ResponseEntity
            .status(response.getStatusCode())
            .body((ResponseDTO) response.getBody());
        System.out.println(JSON.toJSONString(body));
        return body;
    }

    /**
     * Returns the Friends of a SocialUser based on their user ID.
     *
     * @param idUserRequest The ID of the user to Request.
     * @param idUserToFind The ID of the user to returns friends.
     * @return ResponseEntity with a ResponseDTO.
     */
    @GetMapping("/friendsOf/{idUserToFind}")
    public ResponseEntity<ResponseDTO> getFriendsOfUserById(

            @RequestHeader("userId") String idUserRequest,
            @PathVariable String idUserToFind,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        ResponseEntity<Object> response = friendsRelationshipService.getFriendsOfUserById(idUserRequest, idUserToFind, page, size);
        return ResponseEntity
                .status(response.getStatusCode())
                .body((ResponseDTO) response.getBody());
    }
    //endregion

    // Received Request
    /**
     * Cancels a received friendship request.
     *
     * @param idUserRequest                  The ID of the user canceling the request.
     * @param idUserToCancelFriendRequest   The ID of the user to whom the request is canceled.
     * @return ResponseEntity with a ResponseDTO.
     */
    @PostMapping("/cancelReceivedRequest/{idUserToCancelFriendRequest}")
    public ResponseEntity<ResponseDTO> cancelReceivedRequest(
            @RequestHeader("userId") String idUserRequest,
            @PathVariable String idUserToCancelFriendRequest) {
        ResponseEntity<Object> response = friendsRelationshipService.cancelReceivedRequest(idUserRequest, idUserToCancelFriendRequest);
        return ResponseEntity
                .status(response.getStatusCode())
                .body((ResponseDTO) response.getBody());
    }
    //endregion
}