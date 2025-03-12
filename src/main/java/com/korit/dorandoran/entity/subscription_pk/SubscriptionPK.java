package com.korit.dorandoran.entity.subscription_pk;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPK implements Serializable{
    private String userId;
    private String subscriber;
}
