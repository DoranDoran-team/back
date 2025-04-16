package com.korit.dorandoran.common.object;

import java.util.ArrayList;
import java.util.List;

import com.korit.dorandoran.repository.resultset.GetAccuseUserListResultSet;

import lombok.Getter;

@Getter
public class AccuseUserList {

  private String userId;
  private String name;
  private String profileImage;
  private Integer accuseCount;
  private Boolean role;

  public AccuseUserList(GetAccuseUserListResultSet resultSet) {
    this.userId = resultSet.getUserId();
    this.name = resultSet.getName();
    this.profileImage = resultSet.getProfileImage();
    this.accuseCount = resultSet.getAccuseCount();
    this.role = resultSet.getRole();
  }

  public static List<AccuseUserList> getUserList(List<GetAccuseUserListResultSet> resultSets) {
    List<AccuseUserList> accuseUserLists = new ArrayList<>();
    for (GetAccuseUserListResultSet getAccuseUserListResultSet : resultSets) {
      AccuseUserList accuseUserList = new AccuseUserList(getAccuseUserListResultSet);
      accuseUserLists.add(accuseUserList);
    }
    return accuseUserLists;
  }
}
