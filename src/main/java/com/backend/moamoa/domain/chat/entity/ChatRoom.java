package com.backend.moamoa.domain.chat.entity;

import com.backend.moamoa.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Getter
@NoArgsConstructor
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "host")
    private User host;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "guest")
    private User guest;

    private boolean isActive;

    @Builder
    public ChatRoom(User host, User guest) {
        this.host = host;
        this.guest = guest;
        this.isActive = true;
    }
}
