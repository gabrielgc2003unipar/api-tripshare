package unipar.br.apitripshare.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import unipar.br.apitripshare.dto.tweets.CreateTweetDTO;
import unipar.br.apitripshare.dto.tweets.FeedItemDTO;
import unipar.br.apitripshare.entities.Role;
import unipar.br.apitripshare.entities.Tweet;
import unipar.br.apitripshare.repository.TweetRepository;
import unipar.br.apitripshare.repository.UserRepository;

import java.util.UUID;

@Service
public class TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }
    public Page feed(int page, int pageSize) {

        var tweets = tweetRepository.findAll(
                        PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(tweet ->
                        new FeedItemDTO(
                                tweet.getId(),
                                tweet.getContent(),
                                tweet.getUser().getUsername())
                );
        return tweets;

    }
    public void createTweet(CreateTweetDTO dto,
                            JwtAuthenticationToken token) throws ResponseStatusException {
        var user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(dto.content());
        try {
            tweetRepository.save(tweet);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void deleteTweet(Long tweetId,
                            JwtAuthenticationToken token) throws ResponseStatusException{
        var user = userRepository.findById(UUID.fromString(token.getName()));
        var tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if (isAdmin || tweet.getUser().getId().equals(UUID.fromString(token.getName()))) {
            try {
                tweetRepository.deleteById(tweetId);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
    }

}
