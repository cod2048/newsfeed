package com.hanghae.module_activity.client;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewsfeedClientRequest {
    private Long userId;
    private String type;
    private Long targetId;
}
