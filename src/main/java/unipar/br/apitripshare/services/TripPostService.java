package unipar.br.apitripshare.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import unipar.br.apitripshare.dto.posts.CreateTripPostDTO;
import unipar.br.apitripshare.entities.Photo;
import unipar.br.apitripshare.entities.TripPost;
import unipar.br.apitripshare.repository.TripPostRepository;
import unipar.br.apitripshare.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TripPostService {
    private TripPostRepository tripPostRepository;
    private UserRepository userRepository;

    public TripPostService(TripPostRepository tripPostRepository, UserRepository userRepository) {
        this.tripPostRepository = tripPostRepository;
        this.userRepository = userRepository;
    }
    public void createTripPost(CreateTripPostDTO dto, JwtAuthenticationToken token) throws ResponseStatusException {
        var user = userRepository.findById(UUID.fromString(token.getName()));
        var tripPost = new TripPost();
        tripPost.setUser(user.get());
        tripPost.setDestination(dto.destination());
        tripPost.setDescription(dto.description());
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = date.atStartOfDay(); // Converte para LocalDateTime
        ZoneId zoneId = ZoneId.systemDefault(); // Pega o timezone do sistema
        Instant instant = dateTime.atZone(zoneId).toInstant(); // Converte para Instant
        tripPost.setDatePost(instant);
        List<Photo> photos = new ArrayList<>();
        for (var photo : dto.photos()) {
            Photo photoAdd = new Photo();
            photoAdd.setUrl(photo.url());
            photoAdd.setDescription(photo.description());
            photoAdd.setTripPost(tripPost);
            photos.add(photoAdd);
        }
        tripPost.setPhotos(photos);

        try {
            tripPostRepository.save(tripPost);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
