package unipar.br.apitripshare.services;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import unipar.br.apitripshare.dto.posts.CreateTripPostDTO;

@Service
public class TripPostService {
    public void createTripPost(CreateTripPostDTO dto, JwtAuthenticationToken token) throws ResponseStatusException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
