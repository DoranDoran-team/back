package com.korit.dorandoran.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.dorandoran.dto.request.follow.PostUserFollowRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.entity.SubscriptionEntity;
import com.korit.dorandoran.entity.UserEntity;
import com.korit.dorandoran.repository.SubscribtionRepository;
import com.korit.dorandoran.repository.UserRepository;
import com.korit.dorandoran.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImplement implements SubscriptionService{

    private final SubscribtionRepository subscribtionRepository;
    private final UserRepository userRepository;
    
    @Override
    public ResponseEntity<ResponseDto> postSubscription(PostUserFollowRequestDto dto) {
        UserEntity userEntity = null;
        
        try {
            String userId = dto.getUserId();
            String follower = dto.getSubscriber();

            userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();

            userEntity = userRepository.findByUserId(follower);
            if(userEntity == null) return ResponseDto.noExistUserId();

            dto.setUserId(userId);
            dto.setSubscriber(follower);

            SubscriptionEntity subscriptionEntity = new SubscriptionEntity(dto);
            subscribtionRepository.save(subscriptionEntity);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> cancleFollow(String userId, String subscriber) {
        UserEntity userEntity = null;
        SubscriptionEntity subscriptionEntity = null;
        try {
            userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();

            subscriptionEntity = subscribtionRepository.findByUserIdAndSubscriber(userId, subscriber);
            subscribtionRepository.delete(subscriptionEntity);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }
}
