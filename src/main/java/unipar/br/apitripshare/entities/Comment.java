package unipar.br.apitripshare.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tb_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ds_content")
    private String content;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    private TripPost tripPost;
    @Column(name = "dt_create")
    private Instant creationTimestamp;

    public Comment() {
    }

    public Comment(Long id, String content, User user, TripPost tripPost, Instant creationTimestamp) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.tripPost = tripPost;
        this.creationTimestamp = creationTimestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TripPost getTripPost() {
        return tripPost;
    }

    public void setTripPost(TripPost tripPost) {
        this.tripPost = tripPost;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
