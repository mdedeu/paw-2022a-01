package ar.edu.itba.paw.persistence;

import javax.persistence.*;

@Entity
@Table(name = "auth")
public final class UserAuth  {


    @Column(length = 100,nullable = false)
    private String password;

    @Column(name="uname",nullable = false,length = 50)
    @Id
    private String username;

    @ManyToOne()
    @JoinColumn(name="role_id")
    private Role role;

    @Column(name="code")
    private Integer code;
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private UserStatus userStatus;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;


    UserAuth(){}

    public static class Builder {
        private Integer id;
        private String username;
        private String password;
        private String role;
        private Integer code;
        private UserStatus userStatus = UserStatus.UNVERIFIED;

        public Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public Builder withId(int id) { this.id = id; return this; }
        public Builder withRole(String role){this.role = role; return this; }
        public Builder withPassword(String password){this.password = password; return this; }

        public Builder withCode(Integer code){this.code = code ; return this;}
        public Builder withUserStatus(UserStatus userStatus){this.userStatus=userStatus;return this;}


        public int getId() {
            return id;
        }
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
        public String getRole(){return role; }
        public int getCode() {
            return code;
        }
        public UserStatus getUserStatus() {
            return userStatus;
        }
        protected UserAuth build() {
            return new UserAuth(this);
        }
    }

    private UserAuth(Builder builder) {
//        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.code = builder.code;
//        this.role = builder.role; //only creating instance in persistence
        this.userStatus = builder.userStatus;
    }

    public int getId() {
        return user.getId();
    }
    public String getUsername() {
        return username;
    }
    public String getPassword(){return password;}
    public Role getRole() {
        return role;
    }
    public UserStatus getUserStatus() {
        return userStatus;
    }
    public int getCode() { return code; };


//
//    public void setId(int id) {
//        this.id = id;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean equals(Object object){
        if(object == this)
            return true;
        if(!(object instanceof UserAuth))
            return false;
        UserAuth testedAuth= (UserAuth) object;
        return testedAuth.getId() == this.getId();
    }
}