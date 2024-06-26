package unipar.br.apitripshare.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ds_url")
    private String url;
    @Column(name = "ds_description")
    private String description;
    @ManyToOne
    private TripPost tripPost;

    public Photo() {
    }

    public Photo(Long id, String url, String description, TripPost tripPost) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.tripPost = tripPost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TripPost getTripPost() {
        return tripPost;
    }

    public void setTripPost(TripPost tripPost) {
        this.tripPost = tripPost;
    }
}
