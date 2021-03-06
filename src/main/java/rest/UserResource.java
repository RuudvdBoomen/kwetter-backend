/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domain.Link;
import domain.User;
import domain.views.ProfileView;
import domain.views.UserListView;
import exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import services.UserService;

@Path("user")
@Tag(name = "User")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Edit a user")
    public Response edit(User user) {
        userService.editUser(user);
        return Response.ok().build();
    }

    @GET
    @Path("{username}")
    @Operation(summary = "Retrieve a user his profile by it's username")
    public Response getProfile(@PathParam("username") String username) {
        ProfileView profile = null;
        try {
            profile = userService.getProfile(username);
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
        return Response.ok(profile).build();
    }

    @GET
    @Path("{username}/followers")
    @Operation(summary = "Retrieve a user his followers by it's name")
    public Response getFollowers(@PathParam("username") String username, @Context UriInfo uriInfo) {
        List<UserListView> followers;
        try {
            followers = userService.getFollowers(username);
            setUserViewLinks(followers, uriInfo);
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
        return Response.ok(followers).build();
    }

    @GET
    @Path("{username}/following")
    @Operation(summary = "Retrieve a user his follwing by it's name")
    public Response getFollowing(@PathParam("username") String username, @Context UriInfo uriInfo) {
        List<UserListView> following;
        try {
            following = userService.getFollowing(username);
            setUserViewLinks(following, uriInfo);
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
        return Response.ok(following).build();
    }

    @PUT
    @Path("{username}/following/add/{userToFollow}")
    @Operation(summary = "Let a user follow another user")
    public Response follow(@PathParam("username") String username, @PathParam("userToFollow") String userToFollow) {
        try {
            if (userService.addFollow(username, userToFollow)) {
                return Response.ok().build();
            } else {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Path("{username}/following/remove/{userToFollow}")
    @Operation(summary = "Let a user follow another user")
    public Response unfollow(@PathParam("username") String username, @PathParam("userToFollow") String userToUnfollow) {
        try {
            if (userService.removeFollow(username, userToUnfollow)) {
                return Response.ok().build();
            } else {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
    }

    private void setUserViewLinks(List<UserListView> users, UriInfo uriInfo) {
        for (UserListView userListView : users) {
            String uri = uriInfo.getBaseUriBuilder().path(UserResource.class)
                    .path(userListView.getUsername()).build().toString();
            userListView.getLinks().add(new Link(uri, "self", "GET"));
        }
    }
}
