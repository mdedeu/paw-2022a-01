package ar.edu.itba.paw.model;


import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(sequenceName = "users_id_seq", name = "users_id_seq", allocationSize = 1)
    @Column(nullable = false)
    private int id;
    @Column(length = 50, unique = true, nullable = false)
    private String email;
    @Column(name="last_login", insertable = false)
    private LocalDateTime lastLogin;
    @Column(name="phone_number", length = 10)
    private String phoneNumber;
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
    private UserAuth userAuth;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private ProfilePicture profilePicture;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private KycInformation kyc;
    @Column(name="rating_sum", nullable = false)
    private int ratingSum;
    @Column(name="rating_count", nullable = false)
    private int ratingCount;
    @Column(name="rating", nullable=false, updatable = false, insertable = false)
    private double rating;
    @Column(name="locale")
    private Locale locale;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Collection<Complain> complainCollection;

    public Collection<Offer> getOfferCollection() {
        return offerCollection;
    }

    public void setOfferCollection(Collection<Offer> offerCollection) {
        this.offerCollection = offerCollection;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Collection<Offer> offerCollection;


    public User() {
        // Just for Hibernate!
    }

    public User (String email, String phoneNumber, int ratingCount, int ratingSum, Locale locale) {
        this.email = email;
        this.ratingCount = ratingCount;
        this.ratingSum = ratingSum;
        this.phoneNumber= phoneNumber;
        this.locale = locale;
    }

    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public int getRatingSum() {
        return ratingSum;
    }
    public int getRatingCount() {
        return ratingCount;
    }

    public double getRating(){
        return rating;
    }

    public int getNearestWholeRating(){
        return (int) getRating();
    }

    public String getPhoneNumber(){return phoneNumber;}

    /* It is optional because of legacy implementation, in the first sprint we did not hace usernames, only emails */
    public Optional<String> getUsername() {
        if (userAuth != null)
            return Optional.ofNullable(userAuth.getUsername());
        return Optional.empty();
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public Locale getLocale() {
        return locale;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public long getMinutesSinceLastLogin(){
        Duration loggedIn = Duration.between(lastLogin, LocalDateTime.now());
        return loggedIn.toMinutes();
    }
    public Optional<KycInformation> getKyc() {
        return Optional.ofNullable(this.kyc);
    }
    public Optional<ProfilePicture> getProfilePicture() {
        return Optional.ofNullable(profilePicture);
    }


    public void setEmail(String email) {
        this.email = email;
    }
    public void setRatingSum(int ratingSum) {
        this.ratingSum = ratingSum;
    }
    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }
    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }
    public void setKyc(KycInformation kyc){
        this.kyc = kyc;
    }

}
