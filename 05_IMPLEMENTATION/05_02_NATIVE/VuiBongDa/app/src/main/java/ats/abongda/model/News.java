package ats.abongda.model;

import org.parceler.Parcel;

/**
 * Created by NienLe on 06-Aug-16.
 */
@Parcel
public class News {
    private String Content;
    private String Description;
    private int Id;
    private String ImageLink;
    private int IsHot;
    private String Title;
    public String getContent() {
        return Content;
    }
    public void setContent(String Content) {
        this.Content = Content;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }
    public int getId() {
        return Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public String getImageLink() {
        return ImageLink;
    }
    public void setImageLink(String ImageLink) {
        this.ImageLink = ImageLink;
    }
    public int getIsHot() {
        return IsHot;
    }
    public void setIsHot(int IsHot) {
        this.IsHot = IsHot;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String Title) {
        this.Title = Title;
    }
    /*public News(String Content, String Description, int Id,
                       String ImageLink, int IsHot, String Title) {
        super();
        this.Content = Content;
        this.Description = Description;
        this.Id = Id;
        this.ImageLink = ImageLink;
        this.IsHot = IsHot;
        this.Title = Title;
    }*/

    public News() {
    }
}
