package unipar.br.apitripshare.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "tb_trip_post")
public class TripPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_post_id")
    private Long id;
    @Column(name = "dt_post")
    private Instant datePost;
    @Column(name = "ds_destination")
    private String destination;
    @ManyToOne
    private User user;
    @OneToMany
    private List<Photo> photos;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_trip_post_comment",
            joinColumns = @JoinColumn(name = "trip_post_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private List<Comment> comments;

    public TripPost() {
    }
    public TripPost(Long id, Instant datePost, String destination, User user, List<Photo> photos, List<Comment> comments) {
        this.id = id;
        this.datePost = datePost;
        this.destination = destination;
        this.user = user;
        this.photos = photos;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDatePost() {
        return datePost;
    }

    public void setDatePost(Instant datePost) {
        this.datePost = datePost;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
