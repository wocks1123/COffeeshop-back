package com.wocks.coffeeshopback.user.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wocks.coffeeshopback.common.entity.TimestampBase;
import com.wocks.coffeeshopback.post.domain.Post;
import com.wocks.coffeeshopback.user.dto.ProfileUpdateRequestDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends TimestampBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;    // 계정

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String profile;     // 자기소개

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private boolean isDeleted;

    @Column
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;


    public User update(ProfileUpdateRequestDto dto) {
        if (dto.getNickName() != null) {
            nickName = dto.getNickName();
        }
        if (dto.getProfile() != null) {
            profile = dto.getProfile();
        }
        if (dto.getEmail() != null) {
            email = dto.getEmail();
        }
        return this;
    }

    public void delete() {
        isDeleted = true;
        deletedAt = LocalDateTime.now();
    }

}
