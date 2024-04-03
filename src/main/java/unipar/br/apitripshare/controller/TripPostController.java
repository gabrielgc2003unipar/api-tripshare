package unipar.br.apitripshare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import unipar.br.apitripshare.dto.posts.CreateTripPostDTO;
import unipar.br.apitripshare.dto.tweets.CreateTweetDTO;
import unipar.br.apitripshare.services.TripPostService;

@RestController
public class TripPostController {
    private final TripPostService tripPostService;

    public TripPostController(TripPostService tripPostService) {
        this.tripPostService = tripPostService;
    }
    @RequestMapping("/trip-post")
    public ResponseEntity<Void> createTripPost(@RequestBody CreateTripPostDTO dto,
                                               JwtAuthenticationToken token) {
        try {
            tripPostService.createTripPost(dto, token);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
