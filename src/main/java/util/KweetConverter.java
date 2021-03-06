/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Kweet;
import domain.User;
import domain.views.KweetView;
import domain.views.UserListView;
import java.util.ArrayList;
import java.util.List;

public class KweetConverter {

    public static List<KweetView> convertKweets(List<Kweet> kweets) {
        List<KweetView> kweetViews = new ArrayList<>();
        for (Kweet kweet : kweets) {
            kweetViews.add(convertKweet(kweet));
        }
        return kweetViews;
    }

    public static KweetView convertKweet(Kweet kweet) {
        KweetView kweetView = new KweetView();
        kweetView.setId(kweet.getId());
        kweetView.setContent(kweet.getContent());
        kweetView.setPostedOn(kweet.getPostedOn());
        kweetView.setCreatedBy(kweet.getCreatedBy().getUsername());
        kweetView.setLikes(kweet.getLikes().size());
        return kweetView;
    }

    public static List<UserListView> createUserListItems(List<User> likes) {
        List<UserListView> userListViews = new ArrayList<>();
        for (User user : likes) {
            UserListView userListView = new UserListView();
            userListView.setUsername(user.getUsername());
            userListViews.add(userListView);
        }
        return userListViews;
    }
}
