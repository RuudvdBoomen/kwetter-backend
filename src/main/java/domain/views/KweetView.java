/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.views;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KweetView {

    private long id;
    private String content;
    private Date postedOn;
    private String createdBy;
    private String profileImage;
    private int likes;
}
