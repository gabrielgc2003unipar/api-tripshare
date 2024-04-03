package unipar.br.apitripshare.dto.posts;

import unipar.br.apitripshare.dto.photos.PhotosDTO;

import java.time.Instant;
import java.util.List;

public record CreateTripPostDTO(String destination, String description ,List<PhotosDTO> photos) {

}
